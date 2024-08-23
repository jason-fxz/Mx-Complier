package BackEnd;

import ASM.ASMHelper;
import ASM.item.*;
import ASM.node.*;
import ASM.node.global.*;
import ASM.node.ins.*;
import IR.IRvisitor;
import IR.item.*;
import IR.node.*;
import IR.node.def.*;
import IR.node.ins.*;
import IR.type.IRType;

import java.util.HashMap;


public class NaiveASMBuilder implements IRvisitor<ASMHelper> {
    HashMap<String, Integer> structSize;
    ASMBlock curBlock;
    ASMFuncDefNode curFunc;
    ASMRoot root;
    int branchLabelCnt = 0;

    private String getLabel(String label) {
        if (label.equals("entry")) {
            return curFunc.getName();
        }
        return curFunc.getName() + "." + label;
    }

    public ASMRoot getRoot() {
        return root;
    }

    public NaiveASMBuilder() {
        curFunc = null;
        curBlock = null;
        structSize = new HashMap<>();
    }

    // Load IRitem to reg
    private void handleLoadIRitem(ASMReg rd, IRitem item) {
        if (item instanceof IRLiteral) {
            curBlock.addIns((ASMIns)new ASMLoadImmIns(rd, ((IRLiteral)item).getInt()).Note(rd + "<-" + ((IRLiteral)item)));
        } else {
            IRvar var = (IRvar)item;
            var paramId = curFunc.paramsId.get(var.name);
            if (var.isGlobal()) {
                curBlock.addIns((ASMIns)new ASMLoadAddrIns(rd, var.name.substring(1)).Note(rd + "<-" + var));
            } else if (paramId != null) {
                if (paramId < 8) {
                    curBlock.addIns((ASMIns)new ASMMoveIns(rd, ASMReg.x(10 + paramId)).Note(rd + "<-" + var + "(param " + paramId + ")"));
                } else {
                    curBlock.addIns((ASMIns)new ASMLoadIns(rd, new ASMAddr(ASMReg.s0, (paramId - 8) * 4)).Note(rd + "<-" + var + "(param " + paramId + ")"));
                }
            } else {
                int offset = curFunc.stackAddrBaseOns0.get(var.name);
                if (-2048 <= offset && offset <= 2047)
                    curBlock.addIns((ASMIns)new ASMLoadIns(rd, new ASMAddr(ASMReg.s0, offset)).Note(rd + "<-" + var));
                else {
                    curBlock.addIns(new ASMLoadImmIns(ASMReg.t0, offset));
                    curBlock.addIns(new ASMArithIns("add", rd, ASMReg.s0, ASMReg.t0));
                    curBlock.addIns((ASMIns)new ASMLoadIns(rd, new ASMAddr(rd, 0)).Note(rd + "<-" + var));
                }
            }
        }
    }

    private void handleLoadIRitem(ASMReg rd, IRitem item, String comment) {
        if (item instanceof IRLiteral) {
            curBlock.addIns((ASMIns)new ASMLoadImmIns(rd, ((IRLiteral)item).getInt()).Note(comment));
        } else {
            IRvar var = (IRvar)item;
            var paramId = curFunc.paramsId.get(var.name);
            if (var.isGlobal()) {
                curBlock.addIns((ASMIns)new ASMLoadAddrIns(rd, var.name.substring(1)).Note(comment));
            } else if (paramId != null) {
                if (paramId < 8) {
                    curBlock.addIns((ASMIns)new ASMMoveIns(rd, ASMReg.x(10 + paramId)).Note(comment));
                } else {
                    curBlock.addIns((ASMIns)new ASMLoadIns(rd, new ASMAddr(ASMReg.s0, (paramId - 8) * 4)).Note(comment));
                }
            } else {
                int offset = curFunc.stackAddrBaseOns0.get(var.name);
                if (-2048 <= offset && offset <= 2047)
                    curBlock.addIns((ASMIns)new ASMLoadIns(rd, new ASMAddr(ASMReg.s0, offset)).Note(comment));
                else {
                    curBlock.addIns(new ASMLoadImmIns(ASMReg.t0, offset));
                    curBlock.addIns(new ASMArithIns("add", rd, ASMReg.s0, ASMReg.t0));
                    curBlock.addIns((ASMIns)new ASMLoadIns(rd, new ASMAddr(rd, 0)).Note(comment));
                }
            }
        }
    }

    // store reg to IRvar
    private void handleStoreIRvar(ASMReg rs, IRvar var, String comment) {
        int offset = curFunc.stackAddrBaseOns0.get(var.name);
        if (-2048 <= offset && offset <= 2047) {
            curBlock.addIns((ASMIns)new ASMStoreIns(rs, new ASMAddr(ASMReg.s0, offset)).Note(comment));
        } else {
            curBlock.addIns((ASMIns)new ASMLoadImmIns(ASMReg.t0, offset).Note(comment));
            curBlock.addIns((ASMIns)new ASMArithIns("add", ASMReg.t0, ASMReg.s0, ASMReg.t0).Note(comment));
            curBlock.addIns((ASMIns)new ASMStoreIns(rs, new ASMAddr(ASMReg.t0, 0)).Note(comment));
        }
    } 

    private void getStackSpace(IRIns ins) {
        if (ins instanceof allocaIns) {
            var tmp = (allocaIns) ins;
            curFunc.stackSize += 8;
            curFunc.stackAddrBaseOns0.put(tmp.result.name, -curFunc.stackSize);
        } else if (ins instanceof arithIns) {
            var tmp = (arithIns) ins;
            curFunc.stackSize += 4;
            curFunc.stackAddrBaseOns0.put(tmp.result.name, -curFunc.stackSize);
        } else if (ins instanceof callIns) {
            var tmp = (callIns) ins;
            if (tmp.result != null) {
                curFunc.stackSize += 4;
                curFunc.stackAddrBaseOns0.put(tmp.result.name, -curFunc.stackSize);
            }
        } else if (ins instanceof getelementptrIns) {
            var tmp = (getelementptrIns) ins;
            curFunc.stackSize += 4;
            curFunc.stackAddrBaseOns0.put(tmp.result.name, -curFunc.stackSize);
        } else if (ins instanceof icmpIns) {
            var tmp = (icmpIns) ins;
            curFunc.stackSize += 4;
            curFunc.stackAddrBaseOns0.put(tmp.result.name, -curFunc.stackSize);
        } else if (ins instanceof loadIns) {
            var tmp = (loadIns) ins;
            curFunc.stackSize += 4;
            curFunc.stackAddrBaseOns0.put(tmp.result.name, -curFunc.stackSize);
        } else if (ins instanceof phiIns) {
            var tmp = (phiIns) ins;
            curFunc.stackSize += 4;
            curFunc.stackAddrBaseOns0.put(tmp.result.name, -curFunc.stackSize);
        } else if (ins instanceof selectIns) {
            var tmp = (selectIns) ins;
            curFunc.stackSize += 4;
            curFunc.stackAddrBaseOns0.put(tmp.result.name, -curFunc.stackSize);
        }
    }

    // Addi, imm 12bits signed integer -2048 ~ 2047
    private void handleAddi(ASMReg rd, ASMReg rs, int imm, String comment) {
        if (-2048 <= imm && imm <= 2047) {
            curBlock.addIns((ASMIns)new ASMArithiIns("addi", rd, rs, imm).Note(comment));
        } else {
            curBlock.addIns((ASMIns)new ASMLoadImmIns(ASMReg.t0, imm).Note(comment));
            curBlock.addIns((ASMIns)new ASMArithIns("add", rd, rs, ASMReg.t0).Note(comment));
        }
    }

    

    @Override
    public ASMHelper visit(jumpIns it) {
        curBlock.addIns(new ASMJumpIns(getLabel(it.label)));
        return null;
    }

    @Override
    public ASMHelper visit(IRblock iRblock) {
        throw new UnsupportedOperationException("Should Not visit IRblock");
    }

    @Override
    public ASMHelper visit(allocaIns it) {
        int offset = curFunc.stackAddrBaseOns0.get(it.result.name);
        handleAddi(ASMReg.t1, ASMReg.s0, offset + 4, it.toString()); // t1 = addr
        handleStoreIRvar(ASMReg.t1, it.result, it.toString());
        return null;
    }

    @Override
    public ASMHelper visit(arithIns it) {
        String op = it.op;
        switch (op) {
            case "add" -> op = "add";
            case "sub" -> op = "sub";
            case "mul" -> op = "mul";
            case "sdiv"-> op = "div";
            case "srem"-> op = "rem";
            case "shl" -> op = "sll";
            case "ashr"-> op = "sra";
            case "and" -> op = "and";
            case "or"  -> op = "or";
            case "xor" -> op = "xor";
            default -> throw new UnsupportedOperationException("Unknown arithIns op");
        }
        handleLoadIRitem(ASMReg.t1, it.lhs);
        handleLoadIRitem(ASMReg.t2, it.rhs);
        curBlock.addIns(new ASMArithIns(op, ASMReg.t1, ASMReg.t1, ASMReg.t2));
        handleStoreIRvar(ASMReg.t1, it.result, it.toString());
        return null;
    }

    @Override
    public ASMHelper visit(branchIns it) {
        String tureLabel = getLabel(it.trueLabel);
        String falseLabel = getLabel(it.falseLabel);
        String tmpLabel = getLabel("L.branch." + branchLabelCnt++);
        
        handleLoadIRitem(ASMReg.t1, it.cond);
        curBlock.addIns(new ASMBeqzIns(ASMReg.t1, tmpLabel));
        curBlock.addIns(new ASMLoadAddrIns(ASMReg.t1, tureLabel));
        curBlock.addIns(new ASMJumpIns(ASMReg.t1));

        ASMBlock tmpblock = new ASMBlock(tmpLabel);
        curFunc.addBlock(tmpblock);
        curBlock = tmpblock;
        curBlock.addIns(new ASMLoadAddrIns(ASMReg.t1, falseLabel));
        curBlock.addIns(new ASMJumpIns(ASMReg.t1));
        return null;
    }

    @Override
    public ASMHelper visit(callIns it) {
        int spOffset = 0;
        for (int i = 8; i < it.args.size(); ++i) {
            handleLoadIRitem(ASMReg.a0, it.args.get(i));
            curBlock.addIns(new ASMStoreIns(ASMReg.a0, new ASMAddr(ASMReg.sp, spOffset)));
            spOffset += 4;
        }
        for (int i = 0; i < Math.min(8, it.args.size()); ++i) {
            handleLoadIRitem(ASMReg.x(10 + i), it.args.get(i));
        }
        curBlock.addIns(new ASMCallIns(it.func));
        if (it.result != null) {
            handleStoreIRvar(ASMReg.a0, it.result, it.toString());
        }
        return null;
    }

    @Override
    public ASMHelper visit(icmpIns it) {
        handleLoadIRitem(ASMReg.t1, it.lhs);
        handleLoadIRitem(ASMReg.t2, it.rhs);
        switch (it.op) {
            case "eq" -> {
                curBlock.addIns(new ASMArithIns("xor", ASMReg.t1, ASMReg.t1, ASMReg.t2));
                curBlock.addIns(new ASMUnaryIns("seqz", ASMReg.t1, ASMReg.t1));
            }
            case "ne" -> {
                curBlock.addIns(new ASMArithIns("xor", ASMReg.t1, ASMReg.t1, ASMReg.t2));
                curBlock.addIns(new ASMUnaryIns("snez", ASMReg.t1, ASMReg.t1));
            }
            case "slt" -> curBlock.addIns(new ASMArithIns("slt", ASMReg.t1, ASMReg.t1, ASMReg.t2));
            case "sgt" -> curBlock.addIns(new ASMArithIns("slt", ASMReg.t1, ASMReg.t2, ASMReg.t1));
            case "sle" -> {
                // a <= b <=> !(a>b) <=> !(b<a)
                curBlock.addIns(new ASMArithIns("slt", ASMReg.t1, ASMReg.t2, ASMReg.t1));
                curBlock.addIns(new ASMArithiIns("xori", ASMReg.t1, ASMReg.t1, 1));
            }
            case "sge" -> {
                // a >= b <=> !(a<b)
                curBlock.addIns(new ASMArithIns("slt", ASMReg.t1, ASMReg.t1, ASMReg.t2));
                curBlock.addIns(new ASMArithiIns("xori", ASMReg.t1, ASMReg.t1, 1));
            }
            default -> throw new UnsupportedOperationException("Unknown icmpIns op");
        }
        handleStoreIRvar(ASMReg.t1, it.result, ">>> " + it.toString());
        return null;
    }

    @Override
    public ASMHelper visit(loadIns it) {
        if (it.pointer.isGlobal()) {
            curBlock.addIns((ASMIns)new ASMLoadIns(ASMReg.t1, it.pointer.name.substring(1)).Note("load ptr(global)"));
            handleStoreIRvar(ASMReg.t1, it.result, ">>> " + it.toString());
        } else {
            handleLoadIRitem(ASMReg.t1, it.pointer);
            curBlock.addIns((ASMIns)new ASMLoadIns(ASMReg.t1, new ASMAddr(ASMReg.t1, 0)).Note("load ptr"));
            handleStoreIRvar(ASMReg.t1, it.result, ">>> " + it.toString());
        }
        return null;
    }

    @Override
    public ASMHelper visit(getelementptrIns it) {
        handleLoadIRitem(ASMReg.t1, it.pointer);
        IRitem item = null;
        if (it.indices.size() == 1) {
            item = it.indices.get(0);
        } else if (it.indices.size() == 2) {
            item = it.indices.get(1);
        } else throw new UnsupportedOperationException("Unimplemented method in visit getelementptrIns");

        if (item instanceof IRLiteral) {
            int offset = ((IRLiteral)item).getInt() * 4;
            if (offset != 0) {
                curBlock.addIns(new ASMArithiIns("addi", ASMReg.t1, ASMReg.t1, offset));
            }
        } else {
            IRvar var = (IRvar)item;
            handleLoadIRitem(ASMReg.t2, var);
            curBlock.addIns(new ASMArithiIns("slli", ASMReg.t2, ASMReg.t2, 2)); // *4
            curBlock.addIns(new ASMArithIns("add", ASMReg.t1, ASMReg.t1, ASMReg.t2));
        }
        handleStoreIRvar(ASMReg.t1, it.result, it.toString());
        return null;
    }

    

    @Override
    public ASMHelper visit(IRRoot it) {
        root = new ASMRoot();
        structSize.put("i32", 4);
        structSize.put("i1", 4);
        structSize.put("ptr", 4);

        for (var gVar : it.gVars) {
            root.globalVars.add(new ASMglobalVarDef(gVar.getVarName(), structSize.get(gVar.getTypeName())));
        }
        for (var gStr : it.gStrs) {
            root.globalStrs.add(new ASMglobalStrDef(gStr.name.substring(1), gStr.value));
        }
        for (var func : it.funcs) {
            func.accecpt(this);
        }

        return null;
    }

    @Override
    public ASMHelper visit(phiIns phiIns) {
        throw new UnsupportedOperationException("Should Not visit phiIns");
    }

    @Override
    public ASMHelper visit(selectIns it) {
        handleLoadIRitem(ASMReg.t1, it.cond);
        handleLoadIRitem(ASMReg.t2, it.value1);
        String tmpLabel = getLabel("L.select." + branchLabelCnt++);
        curBlock.addIns(new ASMBeqzIns(ASMReg.t1, tmpLabel));
        handleLoadIRitem(ASMReg.t2, it.value2);

        ASMBlock tmpblock = new ASMBlock(tmpLabel);
        curFunc.addBlock(tmpblock);
        curBlock = tmpblock;
        handleStoreIRvar(ASMReg.t2, it.result, it.toString());
        return null;
    }

    @Override
    public ASMHelper visit(returnIns it) {
        if (!it.value.type.equals((IRType.IRvoidType))) {
            if (it.value instanceof IRLiteral) {
                curBlock.addIns((ASMIns)new ASMLoadImmIns(ASMReg.a0, ((IRLiteral)it.value).getInt()).Note("return value(const)"));
            } else {
                handleLoadIRitem(ASMReg.a0, (IRvar)it.value, "return value");
            }
        }
        
        curBlock.addIns((ASMIns)new ASMLoadIns(ASMReg.ra, new ASMAddr(ASMReg.sp, curFunc.stackSize - 4)).Note("UnSave ra")); // sw ra,stackSize-4(sp)
        // FUCKKKKKKKKKKK !!
        curBlock.addIns((ASMIns)new ASMLoadIns(ASMReg.s0, new ASMAddr(ASMReg.sp, curFunc.stackSize - 8)).Note("UnSave s0")); // sw s0,stackSize-8(sp)
        handleAddi(ASMReg.sp, ASMReg.sp, curFunc.stackSize, "return stackSize"); // addi sp,sp,stackSize
        curBlock.addIns(new ASMReturnIns());
        return null;
    }

    @Override
    public ASMHelper visit(storeIns it) {
        if (it.pointer.isGlobal()) {
            handleLoadIRitem(ASMReg.t1, it.value);
            curBlock.addIns((ASMIns)new ASMStoreIns(ASMReg.t1, it.pointer.name.substring(1), ASMReg.t2).Note(it.toString()));
        } else {
            handleLoadIRitem(ASMReg.t1, it.value);
            handleLoadIRitem(ASMReg.t2, it.pointer);
            curBlock.addIns((ASMIns)new ASMStoreIns(ASMReg.t1, new ASMAddr(ASMReg.t2, 0)).Note(it.toString()));
        }
        return null;
    }

    @Override
    public ASMHelper visit(IRFuncDec irFuncDec) {
        throw new UnsupportedOperationException("Should Not visit IRFuncDec");        
    }

    @Override
    public ASMHelper visit(IRFuncDef func) {
        curFunc = new ASMFuncDefNode(func.getName().substring(1), func.params.size());

        root.funcs.add(curFunc);
        int countMaxCallParams = 0;
        for (var blk : func.blockList) {
            for (var ins : blk.insList) {
                getStackSpace(ins);
                if (ins instanceof callIns) {
                    var tmp = (callIns) ins;
                    countMaxCallParams = Math.max(countMaxCallParams, tmp.args.size());
                }
            }
        }
        curFunc.stackSize += Math.max(0, countMaxCallParams - 7) * 4;
        curFunc.stackSize = (curFunc.stackSize + 15) / 16 * 16;
        curFunc.stackSize += 16;
        if (curFunc.stackSize % 16 != 0) {
            throw new RuntimeException("stackSize % 16 != 0");
        }

        for (var block : func.blockList) {
            if (block.empty()) continue;
            curBlock = new ASMBlock(getLabel(block.getLabel()));
            curFunc.addBlock(curBlock);
            if (block.Label.equals("entry")) {
                handleAddi(ASMReg.sp, ASMReg.sp, -curFunc.stackSize, "fetch stackSize"); // addi sp,sp,-stackSize
                curBlock.addIns((ASMIns)new ASMStoreIns(ASMReg.ra, new ASMAddr(ASMReg.sp, curFunc.stackSize - 4)).Note("Save ra")); // sw ra,stackSize-4(sp)
                curBlock.addIns((ASMIns)new ASMStoreIns(ASMReg.s0, new ASMAddr(ASMReg.sp, curFunc.stackSize - 8)).Note("Save s0")); // sw s0,stackSize-8(sp)
                handleAddi(ASMReg.s0, ASMReg.sp, curFunc.stackSize, "record stackSpace"); // addi s0,sp,stackSize

                for (int i = 0; i < curFunc.paramCnt; ++i) {
                    curFunc.paramsId.put(func.params.get(i).name, i); 
                }
            } 

            for (var ins : block.insList) {
                ins.accecpt(this);
            }
            block.endIns.accecpt(this);
        }


        return null;
    }

    @Override
    public ASMHelper visit(IRglobalVarDef iRglobalVarDef) {
        throw new UnsupportedOperationException("Should Not visit IRglobalVarDef");
    }

    @Override
    public ASMHelper visit(IRStrDef irStrDef) {
        throw new UnsupportedOperationException("Should Not visit IRStrDef");
    }

    @Override
    public ASMHelper visit(IRStructDef irStructDef) {
        throw new UnsupportedOperationException("Should Not visit IRStructDef");
    }

}
