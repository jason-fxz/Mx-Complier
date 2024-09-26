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

public class SSALiveness {
    IRRoot irRoot;
    IRFuncDef curFunc;
    HashMap<IRIns, IRIns> prevIns = new HashMap<>();
    HashMap<IRIns, IRblock> blockOfIns = new HashMap<>();
    HashMap<IRvar, List<IRIns>> useOfVar = new HashMap<>();
    HashSet<IRblock> visited = new HashSet<>();

    public SSALiveness(IRRoot irRoot) {
        this.irRoot = irRoot;
    }

    public void run() {
        for (var func : irRoot.funcs) {
            curFunc = func;
            init(func);
            ssaLiveness();
        }
        
    }

    void getUseOfVar(IRIns ins) {
        ins.getUses().forEach(var -> {
            if (!useOfVar.containsKey(var)) {
                useOfVar.put(var, new ArrayList<>());
            }
            useOfVar.get(var).add(ins);
        });

    }

    void init(IRFuncDef func) {
        prevIns.clear();
        blockOfIns.clear();
        useOfVar.clear();
        for (var block : func.blocks.values()) {
            for (int i = 0; i < block.insList.size(); i++) {
                if (i > 0){
                    prevIns.put(block.insList.get(i), block.insList.get(i - 1));
                }
                blockOfIns.put(block.insList.get(i), block);
                getUseOfVar(block.insList.get(i));
            }
            if (block.insList.size() > 0) {
                prevIns.put(block.endIns, block.insList.getLast());
            }
            blockOfIns.put(block.endIns, block);
            getUseOfVar(block.endIns);

            for (var phi : block.phiList) {
                blockOfIns.put(phi, block);
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
                    int idx = 0;
                    while (!var.equals(phi.values.get(idx).value)) ++idx;
                    
                    // IRblock cur = blockOfIns.get(phi);
                    // cur.phiList.forEach(p -> {
                    //     p.liveIn.add(var);
                    // });

                    IRblock pre = curFunc.blocks.get(phi.values.get(idx).label);
                    scanBlock(pre, var);
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
            block.phiList.forEach(phi -> {
                phi.liveOut.add(var);
            });
            if (!hasPhiDef) {
                block.phiList.forEach(phi -> {
                    phi.liveIn.add(var);
                });
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
        var ins = block.endIns;
        scanlivesOut(ins, var);
    }

}
