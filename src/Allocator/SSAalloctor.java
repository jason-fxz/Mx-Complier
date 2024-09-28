package Allocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.IRIns;
import IR.node.ins.phiIns;
import Optimize.CFGBuilder;

public class SSAalloctor {
    CFGBuilder CFG;
    IRRoot irRoot;
    IRFuncDef curFunc;

    HashMap<IRvar, List<IRIns>> defuseOfVar = new HashMap<>();
    Map<IRvar, Double> spillCost = new HashMap<>();

    static final int MAX_ALLOC_REG = 20;
    Set<Integer> inUse = new HashSet<>();
    // Set<Integer> freeReg = new HashSet<>();
    Stack<Integer> freeReg = new Stack<>();

    public SSAalloctor(IRRoot irRoot) {
        this.irRoot = irRoot;
        CFG = new CFGBuilder();
    }

    public void run() {
        // spilling
        for (var func : irRoot.funcs) {
            curFunc = func;
            SSASpill();
        }

        // coloring
        for (var func : irRoot.funcs) {
            curFunc = func;
            SSAColor();
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
    }

    private void countInsUsage(IRIns ins, double delta) {
        ins.getUses().forEach(var -> {
            spillCost.put(var, spillCost.getOrDefault(var, 0.0) + delta);
            if (!defuseOfVar.containsKey(var))
                defuseOfVar.put(var, new ArrayList<>());
            defuseOfVar.get(var).add(ins);
        });
    }

    void SSASpill() {
        curFunc.spilledVar = new HashMap<>();

        // handle function params (>8 params all spilled)
        for (int i = 8; i < curFunc.params.size(); i++) {
            curFunc.spilledVar.put(curFunc.params.get(i), curFunc.spilledVar.size());
        }

        // count the usage of each var & get def use IRvar
        spillCost.clear();
        for (var block : curFunc.blocks.values()) {
            double delta = Math.pow(10, block.loopDepth);
            block.phiList.forEach(ins -> countInsUsage(ins, delta));
            block.insList.forEach(ins -> countInsUsage(ins, delta));
            countInsUsage(block.endIns, delta);
        }
        // prechecks TODO: to be removed
        for (var block : curFunc.blocks.values()) {
            block.phiList.forEach(ins -> {
                assert ins.liveOut.contains(ins.getDef());
            });
        }


        // check if need to spill
        for (var block : curFunc.blocks.values()) {
            block.phiList.forEach(ins -> SpillIns(ins, false));
            block.insList.forEach(ins -> SpillIns(ins, false));
            SpillIns(block.endIns, false);
        }
        // update the liveIn/liveOut
        for (var block : curFunc.blocks.values()) {
            block.phiList.forEach(ins -> SpillIns(ins, true));
            block.insList.forEach(ins -> SpillIns(ins, true));
            SpillIns(block.endIns, true);
        }
    }

    void SSAColor() {
        // coloring
        CFG.build(curFunc);
        
        curFunc.regOfVar = new HashMap<>();
        // handle function params
        int i = 0;
        for (var x : curFunc.entryBlock.getLiveIn()) {
            curFunc.regOfVar.put(x, i++);
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
        for (int i = 0; i < MAX_ALLOC_REG; i++) {
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



}
