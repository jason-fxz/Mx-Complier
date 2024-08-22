package BackEnd;

import ASM.ASMHelper;
import ASM.item.ASMAddr;
import ASM.item.ASMReg;
import ASM.node.ASMBlock;
import ASM.node.ASMRoot;
import ASM.node.global.ASMFuncDefNode;
import ASM.node.global.ASMglobalStrDef;
import ASM.node.global.ASMglobalVarDef;
import ASM.node.ins.ASMArithIns;
import ASM.node.ins.ASMArithiIns;
import ASM.node.ins.ASMJumpIns;
import ASM.node.ins.ASMLoadImmIns;
import ASM.node.ins.ASMLoadIns;
import ASM.node.ins.ASMReturnIns;
import ASM.node.ins.ASMStoreIns;
import IR.IRhelper;
import IR.IRvisitor;
import IR.item.IRLiteral;
import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDec;
import IR.node.def.IRFuncDef;
import IR.node.def.IRStrDef;
import IR.node.def.IRStructDef;
import IR.node.def.IRglobalVarDef;
import IR.node.ins.IRIns;
import IR.node.ins.allocaIns;
import IR.node.ins.arithIns;
import IR.node.ins.branchIns;
import IR.node.ins.callIns;
import IR.node.ins.getelementptr;
import IR.node.ins.icmpIns;
import IR.node.ins.jumpIns;
import IR.node.ins.loadIns;
import IR.node.ins.phiIns;
import IR.node.ins.returnIns;
import IR.node.ins.selectIns;
import IR.node.ins.storeIns;
import IR.type.IRType;

import java.util.HashMap;

import org.stringtemplate.v4.compiler.STParser.arg_return;

public class NaiveASMBuilder implements IRvisitor<ASMHelper> {
    HashMap<String, Integer> structSize;
    ASMBlock curBlock;
    ASMFuncDefNode curFunc;
    ASMRoot root;

    private String getLabel(String label) {
        if (label.equals("entry")) {
            return curFunc.getName();
        }
        return curFunc.getName() + "." + label;
    }

    NaiveASMBuilder() {
        curFunc = null;
        curBlock = null;
        structSize = new HashMap<>();
    }

    // Load IRvar to reg
    private void handleLoadIRvar(ASMReg rd, IRvar var) {
        int offset = curFunc.stackAddrBaseOns0.get(var.name);
        if (-2048 <= offset && offset <= 2047)
            curBlock.addIns(new ASMLoadIns(rd, new ASMAddr(ASMReg.s0, offset)));
        else {
            curBlock.addIns(new ASMLoadImmIns(ASMReg.t0, offset));
            curBlock.addIns(new ASMArithIns("add", rd, ASMReg.s0, ASMReg.t0));
            curBlock.addIns(new ASMLoadIns(rd, new ASMAddr(rd, 0)));
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
        } else if (ins instanceof getelementptr) {
            var tmp = (getelementptr) ins;
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
    private void handleAddi(ASMReg rd, ASMReg rs, int imm) {
        if (-2048 <= imm && imm <= 2047) {
            curBlock.addIns(new ASMArithiIns("addi", rd, rs, imm));
        } else {
            curBlock.addIns(new ASMLoadImmIns(ASMReg.t0, imm));
            curBlock.addIns(new ASMArithIns("add", rd, rs, ASMReg.t0));
        }
    }

    // store reg to IRvar
    private void handleStoreIRvar(ASMReg rs, IRvar var) {
        int offset = curFunc.stackAddrBaseOns0.get(var.name);
        if (-2048 <= offset && offset <= 2047) {
            curBlock.addIns(new ASMStoreIns(rs, new ASMAddr(ASMReg.s0, offset)));
        } else {
            curBlock.addIns(new ASMLoadImmIns(ASMReg.t0, offset));
            curBlock.addIns(new ASMArithIns("add", ASMReg.t0, ASMReg.s0, ASMReg.t0));
            curBlock.addIns(new ASMStoreIns(rs, new ASMAddr(ASMReg.t0, 0)));
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
        handleAddi(ASMReg.t1, ASMReg.s0, offset + 4); // t1 = addr
        handleStoreIRvar(ASMReg.t1, it.result);
        return null;
    }

    @Override
    public ASMHelper visit(arithIns it) {
        String op = it.op;
        if (op.equals("shl")) {
            op = "sll";
        } else if (op.equals("sdiv")) {
            op = "div";
        } else if (op.equals("srem")) {
            op = "rem";
        } else if (op.equals("ashr")) {
            op = "sra";
        } 
        
        
        return null;
    }

    @Override
    public ASMHelper visit(branchIns branchIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(callIns callIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(icmpIns icmpIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(loadIns loadIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(getelementptr getelementptr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
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
            root.globalStrs.add(new ASMglobalStrDef(gStr.name, gStr.value));
        }
        for (var func : it.funcs) {
            func.accecpt(this);
        }

        return null;
    }

    @Override
    public ASMHelper visit(phiIns phiIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(selectIns selectIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(returnIns it) {
        if (!it.value.type.equals((IRType.IRvoidType))) {
            if (it.value instanceof IRLiteral) {
                curBlock.addIns(new ASMLoadImmIns(ASMReg.a0, ((IRLiteral)it.value).getInt()));
            } else {
                handleLoadIRvar(ASMReg.a0, (IRvar)it.value);
            }
        }
        curBlock.addIns(new ASMReturnIns());
        return null;
    }

    @Override
    public ASMHelper visit(storeIns storeIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(IRFuncDec irFuncDec) {
        throw new UnsupportedOperationException("Should Not visit IRFuncDec");        
    }

    @Override
    public ASMHelper visit(IRFuncDef func) {
        curFunc = new ASMFuncDefNode(func.getName(), func.params.size());
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
        curFunc.stackSize += (countMaxCallParams - 7) * 4;
        curFunc.stackSize = (curFunc.stackSize + 15) / 16 * 16;

        for (var block : func.blockList) {
            curBlock = new ASMBlock(getLabel(block.getLabel()));
            curFunc.addBlock(curBlock);
            if (block.Label.equals("entry")) {
                handleAddi(ASMReg.sp, ASMReg.sp, -curFunc.stackSize); // addi sp,sp,-stackSize
                curBlock.addIns(new ASMStoreIns(ASMReg.ra, new ASMAddr(ASMReg.sp, curFunc.stackSize - 4))); // sw ra,stackSize-4(sp)
                curBlock.addIns(new ASMStoreIns(ASMReg.ra, new ASMAddr(ASMReg.s0, curFunc.stackSize - 8))); // sw s0,stackSize-8(sp)
                handleAddi(ASMReg.s0, ASMReg.sp, curFunc.stackSize); // addi s0,sp,stackSize
            } 

            for (var ins : block.insList) {
                ins.accecpt(this);
            }
            
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
