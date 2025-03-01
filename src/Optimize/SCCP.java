package Optimize;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import IR.item.IRLiteral;
import IR.item.IRitem;
import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.*;
import IR.type.IRType;

public class SCCP {
    IRRoot irRoot;

    IRFuncDef curFunc;
    Queue<Edge> edgesQueue = new ArrayDeque<>();  // worklist for edges (IRblock -> IRblock)
    Queue<IRIns> usesQueue = new ArrayDeque<>();  // worklist for expr
    Set<Edge> visitedEdges = new HashSet<>();
    Set<IRblock> visitedBlock = new HashSet<>();
    Map<IRvar, Lattice> varLattice = new HashMap<>();  // var -> lattice
    Map<IRvar, List<IRIns>> varUses = new HashMap<>();  // var -> var uses List<IRIns>
    Map<IRvar, List<IRblock>> varBlock = new HashMap<>();  // var -> var def IRblock
    
    Map<phiIns, IRblock> phi2Block = new HashMap<>();  // phi -> block


    public void run() {
        var timer = Util.ExecutionTimer.timer;
        timer.start("SCCP");
        for (var func : irRoot.funcs) {
            sccp(func);
        }
        timer.stop("SCCP");
    }

    public SCCP(IRRoot irRoot) {
        this.irRoot = irRoot;
    }
    
    public void sccp(IRFuncDef func) {
        curFunc = func;

        edgesQueue.clear();
        usesQueue.clear();
        visitedEdges.clear();
        visitedBlock.clear();
        varLattice.clear();
        varUses.clear();
        varBlock.clear();
        phi2Block.clear();

        buildDefUses();

        edgesQueue.add(new Edge(null, curFunc.entryBlock));

        while (true) {
            if (!edgesQueue.isEmpty()) {
                Edge edge = edgesQueue.poll();
                if (visitedEdges.contains(edge)) {
                    continue;
                }
                boolean haveVisited = visitedBlock.contains(edge.to);
                
                visitedEdges.add(edge);
                visitedBlock.add(edge.to);

                for (var ins : edge.to.phiList) {
                    processIns(ins);
                }
                if (!haveVisited) {
                    for (var ins : edge.to.insList) {
                        processIns(ins);
                    }
                }
                propagate(edge.to);
                continue;
            }

            if (!usesQueue.isEmpty()) {
                IRIns ins = usesQueue.poll();
                processIns(ins);
                continue;
            }

            break;
        }

        replaceConst();
    }

    // meet all phi args
    Lattice meetphi(phiIns phi) {
        var optimisticLt = new Lattice(latticeType.TOP, null);
        // we optimisticly assume all the non visited phi args are TOP
        for (var arg : phi.values) {
            var edge = new Edge(curFunc.blocks.get(arg.label), phi2Block.get(phi));
            if (visitedEdges.contains(edge)) {
                var lt = getLattice(arg.value);
                if (lt.type == latticeType.CONST) {
                    if (optimisticLt.type == latticeType.TOP) {
                        optimisticLt = lt;
                    } else { 
                        if (!optimisticLt.value.equals(lt.value)) {
                            return new Lattice(latticeType.BOTTOM, null);
                        }
                    }
                } else if (lt.type == latticeType.BOTTOM) {
                    return new Lattice(latticeType.BOTTOM, null);
                }
            }
        }
        return optimisticLt;
    }

    Lattice computeArith(String op, IRLiteral lhs, IRLiteral rhs) {
        IRLiteral res = new IRLiteral(lhs.type);
        if (op.equals("add")) {
            res.value = String.valueOf(lhs.getInt() + rhs.getInt());
        } else if (op.equals("sub")) {
            res.value = String.valueOf(lhs.getInt() - rhs.getInt());
        } else if (op.equals("mul")) {
            res.value = String.valueOf(lhs.getInt() * rhs.getInt());
        } else if (op.equals("sdiv")) {
            res.value = rhs.getInt() == 0 ? "0" : String.valueOf(lhs.getInt() / rhs.getInt());
        } else if (op.equals("srem")) {
            res.value = rhs.getInt() == 0 ? "0" : String.valueOf(lhs.getInt() % rhs.getInt());
        } else if (op.equals("shl")) {
            res.value = String.valueOf(lhs.getInt() << rhs.getInt());
        } else if (op.equals("ashr")) {
            res.value = String.valueOf(lhs.getInt() >> rhs.getInt());
        } else if (op.equals("and")) {
            res.value = String.valueOf(lhs.getInt() & rhs.getInt());
        } else if (op.equals("or")) {
            res.value = String.valueOf(lhs.getInt() | rhs.getInt());
        } else if (op.equals("xor")) {
            res.value = String.valueOf(lhs.getInt() ^ rhs.getInt());
        } else {
            throw new RuntimeException("Arith op " + op + " not supported in SCCP");
        }
        return new Lattice(latticeType.CONST, res);
    }

    Lattice computeIcmp(String op, IRLiteral lhs, IRLiteral rhs) {
        IRLiteral res = new IRLiteral(IRType.IRBoolType);
        if (op.equals("eq")) {
            res.value = lhs.getInt() == rhs.getInt() ? "true" : "false";
        } else if (op.equals("ne")) {
            res.value = lhs.getInt() != rhs.getInt() ? "true" : "false";
        } else if (op.equals("slt")) {
            res.value = lhs.getInt() < rhs.getInt() ? "true" : "false";
        } else if (op.equals("sgt")) {
            res.value = lhs.getInt() > rhs.getInt() ? "true" : "false";
        } else if (op.equals("sle")) {
            res.value = lhs.getInt() <= rhs.getInt() ? "true" : "false";
        } else if (op.equals("sge")) {
            res.value = lhs.getInt() >= rhs.getInt() ? "true" : "false";
        } else {
            throw new RuntimeException("Icmp op " + op + " not supported in SCCP");
        }
        return new Lattice(latticeType.CONST, res);
    }



    void processIns(IRIns ins) {
        if (!checkpPossible(ins)) return; // callIns / storeIns / loadIns / ...  
        var val = ins.getDef();        
        var oldLt = getLattice(val);

        if (ins instanceof phiIns) {
            varLattice.put(val, meetphi((phiIns) ins));
        } else if (ins instanceof arithIns) {
            var lt1 = getLattice(((arithIns) ins).lhs);
            var lt2 = getLattice(((arithIns) ins).rhs);

            if (lt1.type == latticeType.CONST && lt2.type == latticeType.CONST) {
                varLattice.put(val, computeArith(((arithIns) ins).op, lt1.value, lt2.value));
            } else if (lt1.type == latticeType.BOTTOM || lt2.type == latticeType.BOTTOM) {
                varLattice.put(val, new Lattice(latticeType.BOTTOM, null));
            } else {
                varLattice.put(val, new Lattice(latticeType.TOP, null));
            }

        } else if (ins instanceof icmpIns) {
            var icmpins = (icmpIns) ins;
            var lt1 = getLattice(icmpins.lhs);
            var lt2 = getLattice(icmpins.rhs);

            // special case for icmpIns   a <=> a
            if (icmpins.lhs.equals(icmpins.rhs)) {
                switch (icmpins.op) {
                    case "eq": case "sge": case "sle":
                        varLattice.put(val, new Lattice(latticeType.CONST, new IRLiteral(IRType.IRBoolType, "true")));
                        break;
                    case "ne": case "sgt": case "slt":
                        varLattice.put(val, new Lattice(latticeType.CONST, new IRLiteral(IRType.IRBoolType, "false")));
                        break;
                }
            } else if (lt1.type == latticeType.CONST && lt2.type == latticeType.CONST) {
                varLattice.put(val, computeIcmp(((icmpIns) ins).op, lt1.value, lt2.value));
            } else if (lt1.type == latticeType.BOTTOM || lt2.type == latticeType.BOTTOM) {
                varLattice.put(val, new Lattice(latticeType.BOTTOM, null));
            } else {
                varLattice.put(val, new Lattice(latticeType.TOP, null));
            }
        } /*else if (ins instanceof getelementptrIns) {
            // Since our getelementptrIns only have two forms:
            // %res = getelementptrIns %ptr, i32 0, %var
            // %res = getelementptrIns %ptr, %var
            var gele = (getelementptrIns) ins;

            var lt1 = getLattice(gele.pointer);
            var lt2 = getLattice(gele.indices.size() == 2 ? gele.indices.get(1) : gele.indices.get(0));

            if (lt1.type == latticeType.CONST && lt2.type == latticeType.CONST) {
                varLattice.put(val, new Lattice(latticeType.CONST, 
                    new IRLiteral(IRType.IRIntType, String.valueOf(lt1.value.getInt() + lt2.value.getInt()))));
            } else if (lt1.type == latticeType.BOTTOM || lt2.type == latticeType.BOTTOM) {
                varLattice.put(val, new Lattice(latticeType.BOTTOM, null));
            } else {
                varLattice.put(val, new Lattice(latticeType.TOP, null));
            }
        } */ else {
            throw new RuntimeException("Ins " + ins + " not supported in SCCP");
        }

        var newLt = getLattice(val);
        if (!oldLt.equals(newLt)) {
            addUses(val);
        }

    }

    void propagate(IRblock block) {
        var ins = block.endIns;
        if (ins instanceof returnIns) {
            return ;
        } else if (ins instanceof jumpIns) {
            edgesQueue.add(new Edge(block, curFunc.blocks.get(((jumpIns) ins).label)));
        } else if (ins instanceof branchIns) {
            var br = (branchIns) ins;
            var condLt = getLattice(br.cond);
            if (condLt.type == latticeType.BOTTOM) {
                edgesQueue.add((new Edge(block, curFunc.blocks.get(br.trueLabel))));
                edgesQueue.add((new Edge(block, curFunc.blocks.get(br.falseLabel))));
            } else if (condLt.type == latticeType.CONST) {
                if (condLt.value.getInt() == 1) {
                    edgesQueue.add((new Edge(block, curFunc.blocks.get(br.trueLabel))));
                } else {
                    edgesQueue.add((new Edge(block, curFunc.blocks.get(br.falseLabel))));
                }
            } else {
                // do nothing
            }

        } else if (ins instanceof icmpbranchIns) {
            var icmpbr = (icmpbranchIns) ins;
            var lt1 = getLattice(icmpbr.lhs);
            var lt2 = getLattice(icmpbr.rhs);
            
            Lattice condLt = null;
            if (icmpbr.lhs.equals(icmpbr.rhs)) {
                switch (icmpbr.op) {
                    case "eq": case "sge": case "sle":
                        condLt = new Lattice(latticeType.CONST, new IRLiteral(IRType.IRBoolType, "true"));
                        break;
                    case "ne": case "sgt": case "slt":
                        condLt = new Lattice(latticeType.CONST, new IRLiteral(IRType.IRBoolType, "false"));
                        break;
                }
            } else if (lt1.type == latticeType.CONST && lt2.type == latticeType.CONST) {
                condLt = computeIcmp(icmpbr.op, lt1.value, lt2.value);
            } else if (lt1.type == latticeType.BOTTOM || lt2.type == latticeType.BOTTOM) {
                condLt = new Lattice(latticeType.BOTTOM, null);
            } else {
                condLt = new Lattice(latticeType.TOP, null);
            }

            if (condLt.type == latticeType.BOTTOM) {
                edgesQueue.add((new Edge(block, curFunc.blocks.get(icmpbr.trueLabel))));
                edgesQueue.add((new Edge(block, curFunc.blocks.get(icmpbr.falseLabel))));
            } else if (condLt.type == latticeType.CONST) {
                if (condLt.value.getInt() == 1) {
                    edgesQueue.add((new Edge(block, curFunc.blocks.get(icmpbr.trueLabel))));
                } else {
                    edgesQueue.add((new Edge(block, curFunc.blocks.get(icmpbr.falseLabel))));
                }
            } else {
                // do nothing
            }            
        } else throw new RuntimeException("Ins " + ins + " should not be the end of block");

    }

    void addUses(IRvar var) {
        if (varUses.containsKey(var)) {
            for (var ins : varUses.get(var)) {
                if (var.equals(ins.getDef())) continue; // revisit itself (may in phi) 
                usesQueue.add(ins);
            }
        }

        if (varBlock.containsKey(var)) {
            for (var block : varBlock.get(var)) {
                if (visitedBlock.contains(block)) {
                    propagate(block);
                }
            }
        }
    }

    boolean checkpPossible(IRIns ins) {
        if (ins.getDef() == null) return false; // callIns(void) / storeIns / ...
        if (ins instanceof callIns) return false;
        if (ins instanceof getelementptrIns) return false;
        if (ins instanceof loadIns) return false;
        return true;
    }


    void buildDefUses() {
        curFunc.params.forEach(param -> {
            varLattice.put(param, new Lattice(latticeType.BOTTOM, null));
        });

        for (var block : curFunc.blockList) {
            for (var ins : block.phiList) {
                phi2Block.put(ins, block);
                ins.getUses().forEach(var -> {
                    varUses.putIfAbsent(var, new ArrayList<>());
                    varUses.get(var).add(ins);
                });
            }
            
            for (var ins : block.insList) {
                if (!checkpPossible(ins)) {
                    if (ins.getDef() != null)
                        varLattice.put(ins.getDef(), new Lattice(latticeType.BOTTOM, null));
                    continue;
                    // ins with no def is useless for SCCP 
                }; 
                ins.getUses().forEach(var -> {
                    varUses.putIfAbsent(var, new ArrayList<>());
                    varUses.get(var).add(ins);
                });
            }
            block.endIns.getUses().forEach(var -> {
                varBlock.putIfAbsent(var, new ArrayList<>());
                varBlock.get(var).add(block);
            });
        }
    }

    Lattice getLattice(IRitem var) {
        if (var instanceof IRLiteral) {
            return new Lattice(latticeType.CONST, (IRLiteral) var);
        }
        if (!varLattice.containsKey(var)) {
            return new Lattice(latticeType.TOP, null);
        }
        return varLattice.get(var);
    }
    
    void replaceConstIns(IRIns ins) {
        ins.getUses().forEach(var -> {
            var Lt = getLattice(var);
            if (Lt.type == latticeType.CONST) {
                ins.replaceUse(var, Lt.value);
            }
        });
    }


    void rewirePhi(IRblock block, String from) {
        for (var phi : block.phiList) {
            phi.removeLabel(from);
        }
    }

    void replaceConst() {
        for (var block : curFunc.blockList) {
            block.phiList.forEach(phi -> {replaceConstIns(phi);});
            block.insList.forEach(ins -> {replaceConstIns(ins);});
            replaceConstIns(block.endIns);

            if (block.endIns instanceof branchIns) {
                var br = (branchIns) block.endIns;
                if (br.cond instanceof IRLiteral) {
                    if (((IRLiteral) br.cond).getInt() == 1) {
                        block.endIns = new jumpIns(br.trueLabel);
                        rewirePhi(curFunc.blocks.get(br.falseLabel), block.getLabel());
                    } else {
                        block.endIns = new jumpIns(br.falseLabel);
                        rewirePhi(curFunc.blocks.get(br.trueLabel), block.getLabel());
                    }
                }
            }

            if (block.endIns instanceof icmpbranchIns) {
                var icmpbr = (icmpbranchIns) block.endIns;
                var lt1 = getLattice(icmpbr.lhs);
                var lt2 = getLattice(icmpbr.rhs);
                if (lt1.type == latticeType.CONST && lt2.type == latticeType.CONST) {
                    var condLt = computeIcmp(icmpbr.op, lt1.value, lt2.value);
                    if (condLt.type == latticeType.CONST) {
                        if (condLt.value.getInt() == 1) {
                            block.endIns = new jumpIns(icmpbr.trueLabel);
                            rewirePhi(curFunc.blocks.get(icmpbr.falseLabel), block.getLabel());
                        } else {
                            block.endIns = new jumpIns(icmpbr.falseLabel);
                            rewirePhi(curFunc.blocks.get(icmpbr.trueLabel), block.getLabel());
                        }
                    }
                }
            }
        }
    }
    


}



class Edge {
    IRblock from;
    IRblock to;

    public Edge(IRblock from, IRblock to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge e = (Edge) obj;
            return e.from == from && e.to == to;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (from == null ? 0 : from.hashCode()) ^ (to == null ? 0 : to.hashCode()) ;
    }

}

enum latticeType {
    TOP,
    CONST,
    BOTTOM
}

class Lattice {
    latticeType type;
    IRLiteral value;

    public Lattice(latticeType type, IRLiteral value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Lattice) {
            Lattice lt = (Lattice) obj;
            if (lt.type != type) return false;
            if (lt.type == latticeType.CONST) {
                return lt.value.equals(value);
            } 
            return true;
        }
        return false;
    }

}