package Allocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IR.item.IRvar;
import IR.node.IRRoot;
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

    public SSAalloctor(IRRoot irRoot) {
        this.irRoot = irRoot;
        CFG = new CFGBuilder(irRoot);
    }

    public void run() {
        // spilling
        for (var func : irRoot.funcs) {
            curFunc = func;
            SSASpill();
        }

        // coloring
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
            if (defuseOfVar.containsKey(var))
                defuseOfVar.get(var).add(ins);
            else
                defuseOfVar.put(var, List.of(ins));
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

    





}
