package Optimize;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.IRIns;
import IR.node.ins.branchIns;
import IR.node.ins.callIns;
import IR.node.ins.icmpIns;
import IR.node.ins.icmpbranchIns;
import IR.node.ins.jumpIns;
import IR.node.ins.storeIns;
import Optimize.CFGBuilder;


public class DCE {
    IRRoot irRoot;
    CFGBuilder CFG = new CFGBuilder();

    public DCE(IRRoot irRoot) {
        this.irRoot = irRoot;
    }

    public void run() {
        var timer = Util.ExecutionTimer.timer;
        timer.start("DCE");        
        for (var func : irRoot.funcs) {
            CFG.buildCFG(func);
            removeUnreachableBlock(func);
            removeUselessIns(func);
            removeUnuseReg(func);
            removeUnreachableBlock(func);
            jumpElimination(func);
            removeUnreachableBlock(func);
        }
        timer.stop("DCE");
    }

    public void runJumpElimination() {
        var timer = Util.ExecutionTimer.timer;
        timer.start("DCE");
        for (var func : irRoot.funcs) {
            CFG.buildCFG(func);
            removeUnreachableBlock(func);
            jumpElimination(func);
            removeUnreachableBlock(func);
        }
        timer.stop("DCE");
    }

    void removeUselessIns(IRFuncDef func) {
        HashMap<IRvar, IRIns> varDef = new HashMap<>();

        Queue<IRIns> workList = new ArrayDeque<>();

        for (var x : func.params) {
            varDef.put(x, null);
        }

        for (var block : func.blocks.values()) {
            for (var ins : block.phiList) {
                if (ins.getDef() != null) {
                    varDef.put(ins.getDef(), ins);
                }
                ins.removed = true;
            }
            for (var ins : block.insList) {
                if (ins.getDef() != null) {
                    varDef.put(ins.getDef(), ins);
                }
                ins.removed = true;
                if (ins instanceof storeIns || ins instanceof callIns) {
                    workList.add(ins);
                    ins.removed = false;
                }
            }
            if (block.endIns instanceof branchIns) {
                var ins = (branchIns) block.endIns;
                if (ins.trueLabel.equals(ins.falseLabel)) {
                    block.endIns = new jumpIns(ins.trueLabel);
                }
            } else if (block.endIns instanceof icmpbranchIns) {
                var ins = (icmpbranchIns) block.endIns;
                if (ins.trueLabel.equals(ins.falseLabel)) {
                    block.endIns = new jumpIns(ins.trueLabel);
                }
            }
            workList.add(block.endIns);
        }
        
        while (!workList.isEmpty()) {
            var ins = workList.poll();
            for (var x : ins.getUses()) {
                if (varDef.containsKey(x)) {
                    var defins = varDef.get(x);
                    if (defins != null && defins.removed == true) {
                        defins.removed = false;
                        workList.add(varDef.get(x));
                    }
                } else throw new RuntimeException("removeUselessIns: unexpected varDef");
            }
        }

        for (var block : func.blocks.values()) {
            block.phiList.removeIf(ins -> ins.removed);
            block.insList.removeIf(ins -> ins.removed);
        }
    }

    void jumpElimination(IRFuncDef func) {
        for (var block : func.blocks.values()) {
            if (block.phiList.size() == 0 && block.insList.size() == 0 && block.endIns instanceof jumpIns
            && block.moveList == null) {
                var jump = (jumpIns) block.endIns;
                var succ = func.blocks.get(jump.label);

                if (succ == block) continue;
                if (!succ.phiList.isEmpty()) continue;
    
                for (var prev : block.getPrevBlocks()) {
                    if (prev.endIns instanceof jumpIns) {
                        ((jumpIns)prev.endIns).replaceLabel(block.Label, jump.label);
                    } else if (prev.endIns instanceof branchIns) {
                        ((branchIns)prev.endIns).replaceLabel(block.Label, jump.label);
                    } else if (prev.endIns instanceof icmpbranchIns) {
                        ((icmpbranchIns)prev.endIns).replaceLabel(block.Label, jump.label);
                    }  else throw new RuntimeException("jumpElimination: unexpected endIns");
                    prev.getNextBlocks().remove(block);
                    prev.getNextBlocks().add(succ);

                    succ.getPrevBlocks().remove(block);
                    succ.getPrevBlocks().add(prev);

                    succ.phiList.forEach(phi -> {
                        phi.replaceLabel(block.Label, prev.Label);
                    });
                }
                
            }
        }

    }

    void removeUnreachableBlock(IRFuncDef curFunc) {
        Set<IRblock> reachable = new HashSet<>();
        Queue<IRblock> workList = new ArrayDeque<>();
        workList.add(curFunc.entryBlock);
        reachable.add(curFunc.entryBlock);
        while (!workList.isEmpty()) {
            var block = workList.poll();
            for (var suc : block.getNextBlocks()) {
                if (!reachable.contains(suc)) {
                    reachable.add(suc);
                    workList.add(suc);
                }
            }
        }
        curFunc.blocks.values().removeIf(block -> !reachable.contains(block));
        for (var block : curFunc.blocks.values()) {
            for (var phi : block.phiList) {
                phi.values.removeIf(item -> !reachable.contains(curFunc.blocks.get(item.label)));
            }
        }
    }


    void removeUnuseReg(IRFuncDef curFunc) {
        Map<IRvar, IRIns> varDef = new HashMap<>();
        Map<IRvar, Integer> varUseCnt = new HashMap<>();

        for (var x : curFunc.params) {
            varDef.put(x, null);
            varUseCnt.put(x, 1); // params just can't be removed
        }

        for (var block : curFunc.blocks.values()) {
            for (var ins : block.phiList) {
                if (ins.getDef() != null) {
                    varDef.put(ins.getDef(), ins);
                    varUseCnt.put(ins.getDef(), 0);
                }
            }
            for (var ins : block.insList) {
                if (ins.getDef() != null) {
                    varDef.put(ins.getDef(), ins);
                    varUseCnt.put(ins.getDef(), 0);
                }
            }
            if (block.endIns instanceof branchIns) {
                var ins = (branchIns) block.endIns;
                if (ins.trueLabel.equals(ins.falseLabel)) {
                    block.endIns = new jumpIns(ins.trueLabel);
                }
            } else if (block.endIns instanceof icmpbranchIns) {
                var ins = (icmpbranchIns) block.endIns;
                if (ins.trueLabel.equals(ins.falseLabel)) {
                    block.endIns = new jumpIns(ins.trueLabel);
                }
            }
            if (block.endIns.getDef() != null) {
                varDef.put(block.endIns.getDef(), block.endIns);
                varUseCnt.put(block.endIns.getDef(), 0);
            }
        }

        for (var block : curFunc.blocks.values()) {
            for (var ins : block.phiList) {
                ins.getUses().forEach(var -> varUseCnt.put(var, varUseCnt.getOrDefault(var, 0) + 1));
            }
            for (var ins : block.insList) {
                ins.getUses().forEach(var -> varUseCnt.put(var, varUseCnt.getOrDefault(var, 0) + 1));
            }
            block.endIns.getUses().forEach(var -> varUseCnt.put(var, varUseCnt.getOrDefault(var, 0) + 1));
        }

        Queue<IRvar> workList = new ArrayDeque<>();

        for (var var : varUseCnt.keySet()) {
            if (varDef.containsKey(var) && varUseCnt.get(var) == 0) {
                workList.add(var);
            }
        }

        while (!workList.isEmpty()) {
            IRvar var = workList.poll();
            IRIns def = varDef.get(var);
            if (def instanceof callIns) { // callIns can't be removed
                ((callIns)def).result = null;
                continue;
            }
            def.removed = true;
            for (var use : def.getUses()) {
                varUseCnt.put(use, varUseCnt.get(use) - 1);
                if (varUseCnt.get(use) == 0) {
                    workList.add(use);
                }
            }
        }

        for (var block : curFunc.blocks.values()) {
            block.insList.removeIf(ins -> ins.removed);
            block.phiList.removeIf(ins -> ins.removed);
        }

        // CHECK

        Set<IRvar> used = new HashSet<>();

        for (var block : curFunc.blocks.values()) {
            for (var ins : block.phiList) {
                ins.getUses().forEach(var -> used.add(var));
            }
            for (var ins : block.insList) {
                ins.getUses().forEach(var -> used.add(var));
            }
            block.endIns.getUses().forEach(var -> used.add(var));
        }

        for (var block : curFunc.blocks.values()) {
            block.phiList.forEach(phi -> {
                assert used.contains(phi.getDef());
            });
            block.insList.forEach(ins -> {
                if (ins.getDef() != null) {
                    assert used.contains(ins.getDef());
                }
            });
        }

        // Set<IRvar> used = new HashSet<>();

        // for (var block : CFG.blockList) {
        // for (var ins : block.phiList) {
        // ins.getUses().forEach(var -> used.add(var));
        // }
        // for (var ins : block.insList) {
        // ins.getUses().forEach(var -> used.add(var));
        // }
        // block.endIns.getUses().forEach(var -> used.add(var));
        // }

        // for (var block : CFG.blockList) {
        // block.phiList.removeIf(ins -> ins.getDef() != null &&
        // !used.contains(ins.getDef()));
        // block.insList
        // .removeIf(ins -> !(ins instanceof callIns) && ins.getDef() != null &&
        // !used.contains(ins.getDef()));
        // }
    }


}
