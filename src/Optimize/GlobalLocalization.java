package Optimize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.ins.allocaIns;
import IR.node.ins.callIns;
import IR.node.ins.loadIns;
import IR.node.ins.returnIns;
import IR.node.ins.storeIns;
import IR.type.IRType;
import Util.IRLabeler;

public class GlobalLocalization {
    IRRoot irRoot;
    HashMap<String, HashSet<String>> callEdge;
    HashMap<String, HashSet<IRvar>> haveStore;

    HashMap<String, tarjanInfo> tarjanMap;
    Stack<String> tarjanStack;
    int trajanNum;

    ArrayList<HashSet<String>> SCC;
    ArrayList<HashSet<Integer>> SCCEdge;
    ArrayList<HashSet<IRvar>> SCCgVars, SCCgVarsLocalized;


    HashMap<IRvar, IRType> gVarsType;
    int SSCNum;


    public GlobalLocalization(IRRoot irRoot) {
        this.irRoot = irRoot;
    }

    public void run() {
        var timer = Util.ExecutionTimer.timer;
        timer.start("GlobalLocalization");
        CallAnalysis();
        GvarsLocalize();
        timer.stop("GlobalLocalization");
    }

    void CallAnalysis() {
        InitCallEdge();
        TarjanSCC();
        haveStore = new HashMap<>();
        for (var func : irRoot.funcs) {
            int sccid = tarjanMap.get(func.name).belong;
            var set = new HashSet<IRvar>();
            for (var block : func.blocks.values()) {
                for (var ins : block.insList) {
                    if (ins instanceof loadIns) {
                        var load = (loadIns) ins;
                        if (load.pointer.isGlobal()) {
                            SCCgVars.get(sccid).add(load.pointer);
                        }
                    } else if (ins instanceof storeIns) {
                        var store = (storeIns) ins;
                        if (store.pointer.isGlobal()) {
                            set.add(store.pointer);
                            SCCgVars.get(sccid).add(store.pointer);
                        }
                        
                    }
                }
            }
            haveStore.put(func.name, set);
        }
        dfs(tarjanMap.get("@main").belong);
    }


    void dfs(int x) {
        SCCgVarsLocalized.get(x).addAll(SCCgVars.get(x));
        for (var y : SCCEdge.get(x)) {
            dfs(y);
            SCCgVarsLocalized.get(x).removeAll(SCCgVars.get(y));
            SCCgVars.get(x).addAll(SCCgVars.get(y));
        }
    }

    void GvarsLocalize() {
        gVarsType = new HashMap<>();
        for (var gvar : irRoot.gVars) {
            gVarsType.put(gvar.var, gvar.var.type);
        }

        for (var func : irRoot.funcs) {
            int sccid = tarjanMap.get(func.name).belong;
            if (SCC.get(sccid).size() > 1) continue;
            if (callEdge.get(func.name).contains(func.name)) continue;
            if (SCCgVarsLocalized.get(sccid).isEmpty()) continue;
            HashMap<IRvar, IRvar> map2local = new HashMap<>();

            for (var gvar : SCCgVarsLocalized.get(sccid)) {
                var local = new IRvar("%.global." + gvar.name.substring(1));
                func.entryBlock.insList.add(new allocaIns(local, gVarsType.get(gvar)));
                map2local.put(gvar, local);
            }

            for (var entry : map2local.entrySet()) {
                var tmpvar = new IRvar(gVarsType.get(entry.getKey()), IRLabeler.getIdLabel("%tmp"));
                func.entryBlock.insList.add(new loadIns(tmpvar, entry.getKey()));
                func.entryBlock.insList.add(new storeIns(tmpvar, entry.getValue()));
            }

            for (var block : func.blocks.values()) {
                if (block == func.entryBlock) continue;
                for (var ins : block.insList) {
                    if (ins instanceof loadIns) {
                        var load = (loadIns) ins;
                        if (map2local.containsKey(load.pointer)) {
                            load.pointer = map2local.get(load.pointer);
                        }
                    } else if (ins instanceof storeIns) {
                        var store = (storeIns) ins;
                        if (map2local.containsKey(store.pointer)) {
                            store.pointer = map2local.get(store.pointer);
                        }
                    }
                }
                if (block.endIns instanceof returnIns) {
                    for (var entry : map2local.entrySet()) {
                        if (haveStore.get(func.name).contains(entry.getKey())) {
                            var tmpvar = new IRvar(gVarsType.get(entry.getKey()), IRLabeler.getIdLabel("%tmp"));
                            block.insList.add(new loadIns(tmpvar, entry.getValue()));
                            block.insList.add(new storeIns(tmpvar, entry.getKey()));
                        }
                    }
                }
            }
        }
    }

    void InitCallEdge() {
        callEdge = new HashMap<>();
        for (var func : irRoot.funcs) {
            var set = new HashSet<String>();
            for (var block : func.blocks.values()) {
                for (var ins : block.insList) {
                    if (ins instanceof callIns) {
                        String funcName = "@" + ((callIns) ins).func;
                        if (funcName.equals("@__mx_global_init")) continue;
                        set.add(funcName);
                    }
                }
            }
            callEdge.put(func.name, set);
        }
    }

    void TarjanSCC() {
        tarjanMap = new HashMap<>();
        trajanNum = 0;
        tarjanStack = new Stack<>();
        SSCNum = 0;
        SCC = new ArrayList<>();
        SCCEdge = new ArrayList<>();
        SCCgVars = new ArrayList<>();
        SCCgVarsLocalized = new ArrayList<>();
        for (var func : irRoot.funcs) {
            String funcName = func.name;
            if (!tarjanMap.containsKey(funcName)) {
                Tarjan(funcName);
            }
        }

        for (var func : irRoot.funcs) {
            String x = func.name;
            for (var y : callEdge.get(x)) {
                if (tarjanMap.get(x).belong != tarjanMap.get(y).belong) {
                    SCCEdge.get(tarjanMap.get(x).belong).add(tarjanMap.get(y).belong);
                }
            }
        }
    }
    
    void Tarjan(String x) {
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
            var set = new HashSet<String>();
            String y;
            do {
                y = tarjanStack.pop();
                tarjanMap.get(y).inStack = false;
                tarjanMap.get(y).belong = SSCNum;
                set.add(y);
            } while (!x.equals(y));
            SCC.add(set);
            SCCEdge.add(new HashSet<>());
            SCCgVars.add(new HashSet<>());
            SCCgVarsLocalized.add(new HashSet<>());
            ++SSCNum;
        }
    }

}

class tarjanInfo {
    public int dfn;
    public int low;
    public boolean inStack;
    public int belong;

    public tarjanInfo(int num) {
        dfn = low = num;
        inStack = true;
        belong = -1;
    }

}