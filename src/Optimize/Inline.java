package Optimize;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import IR.item.IRitem;
import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.IRIns;
import IR.node.ins.callIns;
import IR.node.ins.icmpbranchIns;
import IR.node.ins.jumpIns;
import IR.node.ins.returnIns;
import IR.type.IRType;
import Util.IRLabeler;
import builtin.Builtin;

public class Inline {
    IRRoot irRoot;
    HashMap<IRFuncDef, HashSet<IRFuncDef>> callEdge;

    HashMap<IRFuncDef, tarjanInfo> tarjanMap;
    Stack<IRFuncDef> tarjanStack;
    int trajanNum;

    ArrayList<HashSet<IRFuncDef>> SCC;
    ArrayList<HashSet<Integer>> SCCEdge;

    HashMap<String, IRFuncDef> funcMap;

    int SSCNum;


    public Inline(IRRoot irRoot) {
        this.irRoot = irRoot;
    }

    public void run() {
        var timer = Util.ExecutionTimer.timer;
        timer.start("Inline");
        CallAnalysis();
        InlineFunc();
        timer.stop("Inline");
    }

    void CallAnalysis() {
        InitCallEdge();
        TarjanSCC(); // After this, Call Graph becomes DAG
    }

    void InitCallEdge() {
        callEdge = new HashMap<>();
        funcMap = new HashMap<>();
        
        for (var func : irRoot.funcs) {
            funcMap.put(func.name, func);
        }

        for (var func : irRoot.funcs) {
            var set = new HashSet<IRFuncDef>();
            for (var block : func.blockList) {
                for (var ins : block.insList) {
                    if (ins instanceof callIns) {
                        String funcName = "@" + ((callIns) ins).func;
                        if (Builtin.isBuiltinFunc(funcName)) continue;
                        set.add(funcMap.get(funcName));
                    }
                }
            }
            callEdge.put(func, set);
        }
    }

    void InlineFunc() {
        dfs(tarjanMap.get(funcMap.get("@main")).belong, new HashSet<>());
    }

    void dfs(int x, HashSet<Integer> visited) {
        if (visited.contains(x)) return;
        visited.add(x);
        for (var y : SCCEdge.get(x)) {
            dfs(y, visited);
        }
        for (var func : SCC.get(x)) {
            for (int i = 0; i < func.blockList.size(); ++i) {
                var block = func.blockList.get(i);
                for (int j = 0; j < block.insList.size(); ++j) {
                    var ins = block.insList.get(j);
                    if (ins instanceof callIns) {
                        var call = (callIns) ins;
                        var cfunc = funcMap.get("@" + call.func);
                        if (cfunc == null) {
                            assert Builtin.isBuiltinFunc("@" + call.func);
                            continue;
                        }
                        if (tarjanMap.get(func).belong != tarjanMap.get(cfunc).belong) {
                            func.inlineDepth = Math.max(func.inlineDepth, cfunc.inlineDepth + 1);
                            var sblock = block.splitBlock(j + 1);
                            var sblockList = DumpFunc(cfunc, call.args, sblock, call.result);
                            block.insList.removeLast();
                            block.endIns = new jumpIns(sblockList.getFirst().getLabel());
                            func.blockList.addAll(i + 1, sblockList);
                            sblockList.forEach(Block -> {
                                func.blocks.put(Block.Label, Block);
                            });
                            i += sblockList.size();
                            break;
                        }
                    }
                }
            }
        }
    }

    HashMap<IRitem, IRitem> renameItem;
    HashMap<String, String> renameLabel;

    private void SetRename(IRIns ins) {
        ins.getUses().forEach(IRvar -> {
            if (!renameItem.containsKey(IRvar)) {
                IRvar newVar = new IRvar(IRvar.type, IRLabeler.getIdLabel(IRvar.name + ".il"));
                renameItem.put(IRvar, newVar);
            }
        });
        if (ins.getDef() != null) {
            if (!renameItem.containsKey(ins.getDef())) {
                IRvar newVar = new IRvar(ins.getDef().type, IRLabeler.getIdLabel(ins.getDef().name + ".il"));
                renameItem.put(ins.getDef(), newVar);
            }
        }
    }

    private void GetRename(IRIns ins) {
        ins.replaceDef(renameItem);
        ins.replaceUse(renameItem);
        ins.replaceLabel(renameLabel);
        if (ins instanceof icmpbranchIns) {
            var tins = (icmpbranchIns) ins;
            tins.llvmirTmpVar = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel(tins.llvmirTmpVar.name + ".il"));
        }
    }

    private ArrayList<IRblock> DumpFunc(IRFuncDef func, ArrayList<IRitem> args, IRblock retBlock, IRvar retVar) {
        ArrayList<IRblock> blockList = new ArrayList<>();
        for (var block : func.blockList) {
            blockList.add(block.clone());
        }
        renameLabel = new HashMap<>();
        renameItem = new HashMap<>();
        for (int i = 0; i < args.size(); ++i) {
            renameItem.put(func.params.get(i), args.get(i));
        }
        for (var block : blockList) {
            renameLabel.put(block.Label, IRLabeler.getIdLabel(block.Label + ".il"));
            block.Label = renameLabel.get(block.Label);
            for (var phi : block.phiList) SetRename(phi);
            for (var ins : block.insList) SetRename(ins);
            SetRename(block.endIns);
            if (block.endIns instanceof returnIns && retVar != null) {
                renameItem.put(((returnIns) block.endIns).value, retVar);
            }
        }
        for (var block : blockList) {
            for (var phi : block.phiList) GetRename(phi);
            for (var ins : block.insList) GetRename(ins);
            GetRename(block.endIns);
            if (block.endIns instanceof returnIns) {
                block.endIns = new jumpIns(retBlock.getLabel());
            }
        }
        blockList.add(retBlock);
        return blockList;
    }



    void TarjanSCC() {
        tarjanMap = new HashMap<>();
        trajanNum = 0;
        tarjanStack = new Stack<>();
        SSCNum = 0;
        SCC = new ArrayList<>();
        SCCEdge = new ArrayList<>();
        for (var func : irRoot.funcs) {
            if (!tarjanMap.containsKey(func)) {
                Tarjan(func);
            }
        }

        for (var x : irRoot.funcs) {
            for (var y : callEdge.get(x)) {
                if (tarjanMap.get(x).belong != tarjanMap.get(y).belong) {
                    SCCEdge.get(tarjanMap.get(x).belong).add(tarjanMap.get(y).belong);
                }
            }
        }
    }
    
    void Tarjan(IRFuncDef x) {
        var info = new tarjanInfo(++trajanNum);
        tarjanMap.put(x, info);
        tarjanStack.push(x);
        if (callEdge.containsKey(x))  {
            for (var y : callEdge.get(x)) {
                if (!tarjanMap.containsKey(y)) {
                    Tarjan(y);
                    info.low = Math.min(info.low, tarjanMap.get(y).low);
                } else if (tarjanMap.get(y).inStack) {
                    info.low = Math.min(info.low, tarjanMap.get(y).dfn);
                }
            }
        }
        if (info.dfn == info.low) {
            var set = new HashSet<IRFuncDef>();
            IRFuncDef y;
            do {
                y = tarjanStack.pop();
                tarjanMap.get(y).inStack = false;
                tarjanMap.get(y).belong = SSCNum;
                set.add(y);
            } while (!x.equals(y));
            SCC.add(set);
            SCCEdge.add(new HashSet<>());
            ++SSCNum;
        }
    }


    
}


// class tarjanInfo {
//     public int dfn;
//     public int low;
//     public boolean inStack;
//     public int belong;

//     public tarjanInfo(int num) {
//         dfn = low = num;
//         inStack = true;
//         belong = -1;
//     }

// }