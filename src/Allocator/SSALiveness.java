package Allocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.IRIns;
import IR.node.ins.phiIns;
import Util.ExecutionTimer;

public class SSALiveness {
    IRRoot irRoot;
    IRFuncDef curFunc;
    HashMap<IRIns, IRIns> prevIns = new HashMap<>();
    HashMap<IRIns, IRblock> blockOfIns = new HashMap<>();
    HashMap<IRvar, List<IRIns>> useOfVar = new HashMap<>();
    HashSet<IRblock> visited = new HashSet<>();
    
    int totalUse = 0;
    
    public SSALiveness(IRRoot irRoot) {
        this.irRoot = irRoot;
    }
    
    public void run() {
        var timer = ExecutionTimer.timer;
        timer.start("Liveness");

        for (var func : irRoot.funcs) {
            curFunc = func;
            timer.start("SSA Liveness init");
            init(func);
            timer.stop("SSA Liveness init");
            timer.start("SSA Liveness main : " + func.name);
            ssaLiveness();
            timer.stop("SSA Liveness main : " + func.name);
        }
        timer.stop("Liveness");

    }

    void getUseOfVar(IRIns ins) {
        ins.getUses().forEach(var -> {
            if (!useOfVar.containsKey(var)) {
                useOfVar.put(var, new ArrayList<>());
            }
            useOfVar.get(var).add(ins);
            totalUse++;
        });

    }

    void init(IRFuncDef func) {
        prevIns.clear();
        blockOfIns.clear();
        useOfVar.clear();
        totalUse = 0;
        for (var block : func.blocks.values()) {
            for (int i = 0; i < block.insList.size(); i++) {
                var ins = block.insList.get(i);
                if (i > 0){
                    prevIns.put(ins, block.insList.get(i - 1));
                }
                ins.liveIn = new HashSet<>();
                ins.liveOut = new HashSet<>();
                blockOfIns.put(ins, block);
                getUseOfVar(ins);
            }
            if (block.insList.size() > 0) {
                prevIns.put(block.endIns, block.insList.getLast());
            }
            blockOfIns.put(block.endIns, block);
            block.endIns.liveIn = new HashSet<>();
            block.endIns.liveOut = new HashSet<>();
            getUseOfVar(block.endIns);

            var phiLiveIn = new HashSet<IRvar>();
            var phiLiveOut = new HashSet<IRvar>();
            for (var phi : block.phiList) {
                blockOfIns.put(phi, block);
                phi.liveIn = phiLiveIn;
                phi.liveOut = phiLiveOut;
                getUseOfVar(phi);
            }
        }
    }

    void ssaLiveness() {
        for (var entry : useOfVar.entrySet()) {
            visited.clear();
            IRvar var = entry.getKey();
            for (var ins : entry.getValue()) {
                if (ins instanceof phiIns) {
                    phiIns phi = (phiIns) ins;
                    for (int idx = 0; idx < phi.values.size(); ++idx) {
                        if (var.equals(phi.values.get(idx).value)) {
                            IRblock pre = curFunc.blocks.get(phi.values.get(idx).label);
                            scanBlock(pre, var);
                        }
                    }
                } else {
                    scanliveIn(ins, var);
                }
            }
        }
    }

    void scanliveIn(IRIns ins, IRvar var) {
        ins.liveIn.add(var);
        var prev = prevIns.get(ins);
        if (prev == null) {
            // first ins in block, we should handle phiIns
            var block = blockOfIns.get(ins);
            boolean hasPhiDef = false;
            for (var phi : block.phiList) {
                if (phi.getDef().equals(var)) {
                    hasPhiDef = true;
                    break;
                }
            }
            // all phiIns happens in the same time

            if (!block.phiList.isEmpty()) block.phiList.getFirst().liveOut.add(var);

            if (!hasPhiDef) {
                if (!block.phiList.isEmpty()) block.phiList.getFirst().liveIn.add(var);
                for (var pre : block.getPrevBlocks()) {
                    scanBlock(pre, var);
                }
            }
        } else {
            scanlivesOut(prev, var);
        }
    }

    void scanlivesOut(IRIns ins, IRvar var) {
        ins.liveOut.add(var);
        IRvar def = ins.getDef();
        if (def == null || !def.equals(var)) {
            scanliveIn(ins, var);
        }
    }

    void scanBlock(IRblock block, IRvar var) {
        if (visited.contains(block))
            return;
        visited.add(block);
        scanlivesOut( block.endIns, var);
    }

}
