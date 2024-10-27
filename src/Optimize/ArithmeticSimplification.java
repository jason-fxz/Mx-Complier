package Optimize;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;


import IR.item.IRLiteral;
import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.IRIns;
import IR.node.ins.arithIns;
import Util.IRLabeler;

public class ArithmeticSimplification {
    IRRoot irRoot;
    
    public ArithmeticSimplification(IRRoot irRoot) {
        this.irRoot = irRoot;
    }

    public void run() {
        var timer = Util.ExecutionTimer.timer;
        timer.start("ArithmeticSimplification");
        for (var func : irRoot.funcs) {
            instcombine(func);
        }
        timer.stop("ArithmeticSimplification");
    }

    void setConst2Right(arithIns ins) {
        switch (ins.op) {
            case "add": case "mul": case "and": case "or": case "xor":
                if (ins.lhs instanceof IRLiteral && ins.rhs instanceof IRvar) {
                    var tmp = ins.lhs;
                    ins.lhs = ins.rhs;
                    ins.rhs = tmp;
                }
                break;
            default:
                break;
        }
    }

    void instcombine(IRFuncDef func) {
        Map<IRvar, List<InsRef>> varUses = new HashMap<>();
        Map<IRvar, InsRef> varDef = new HashMap<>();
        
        Queue<InsRef> workList = new ArrayDeque<>();


        for (var block : func.blocks.values()) {
            ArrayList<IRIns> replaceList = new ArrayList<>();
            for (int i = 0; i < block.insList.size(); ++i) {
                if (block.insList.get(i) instanceof arithIns) {
                    var ins = (arithIns) block.insList.get(i);
                    setConst2Right(ins);
                    switch (ins.op) {
                        case "add":
                            if (ins.lhs.equals(ins.rhs)) {
                                replaceList.add(new arithIns(ins.result, "shl", ins.lhs, new IRLiteral("1")));
                            } else replaceList.add(ins);
                            break;
                        case "sub":
                            if (ins.rhs instanceof IRLiteral) {
                                int C = ((IRLiteral)ins.rhs).getInt();
                                replaceList.add(new arithIns(ins.result, "add", ins.lhs, new IRLiteral(String.valueOf(-C))));
                            } else replaceList.add(ins);
                            break;
                        case "mul":
                            if (ins.rhs instanceof IRLiteral) {
                                int C = ((IRLiteral)ins.rhs).getInt();
                                if (C == -1) {
                                    replaceList.add(new arithIns(ins.result, "sub", new IRLiteral("0"), ins.lhs));
                                } else if (C == 0) {
                                    replaceList.add(new arithIns(ins.result, "xor", new IRLiteral("0") , new IRLiteral("0")));  
                                } else if (C > 0) {
                                    if (Integer.bitCount(C) == 1) { // Check if mulc is a power of 2
                                        int shiftAmount = Integer.numberOfTrailingZeros(C);
                                        replaceList.add(new arithIns(ins.result, "shl", ins.lhs, new IRLiteral(String.valueOf(shiftAmount))));
                                    } else replaceList.add(ins);
                                } else if (C < 0) {
                                    int CC = -C;
                                    if (Integer.bitCount(CC) == 1) {
                                        int shiftAmount = Integer.numberOfTrailingZeros(CC);
                                        var tmpvar = new IRvar(IRLabeler.getIdLabel("%mul.tmp"));
                                        replaceList.add(new arithIns(tmpvar, "sub", new IRLiteral("0"), ins.lhs));
                                        replaceList.add(new arithIns(ins.result, "shl", tmpvar, new IRLiteral(String.valueOf(shiftAmount))));
                                    } else replaceList.add(ins);
                                } else {
                                    replaceList.add(ins);
                                }
                            } else replaceList.add(ins);
                            break;
                        default:
                            replaceList.add(ins);
                            break;
                    }
                } else {
                    replaceList.add(block.insList.get(i));
                }
            }
            block.insList = replaceList;
        }

        for (var block : func.blocks.values()) {
            for (int i = 0; i < block.insList.size(); ++i) {
                var ins = block.insList.get(i);
                if (ins instanceof arithIns) {
                    var arith = (arithIns) ins;
                    if (arith.rhs instanceof IRLiteral && arith.lhs instanceof IRvar) {
                        var ref = new InsRef(block, i);
                        varDef.put(arith.result, ref);
                        varUses.putIfAbsent((IRvar)arith.lhs, new ArrayList<>());
                        varUses.get(arith.lhs).add(ref);
                    }
                }
            }
        }


        for (var entry : varUses.entrySet()) {
            var val = entry.getKey();
            if (!varDef.containsKey(val)) {
                for (var ref : entry.getValue()) {
                    workList.add(ref);
                }
            }
        }

        while (!workList.isEmpty()) {
            // Y = X + C1    Ins1
            // Z = Y + C2    Ins2
            // => Z = X + (C1 + C2)            
            var Ins1 = workList.poll();
            var X = Ins1.ins().lhs;
            var Y = Ins1.ins().result;
            int C1 = ((IRLiteral)Ins1.ins().rhs).getInt();
            if (!varUses.containsKey(Y)) continue;
            for (var Ins2 : varUses.get(Y)) {
                assert Ins2.ins().lhs.equals(Y);
                var Z = Ins2.ins().result;
                int C2 = ((IRLiteral)Ins2.ins().rhs).getInt();
                
                if (Ins1.ins().op.equals(Ins2.ins().op)) {
                    switch (Ins1.ins().op) {
                        case "add" -> {
                            Ins2.replace(new arithIns(Z, "add", X, new IRLiteral(String.valueOf(C1 + C2))));
                        }
                        case "mul" -> {
                            Ins2.replace(new arithIns(Z, "mul", X, new IRLiteral(String.valueOf(C1 * C2))));
                        }
                        case "and" -> {
                            Ins2.replace(new arithIns(Z, "and", X, new IRLiteral(String.valueOf(C1 & C2))));
                        }
                        case "or" -> {
                            Ins2.replace(new arithIns(Z, "or", X, new IRLiteral(String.valueOf(C1 | C2))));
                        }
                        case "xor" -> {
                            Ins2.replace(new arithIns(Z, "xor", X, new IRLiteral(String.valueOf(C1 ^ C2))));
                        }
                        default -> {
                            break;
                        }
                    }
                }
                workList.add(Ins2);
            }
        }


        // 摆了，写不动。
        
    }
}


class InsRef {
    IRblock block;
    int index;

    public InsRef(IRblock block, int index) {
        this.block = block;
        this.index = index;
    }

    public arithIns ins() {
        return (arithIns) block.insList.get(index);
    }

    public void replace(arithIns newIns) {
        block.insList.set(index, newIns);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InsRef) {
            var ref = (InsRef) obj;
            return ref.block == block && ref.index == index;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return block.hashCode() ^ index;
    }
}