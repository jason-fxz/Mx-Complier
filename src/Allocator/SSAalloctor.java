package Allocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.List;

import IR.item.IRitem;
import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.IRIns;
import IR.node.ins.branchIns;
import IR.node.ins.icmpbranchIns;
import IR.node.ins.jumpIns;
import IR.node.ins.phiIns;
import Optimize.CFGBuilder;
import Util.IRLabeler;
import Util.Pair;

public class SSAalloctor {
    CFGBuilder CFG;
    IRRoot irRoot;
    IRFuncDef curFunc;

    Map<IRvar, Double> spillCost = new HashMap<>();

    static final int MAX_ALLOC_REG = 26; // 0-7 are for params
    Set<Integer> inUse = new HashSet<>();
    Stack<Integer> freeReg = new Stack<>();

    public SSAalloctor(IRRoot irRoot) {
        this.irRoot = irRoot;
        CFG = new CFGBuilder();
    }

    public void run() {
        var timer = Util.ExecutionTimer.timer;
        timer.start("Allocator");
        for (var func : irRoot.funcs) {
            curFunc = func;
            SSASpill();
            CFG.buildCFG(curFunc).calcDom().buildDomTree();
            SSAColor();
            insertBlockOnCriticalEdges();
            PhiElimation();
            // reorderBlocks();
        }
        timer.stop("Allocator");
    }

    void PhiElimation() {
        for (var block : curFunc.blockList) {
            if (block.empty()) throw new RuntimeException("PhiElimation: block empty");
            if (block.phiList.isEmpty()) continue;
            for (var prev : block.getPrevBlocks()) {
                ArrayList<Pair<IRitem, IRitem>> moves = new ArrayList<>();
                for (var phi : block.phiList) {
                    moves.add(new Pair<>(phi.getValue(prev.getLabel()), phi.result));
                }
                prev.moveList = moves;
            }
            block.phiList.clear();
        }
    }

    private void Spillvars(ArrayList<IRvar> vars) {
        // Sort the vars by usage count
        vars.sort((a, b) -> Double.compare(spillCost.get(a), spillCost.get(b)));
        int n = vars.size() - MAX_ALLOC_REG;
        assert spillCost.get(vars.getFirst()) <= spillCost.get(vars.getLast());
        for (int i = 0; i < n; i++) {
            // spill vars.get(i)
            curFunc.spilledVar.put(vars.get(i), curFunc.spilledVar.size());
        }

    }

    private void SpillIns(IRIns ins, boolean check) {
        ins.liveOut.removeIf(var -> curFunc.spilledVar.containsKey(var));
        ins.liveIn.removeIf(var -> curFunc.spilledVar.containsKey(var));

        if (ins.liveOut.size() > MAX_ALLOC_REG) {
            if (check) {
                throw new RuntimeException("SpillIns : liveOut.size() > MAX_ALLOC_REG");
            }
            Spillvars(new ArrayList<>(ins.liveOut));
        }

        if (check) {
            if (ins.getDef() != null && !curFunc.spilledVar.containsKey(ins.getDef())) {
                assert ins.liveOut.contains(ins.getDef());
            }
        }

        if (check) {
            curFunc.maxUsedReg = Math.max(curFunc.maxUsedReg, ins.liveOut.size());
        }
    }

    private void countInsUsage(IRIns ins, double delta) {
        ins.getUses().forEach(var -> {
            spillCost.put(var, spillCost.getOrDefault(var, 0.0) + delta);
        });
    }

    void SSASpill() {
        curFunc.spilledVar = new HashMap<>();

        // handle function params (>8 params all spilled)
        for (int i = 8; i < curFunc.params.size(); i++) {
            curFunc.spilledVar.put(curFunc.params.get(i), curFunc.spilledVar.size());
        }
        // <8 never spill
        for (int i = 0; i < 8 && i < curFunc.params.size(); i++) {
            spillCost.put(curFunc.params.get(i), 1e200); 
        }

        // count the usage of each var & get def use IRvar
        spillCost.clear();
        for (var block : curFunc.blockList) {
            double delta = Math.pow(10, block.loopDepth);
            block.phiList.forEach(ins -> countInsUsage(ins, delta));
            block.insList.forEach(ins -> countInsUsage(ins, delta));
            countInsUsage(block.endIns, delta);
        }
        // prechecks TODO: to be removed
        // for (var block : curFunc.blocks.values()) {
        //     block.phiList.forEach(ins -> {
        //         assert ins.liveOut.contains(ins.getDef());
        //     });
        // }


        // check if need to spill
        for (var block : curFunc.blockList) {
            block.phiList.forEach(ins -> SpillIns(ins, false));
            block.insList.forEach(ins -> SpillIns(ins, false));
            SpillIns(block.endIns, false);
        }
        // update the liveIn/liveOut
        for (var block : curFunc.blockList) {
            block.phiList.forEach(ins -> SpillIns(ins, true));
            block.insList.forEach(ins -> SpillIns(ins, true));
            SpillIns(block.endIns, true);
        }
    }

    void SSAColor() {
        // coloring
        
        curFunc.regOfVar = new HashMap<>();
        // handle function params
        // int i = 0;
        // for (var x : curFunc.entryBlock.getLiveIn()) {
        //     curFunc.regOfVar.put(x, i++);
        // }
        for (int i = 0; i < 8 && i < curFunc.params.size(); i++) {
            var x = curFunc.params.get(i);
            if (curFunc.entryBlock.getLiveIn().contains(x)) {
                curFunc.regOfVar.put(x, i);
                curFunc.maxUsedReg = Math.max(curFunc.maxUsedReg, i + 1);
            }
        }
        preOrderDFS(curFunc.entryBlock);

    }

    // get a free reg from freeReg
    int getFreeReg() {
        if (freeReg.size() == 0) {
            throw new RuntimeException("No free reg");
        }
        var reg = freeReg.pop();
        inUse.add(reg);
        assert reg < curFunc.maxUsedReg;
        return reg;
    }

    void delReg(Integer reg) {
        if (reg == null) {
            throw new RuntimeException("delReg : reg == null");
            // return;
        }
        inUse.remove(reg);
        freeReg.add(reg);
    }

    void colorIns(IRIns ins) {
        if (!(ins instanceof phiIns)) {
            for (var x : ins.getUses()) {
                if (!curFunc.spilledVar.containsKey(x) && !ins.liveOut.contains(x)) {
                    delReg(curFunc.regOfVar.get(x));
                }
            }

        }
        var y = ins.getDef();
        if (y != null && !curFunc.spilledVar.containsKey(y)) {
            curFunc.regOfVar.put(y, getFreeReg());
        }
    }

    void preOrderDFS(IRblock block) {
        inUse.clear();
        freeReg.clear();
        for (var x : block.getLiveIn()) {
            if (!curFunc.spilledVar.containsKey(x)) {
                if (!curFunc.regOfVar.containsKey(x)) {
                    throw new RuntimeException("preOrderDFS : regOfVar not contains x");
                }
                inUse.add(curFunc.regOfVar.get(x));
            }
        }
        for (int i = MAX_ALLOC_REG - 1; i >= 0; --i) {
            if (!inUse.contains(i)) freeReg.add(i);
        }


        for (var ins : block.phiList){
            colorIns(ins);
        }
        assert block.phiList.size() == 0 || block.phiList.get(0).liveOut.size() == inUse.size();
        
        for (var ins : block.insList) {
            colorIns(ins);
            assert ins.liveOut.size() == inUse.size();
        }
        colorIns(block.endIns);
        assert block.endIns.liveOut.size() == inUse.size();

        for (var child : CFG.domChildren.get(block)) {
            preOrderDFS(child);
        }
    }

    void insertBlockOnCriticalEdges() {
        List<Pair<IRblock, IRblock>> criticalEdges = findCirticalEdges();
        
        for (var edge : criticalEdges) {
            IRblock newBlock = new IRblock(IRLabeler.getIdLabel(".CTE"));

            IRblock pred = edge.first;
            IRblock succ = edge.second;

            if (pred.endIns instanceof jumpIns) {
                ((jumpIns) pred.endIns).replaceLabel(succ.Label, newBlock.Label);
            } else if (pred.endIns instanceof branchIns) {
                ((branchIns) pred.endIns).replaceLabel(succ.Label, newBlock.Label);
            } else if (pred.endIns instanceof icmpbranchIns) {
                ((icmpbranchIns) pred.endIns).replaceLabel(succ.Label, newBlock.Label);
            } else throw new RuntimeException("insertBlockOnCriticalEdges: pred.endIns not jumpIns or branchIns");

            newBlock.setEndIns(new jumpIns(succ.Label));
            newBlock.initPrevNextBlocks();

            pred.getNextBlocks().remove(succ);
            pred.addNextBlock(newBlock);
            newBlock.addPrevBlock(pred);

            succ.getPrevBlocks().remove(pred);
            succ.addPrevBlock(newBlock);
            newBlock.addNextBlock(succ);

            succ.phiList.forEach(phi -> {
                phi.replaceLabel(pred.Label, newBlock.Label);
            });

            // curFunc.blocks.put(newBlock.Label, newBlock);
            CFG.blockList.add(CFG.blockList.indexOf(pred) + 1, newBlock);
            curFunc.blocks.put(newBlock.Label, newBlock);
        }

        // curFunc.blocks.clear();
        // for (var block : CFG.blockList) {
        //     curFunc.blocks.put(block.Label, block);
        // }

        if (!findCirticalEdges().isEmpty()) {
            throw new RuntimeException("critical edges not removed");
        }
    }

    List<Pair<IRblock, IRblock>> findCirticalEdges() {
        List<Pair<IRblock, IRblock>> criticalEdges = new ArrayList<>();

        for (var block : CFG.blockList) {
            if (block.getNextBlocks().size() > 1) {
                for (var nextBlock : block.getNextBlocks()) {
                    if (nextBlock.getPrevBlocks().size() > 1) {
                        criticalEdges.add(new Pair<>(block, nextBlock));
                    }
                }
            }
        }
        return criticalEdges;
    }

    void reorderBlocks() {
        // Reorder blocks to ensure connected blocks are closer
        List<IRblock> orderedBlocks = new ArrayList<>();
        Set<IRblock> visitedBlocks = new HashSet<>();

        reorderBlocksDFS(curFunc.entryBlock, orderedBlocks, visitedBlocks);

        curFunc.blockList.clear();
        curFunc.blocks.clear();

        for (var block : orderedBlocks) {
            curFunc.blockList.add(block);
            curFunc.blocks.put(block.Label, block);
        }
    }

    void reorderBlocksDFS(IRblock block, List<IRblock> orderedBlocks, Set<IRblock> visitedBlocks) {
        orderedBlocks.add(block);
        visitedBlocks.add(block);

        for (var nextBlock : block.getNextBlocks()) {
            if (!visitedBlocks.contains(nextBlock)) {
                reorderBlocksDFS(nextBlock, orderedBlocks, visitedBlocks);
            }
        }
    }


}
