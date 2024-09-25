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
import Optimize.CFGBuilder;

public class SSAalloctor {
    CFGBuilder CFG;
    IRRoot irRoot;
    IRFuncDef curFunc;

    HashMap<IRvar, List<IRIns>> defuseOfVar = new HashMap<>();
    Map<IRvar, Integer> usageCount = new HashMap<>();

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
        vars.sort((a, b) -> usageCount.get(b) - usageCount.get(a));
        int n = vars.size() - MAX_ALLOC_REG;
        for (int i = 0; i < n; i++) {
            // spill vars.get(i)
            curFunc.spilledVar.put(vars.get(i), curFunc.spilledVar.size());
        }

    }

    private void SpillIns(IRIns ins) {
        ins.liveOut.removeIf(var -> curFunc.spilledVar.containsKey(var));
        ins.liveIn.removeIf(var -> curFunc.spilledVar.containsKey(var));

        if (ins.liveOut.size() > MAX_ALLOC_REG) {
            Spillvars(new ArrayList<>(ins.liveOut));
        }
    }

    private void countInsUsage(IRIns ins) {
        ins.getUses().forEach(var -> {
            usageCount.put(var, usageCount.getOrDefault(var, 0) + 1);
            if (!defuseOfVar.containsKey(var))
                defuseOfVar.put(var, new ArrayList<>());
            defuseOfVar.get(var).add(ins);
        });
    }

    void SSASpill() {
        // count the usage of each var & get def use IRvar
        usageCount.clear();
        for (var block : curFunc.blocks.values()) {
            block.phiList.forEach(ins -> countInsUsage(ins));
            block.insList.forEach(ins -> countInsUsage(ins));
            countInsUsage(block.endIns);
        }

        // check if need to spill
        curFunc.spilledVar = new HashMap<>();
        for (var block : curFunc.blocks.values()) {
            block.phiList.forEach(ins -> SpillIns(ins));
            block.insList.forEach(ins -> SpillIns(ins));
            SpillIns(block.endIns);
        }
    }

    void SSAColor() {
        // coloring
        CFG.build(curFunc);
        
        curFunc.regOfVar = new HashMap<>();
        // handle function params
        for (var x : curFunc.entryBlock.getLiveIn()) {
            curFunc.regOfVar.put(x, getFreeReg());
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
            // throw new RuntimeException("delReg : reg == null");
            return;
        }
        inUse.remove(reg);
        freeReg.add(reg);
    }

    void colorIns(IRIns ins) {
        for (var x : ins.getUses()) {
            if (!curFunc.spilledVar.containsKey(x) && !ins.liveOut.contains(x)) {
                delReg(curFunc.regOfVar.get(x));
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
            inUse.add(curFunc.regOfVar.get(x));
        }
        for (int i = 0; i < MAX_ALLOC_REG; i++) {
            if (!inUse.contains(i)) freeReg.add(i);
        }


        for (var ins : block.phiList)
            colorIns(ins);
        for (var ins : block.insList)
            colorIns(ins);
        colorIns(block.endIns);

        for (var child : CFG.domChildren.get(block)) {
            preOrderDFS(child);
        }
    }



}
