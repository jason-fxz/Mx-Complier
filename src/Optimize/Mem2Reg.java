package Optimize;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import IR.item.IRitem;
import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.IRIns;
import IR.node.ins.allocaIns;
import IR.node.ins.branchIns;
import IR.node.ins.callIns;
import IR.node.ins.jumpIns;
import IR.node.ins.loadIns;
import IR.node.ins.phiIns;
import IR.node.ins.storeIns;
import Util.IRLabeler;

public class Mem2Reg {
    private CFGBuilder CFG;
    
    public IRRoot root;
    public IRFuncDef curFunc;

    private Set<IRvar> allocVar;

    // Phi
    private Map<IRvar, Set<IRblock>> phiPosition;

    private Map<IRitem, IRitem> replaceMap;

    public Mem2Reg(IRRoot root) {
        curFunc = null;
        this.root = root;
        CFG = new CFGBuilder();
    }

    void addAlloc(IRvar ptr) {
        allocVar.add(ptr);
    }

    boolean isAlloc(IRvar ptr) {
        return allocVar.contains(ptr);
    }

    private void phiInsertPositions(IRFuncDef funcDef) {
        phiPosition = new HashMap<>();
        Map<IRvar, Set<IRblock>> block_contain_store = new HashMap<>();
        var allocVar = funcDef.getAllocas();

        for (IRvar var : allocVar) {
            phiPosition.put(var, new HashSet<>());
            block_contain_store.put(var, new HashSet<>());
            addAlloc(var);
        }

        for (IRblock block : CFG.blockList) {
            for (IRIns ins : block.insList) {
                if (ins instanceof storeIns) {
                    IRvar ptr = ((storeIns) ins).pointer;
                    if (block_contain_store.containsKey(ptr)) {
                        block_contain_store.get(ptr).add(block);
                    }
                }
            }
        }

        for (IRvar var : allocVar) {
            Set<IRblock> visited = block_contain_store.get(var);
            Queue<IRblock> extandlist = new ArrayDeque<>(visited);
            while (!extandlist.isEmpty()) {
                var cur = extandlist.poll();
                for (var frontier : CFG.domFrontiers.get(cur)) {
                    phiPosition.get(var).add(frontier);
                    if (!visited.contains(frontier)) {
                        visited.add(frontier);
                        extandlist.add(frontier);
                    }
                }
            }
        }
    }

    private void insertPhi() {
        for (IRvar var : allocVar) {
            for (IRblock block : phiPosition.get(var)) {
                IRvar phiVar = new IRvar(var.type, IRLabeler.getIdLabel(var.toString()));
                phiIns phi = new phiIns(phiVar);
                phi.var = var;
                block.phiList.add(phi);
            }
        }

    }

    Pair<IRblock, IRitem> findVarValue(IRvar var, Stack<Map<IRvar, Pair<IRblock, IRitem>>> curVarValue) {
        for (int i = curVarValue.size() - 1; i >= 0; i--) {
            if (curVarValue.get(i).containsKey(var)) {
                return curVarValue.get(i).get(var);
            }
        }
        return null;
    }



    void replaceVar(IRblock block, Set<IRblock> visited, Stack<Map<IRvar, Pair<IRblock, IRitem>>> curVarValue, IRblock preBlock) {
        for (var phi : block.phiList) {
            var cur = findVarValue(phi.var, curVarValue);
            curVarValue.peek().put(phi.var, new Pair<>(block, phi.result));
            if (cur == null) {
                phi.addValue(null, preBlock.Label);
            } else {
                phi.addValue(cur.second, preBlock.Label);
            }
        }

        if (visited.contains(block)) {
            return;
        }
        visited.add(block); 
        for (var ins : block.insList) {
            if (ins instanceof loadIns) {
                IRvar ptr = ((loadIns) ins).pointer;
                if (isAlloc(ptr)) {
                    var cur = findVarValue(ptr, curVarValue);
                    ins.removed = true;
                    if (cur == null) {
                        // continue;
                        throw new RuntimeException("replaceVar: load.ptr not found");
                    } 
                    IRitem tmpOld = ((loadIns) ins).result;
                    IRitem tmpNew = cur.second;
                    if (replaceMap.containsKey(tmpNew)) {
                        replaceMap.put(tmpOld, replaceMap.get(tmpNew));
                    } else {
                        replaceMap.put(tmpOld, tmpNew);
                    }
                }
            } else if (ins instanceof storeIns) {
                IRvar ptr = ((storeIns) ins).pointer;
                if (isAlloc(ptr)) {
                    curVarValue.peek().put(ptr, new Pair<IRblock, IRitem>(block, ((storeIns) ins).value));
                    ins.removed = true;
                }
            } else if (ins instanceof allocaIns) {
                ins.removed = true;
            }
        }

        if (block.endIns instanceof jumpIns) {
            IRblock nextBlock = curFunc.blocks.get(((jumpIns) block.endIns).label);
            replaceVar(nextBlock, visited, curVarValue, block);
        } else if (block.endIns instanceof branchIns) {
            IRblock trueBlock = curFunc.blocks.get(((branchIns) block.endIns).trueLabel);
            IRblock falseBlock = curFunc.blocks.get(((branchIns) block.endIns).falseLabel);

            curVarValue.push(new HashMap<>());
            replaceVar(trueBlock, visited, curVarValue, block);
            curVarValue.pop();

            curVarValue.push(new HashMap<>());
            replaceVar(falseBlock, visited, curVarValue, block);
            curVarValue.pop();
        }
    }

    void tidyUp() {
        for (var block : CFG.blockList) {
            block.insList.removeIf(ins -> ins.removed);
            block.phiList.forEach(phi -> {
                block.insList.add(0, phi);
            });
            
            block.insList.forEach(ins -> ins.replaceUse(replaceMap));
            block.endIns.replaceUse(replaceMap);
        }
    }

    void removeUnuseReg() {
        Set<IRvar> used = new HashSet<>();

        for (var block : CFG.blockList) {
            for (var ins : block.phiList) {
                ins.getUses().forEach(var -> used.add(var));
            }
            for (var ins : block.insList) {
                ins.getUses().forEach(var -> used.add(var));
            }
            block.endIns.getUses().forEach(var -> used.add(var));
        }

        for (var block : CFG.blockList) {
            block.phiList.removeIf(ins -> ins.getDef() != null && !used.contains(ins.getDef()));
            block.insList.removeIf(ins -> !(ins instanceof callIns) && ins.getDef() != null && !used.contains(ins.getDef()));
        }        
    }

    void visitFunc(IRFuncDef funcDef) {
        allocVar = new HashSet<>();
        replaceMap = new HashMap<>();

        curFunc = funcDef;
        
        CFG.build(funcDef);

        phiInsertPositions(funcDef);
        insertPhi();

        Stack<Map<IRvar, Pair<IRblock, IRitem>>> valueStack = new Stack<>();
        valueStack.push(new HashMap<>());

        replaceVar(funcDef.entryBlock, new HashSet<>(), valueStack, null);
        tidyUp();

        removeUnuseReg();

    }

    public void run() {
        for (var funcDef : root.funcs) {
            visitFunc(funcDef);
        }
    }

}

class Pair<T1, T2> {
    public final T1 first;
    public final T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair<?, ?> other = (Pair<?, ?>) obj;
            return first.equals(other.first) && second.equals(other.second);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ", " + second.toString() + ")";
    }

}