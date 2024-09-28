package Backend;

import ASM.ASMHelper;
import ASM.item.*;
import ASM.node.*;
import ASM.node.global.*;
import ASM.node.ins.ASMArithIns;
import ASM.node.ins.ASMArithiIns;
import ASM.node.ins.ASMBeqzIns;
import ASM.node.ins.ASMJumpIns;
import ASM.node.ins.ASMLoadAddrIns;
import ASM.node.ins.ASMLoadImmIns;
import ASM.node.ins.ASMLoadIns;
import ASM.node.ins.ASMStoreIns;
import ASM.node.ins.ASMUnaryIns;
import IR.IRvisitor;
import IR.item.*;
import IR.node.*;
import IR.node.def.*;
import IR.node.ins.*;

import java.util.HashMap;


public class ASMBuilder implements IRvisitor<ASMHelper> {
    ASMBlock curBlock;
    ASMFuncDefNode curFunc;
    ASMRoot root;
    int branchLabelCnt = 0;
    HashMap<IRvar, ASMItem> var2ASMItem;
    HashMap<Integer, ASMReg> regMap;


    private String getLabel(String label) {
        if (label.equals("entry")) {
            return curFunc.getName();
        }
        return curFunc.getName() + "." + label;
    }

    public ASMBuilder() {
        for (int i = 0; i < 24; ++i) {
            regMap.put(i, ASMReg.x(8 + i));            
        }
    }


 

    @Override
    public ASMHelper visit(IRglobalVarDef iRglobalVarDef) {
        root.globalVars.add(new ASMglobalVarDef(iRglobalVarDef.getVarName(), 4));
        return null;
    }

    @Override
    public ASMHelper visit(IRStrDef irStrDef) {
        root.globalStrs.add(new ASMglobalStrDef(irStrDef.name.substring(1), irStrDef.value));
        return null;
    }

    @Override
    public ASMHelper visit(IRStructDef irStructDef) {
        throw new UnsupportedOperationException("Should Not visit IRStructDef");
    }



    @Override
    public ASMHelper visit(jumpIns it) {
        curBlock.addIns(new ASMJumpIns(getLabel(it.label)));
        return null;
    }



    @Override
    public ASMHelper visit(IRblock it) {
        throw new UnsupportedOperationException("Should Not visit IRblock");
    }



    @Override
    public ASMHelper visit(allocaIns it) {
        throw new UnsupportedOperationException("Should Not visit allocaIns");
    }

    void handleASMAddrLoad(ASMReg rd, ASMAddr addr) {
        if (-2048 <= addr.getOffset() && addr.getOffset() <= 2047) {
            curBlock.addIns(new ASMLoadIns(rd, addr));
        } else {
            curBlock.addIns(new ASMLoadImmIns(rd, addr.getOffset()));
            curBlock.addIns(new ASMArithIns("add", rd, addr.getBase(), rd));
            curBlock.addIns(new ASMLoadIns(rd, new ASMAddr(rd, 0)));
        }
    }

    void handleASMAddrStore(ASMReg rs, ASMAddr addr, ASMReg tmp) {
        if (rs.equals(tmp)) throw new RuntimeException("handleASMAddrStore: rs == tmp");
        if (-2048 <= addr.getOffset() && addr.getOffset() <= 2047) {
            curBlock.addIns(new ASMStoreIns(rs, addr));
        } else {
            curBlock.addIns(new ASMLoadImmIns(tmp, addr.getOffset()));
            curBlock.addIns(new ASMArithIns("add", tmp, addr.getBase(), tmp));
            curBlock.addIns(new ASMStoreIns(rs, new ASMAddr(tmp, 0)));
        }
    }

    void handleASMAddi(ASMReg rd, ASMReg rs, int imm) {
        if (-2048 <= imm && imm <= 2047) {
            curBlock.addIns(new ASMArithiIns("addi", rd, rs, imm));
        } else {
            curBlock.addIns(new ASMLoadImmIns(rd, imm));
            curBlock.addIns(new ASMArithIns("add", rd, rs, rd));
        }
    }

    ASMReg loadIRitem(IRitem item, ASMReg tmp) {
        if (item instanceof IRLiteral) {
            curBlock.addIns(new ASMLoadImmIns(tmp, ((IRLiteral) item).getInt()));
            return tmp;
        } else {
            var asmitem = var2ASMItem.get(item);
            if (asmitem instanceof ASMReg) {
                return (ASMReg) asmitem;
            } else {
                handleASMAddrLoad(tmp, (ASMAddr) asmitem);
                return tmp;
            }
        }
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
        var rs1 = loadIRitem(it.lhs, ASMReg.t1);
        var rs2 = loadIRitem(it.rhs, ASMReg.t2);

        var asmitem = var2ASMItem.get(it.result);
        if (asmitem instanceof ASMReg) {
            curBlock.addIns(new ASMArithIns(op, (ASMReg) asmitem, rs1, rs2));
        } else {
            curBlock.addIns(new ASMArithIns(op, ASMReg.t1, rs1, rs2));
            handleASMAddrStore(ASMReg.t1, (ASMAddr) asmitem, ASMReg.t2);
        }
        return null;
    }



    @Override
    public ASMHelper visit(branchIns it) {
        String tureLabel = getLabel(it.trueLabel);
        String falseLabel = getLabel(it.falseLabel);
        String tmpLabel = getLabel("L.branch." + branchLabelCnt++);
        
        var cond = loadIRitem(it.cond, ASMReg.t1);
        curBlock.addIns(new ASMBeqzIns(cond, tmpLabel));
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }



    @Override
    public ASMHelper visit(icmpIns it) {
        var rs1 = loadIRitem(it.lhs, ASMReg.t1);
        var rs2 = loadIRitem(it.rhs, ASMReg.t2);

        var asmitem = var2ASMItem.get(it.result);
        var rd = asmitem instanceof ASMReg ? (ASMReg) asmitem : ASMReg.t1;

        switch (it.op) {
            case "eq" -> {
                curBlock.addIns(new ASMArithIns("xor", ASMReg.t1, rs1, rs2));
                curBlock.addIns(new ASMUnaryIns("seqz", rd, ASMReg.t1));
            }
            case "ne" -> {
                curBlock.addIns(new ASMArithIns("xor", ASMReg.t1, rs1, rs2));
                curBlock.addIns(new ASMUnaryIns("snez", rd, ASMReg.t1));
            }
            case "slt" -> curBlock.addIns(new ASMArithIns("slt", rd, rs1, rs2));
            case "sgt" -> curBlock.addIns(new ASMArithIns("slt", rd, rs2, rs1));
            case "sle" -> {
                // a <= b <=> !(a>b) <=> !(b<a)
                curBlock.addIns(new ASMArithIns("slt", ASMReg.t1, rs2, rs1));
                curBlock.addIns(new ASMArithiIns("xori", rd, ASMReg.t1, 1));
            }
            case "sge" -> {
                // a >= b <=> !(a<b)
                curBlock.addIns(new ASMArithIns("slt", ASMReg.t1, rs1, rs2));
                curBlock.addIns(new ASMArithiIns("xori", rd, ASMReg.t1, 1));
            }
            default -> throw new UnsupportedOperationException("Unknown icmpIns op");
        }
        if (asmitem instanceof ASMAddr) {
            handleASMAddrStore(rd, (ASMAddr) asmitem, ASMReg.t2);
        }
        return null;
    }



    @Override
    public ASMHelper visit(loadIns it) {
        var asmitem = var2ASMItem.get(it.result);
        var rd = asmitem instanceof ASMReg ? (ASMReg) asmitem : ASMReg.t1;

        if (it.pointer.isGlobal()) {
            curBlock.addIns(new ASMLoadIns(rd, it.pointer.name.substring(1)));
            if (asmitem instanceof ASMAddr) handleASMAddrStore(rd, (ASMAddr) asmitem, ASMReg.t2);
        } else {
            var rs = loadIRitem(it.pointer, ASMReg.t2);
            curBlock.addIns(new ASMLoadIns(rd, new ASMAddr(rs, 0)));
            if (asmitem instanceof ASMAddr) handleASMAddrStore(rd, (ASMAddr) asmitem, ASMReg.t2);
        }
        return null;
    }

    @Override
    public ASMHelper visit(storeIns it) {
        var rs = loadIRitem(it.value, ASMReg.t1);

        if (it.pointer.isGlobal()) {
            curBlock.addIns(new ASMStoreIns(rs, it.pointer.name.substring(1), ASMReg.t2));
        } else {
            var ptr = loadIRitem(it.pointer, ASMReg.t2);
            curBlock.addIns(new ASMStoreIns(rs, new ASMAddr(ptr, 0)));
        }
        return null;   
    }



    @Override
    public ASMHelper visit(getelementptrIns it) {
        var base = loadIRitem(it.pointer, ASMReg.t1);

        var asmitem = var2ASMItem.get(it.result);
        var rd = asmitem instanceof ASMReg ? (ASMReg) asmitem : ASMReg.t2;

        IRitem item = null;
        if (it.indices.size() == 1) {
            item = it.indices.get(0);
        } else if (it.indices.size() == 2) {
            item = it.indices.get(1);
        } else throw new UnsupportedOperationException("Unimplemented method in visit getelementptrIns");

        if (item instanceof IRLiteral) {
            int offset = ((IRLiteral) item).getInt() * 4;
            handleASMAddi(rd, base, offset);
        } else {
            var offset = loadIRitem(item, ASMReg.t2);
            curBlock.addIns(new ASMArithiIns("slli", ASMReg.t2, offset, 2)); // offset *= 4
            curBlock.addIns(new ASMArithIns("add", rd, base, ASMReg.t2));
        }
        if (asmitem instanceof ASMAddr) handleASMAddrStore(rd, (ASMAddr) asmitem, ASMReg.t1);
        return null;
    }



    @Override
    public ASMHelper visit(IRRoot it) {
        root = new ASMRoot();

        for (var gVar : it.gVars) gVar.accecpt(this);
        for (var gStr : it.gStrs) gStr.accecpt(this);
        for (var func : it.funcs) func.accecpt(this);
        
        return null;
    }



    @Override
    public ASMHelper visit(phiIns it) {
        throw new UnsupportedOperationException("Should Not visit phiIns!!");
    }



    @Override
    public ASMHelper visit(selectIns it) {
        throw new UnsupportedOperationException("Should Not Use SelectIns!!");
    }



    @Override
    public ASMHelper visit(returnIns it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }



    



    @Override
    public ASMHelper visit(IRFuncDec it) {
        throw new UnsupportedOperationException("Should Not visit IRFuncDec");
    }



    @Override
    public ASMHelper visit(IRFuncDef it) {
        curFunc = new ASMFuncDefNode(it.getName().substring(1), it.params.size());
        root.funcs.add(curFunc);

        int countMaxCallParams = 0;
        for (var blk : it.blocks.values()) {
            for (var ins : blk.insList) {
                if (ins instanceof callIns) {
                    var tmp = (callIns) ins;
                    countMaxCallParams = Math.max(countMaxCallParams, tmp.args.size());
                }
            }
        }

        curFunc.stackSize = 8 + it.maxUsedReg * 4 + Math.max(0, countMaxCallParams - 8) * 4 + it.spilledVar.size() * 4;
        curFunc.stackSize = (curFunc.stackSize + 15) / 16 * 16;

        for (var entry : it.regOfVar.entrySet()) {
            var2ASMItem.put(entry.getKey(), regMap.get(entry.getValue()));   
        }
        curFunc.stackSpillOffset = curFunc.stackSize - (4 + 4 * it.spilledVar.size());
        // !! curFunc.stackSize - 4  save ra
        for (var entry : it.spilledVar.entrySet()) {
            var2ASMItem.put(entry.getKey(), new ASMAddr(ASMReg.sp, curFunc.stackSpillOffset + 4 * entry.getValue()));
            assert curFunc.stackSpillOffset + 4 * entry.getValue() < curFunc.stackSize - 4 : "ASMBuilder: spilled var overflow";
        }

        // TODO

        


        return null;
        
        
    }

}
