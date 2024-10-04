package Backend;

import ASM.ASMHelper;
import ASM.item.*;
import ASM.node.*;
import ASM.node.global.*;
import ASM.node.ins.ASMArithIns;
import ASM.node.ins.ASMArithiIns;
import ASM.node.ins.ASMBeqzIns;
import ASM.node.ins.ASMCallIns;
import ASM.node.ins.ASMJumpIns;
import ASM.node.ins.ASMLoadAddrIns;
import ASM.node.ins.ASMLoadImmIns;
import ASM.node.ins.ASMLoadIns;
import ASM.node.ins.ASMMoveIns;
import ASM.node.ins.ASMReturnIns;
import ASM.node.ins.ASMStoreIns;
import ASM.node.ins.ASMUnaryIns;
import IR.IRvisitor;
import IR.item.*;
import IR.node.*;
import IR.node.def.*;
import IR.node.ins.*;
import IR.type.IRType;
import Util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;


public class ASMBuilder implements IRvisitor<ASMHelper> {
    ASMBlock curBlock;
    ASMFuncDefNode curFunc;
    ASMRoot root;
    int branchLabelCnt = 0;
    HashMap<IRvar, ASMItem> var2ASMItem;
    HashMap<Integer, ASMReg> regMap;
    HashMap<ASMReg, ASMAddr> calleeSaveMap;
    HashMap<ASMReg, ASMAddr> callerSaveMap;
    HashMap<IRblock, ASMBlock> blockMap;


    private String getLabel(String label) {
        if (label.equals("entry")) {
            return curFunc.getName();
        }
        return curFunc.getName() + "." + label;
    }

    public ASMBuilder() {
        regMap = new HashMap<>();
    }

    public ASMRoot getRoot() {
        return root;
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
        curBlock.addJumpIns(new ASMJumpIns(getLabel(it.label)), it.toString() + getVarASMitem(it));
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

    void handleASMAddrLoad(ASMReg rd, ASMAddr addr, String comment) {
        if (comment == null) {
            comment = "handleASMAddrLoad";
        }
        if (-2048 <= addr.getOffset() && addr.getOffset() <= 2047) {
            curBlock.addIns(new ASMLoadIns(rd, addr), comment);
        } else {
            curBlock.addIns(new ASMLoadImmIns(rd, addr.getOffset()), comment);
            curBlock.addIns(new ASMArithIns("add", rd, addr.getBase(), rd), comment);
            curBlock.addIns(new ASMLoadIns(rd, new ASMAddr(rd, 0)), comment);
        }
    }

    void handleASMAddrStore(ASMReg rs, ASMAddr addr, ASMReg tmp, String comment) {
        if (comment == null) {
            comment = "handleASMAddrStore";
        }
        if (rs.equals(tmp)) throw new RuntimeException("handleASMAddrStore: rs == tmp");
        if (-2048 <= addr.getOffset() && addr.getOffset() <= 2047) {
            curBlock.addIns(new ASMStoreIns(rs, addr), comment);
        } else {
            curBlock.addIns(new ASMLoadImmIns(tmp, addr.getOffset()), comment);
            curBlock.addIns(new ASMArithIns("add", tmp, addr.getBase(), tmp), comment);
            curBlock.addIns(new ASMStoreIns(rs, new ASMAddr(tmp, 0)), comment);
        }
    }

    void handleASMAddi(ASMReg rd, ASMReg rs, int imm, String comment) {
        if (comment == null) {
            comment = "handleASMAddi";
        }
        if (-2048 <= imm && imm <= 2047) {
            curBlock.addIns(new ASMArithiIns("addi", rd, rs, imm), comment);
        } else {
            curBlock.addIns(new ASMLoadImmIns(ASMReg.t0, imm), comment);
            curBlock.addIns(new ASMArithIns("add", rd, rs, ASMReg.t0), comment);
        }
    }

    void handlemove(ASMItem rd, ASMItem rs, String comment) {
        if (rd.inReg() && rs.inReg()) {
            curBlock.addIns(new ASMMoveIns((ASMReg)rd, (ASMReg)rs), comment);
        } else if (rd.inReg() && rs.inMem()) {
            handleASMAddrLoad((ASMReg)rd, (ASMAddr)rs, comment);
        } else if (rd.inMem() && rs.inReg()) {
            handleASMAddrStore((ASMReg)rs, (ASMAddr)rd, ASMReg.t1, comment);
        } else {
            handleASMAddrLoad(ASMReg.t1, (ASMAddr)rs, comment);
            handleASMAddrStore(ASMReg.t1, (ASMAddr)rd, ASMReg.t2, comment);
        }
    }

    ASMReg loadIRitem(IRitem item, ASMReg tmp, String comment) {
        if (item instanceof IRLiteral) {
            curBlock.addIns(new ASMLoadImmIns(tmp, ((IRLiteral) item).getInt()));
            return tmp;
        } else {
            if (((IRvar)item).isGlobal()) {
                curBlock.addIns(new ASMLoadAddrIns(tmp, ((IRvar)item).name.substring(1)));
                return tmp;
            }
            var asmitem = var2ASMItem.get(item);
            if (asmitem instanceof ASMReg) {
                return (ASMReg) asmitem;
            } else {
                handleASMAddrLoad(tmp, (ASMAddr) asmitem, comment);
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
        var rs1 = loadIRitem(it.lhs, ASMReg.t1, "load lhs");
        var rs2 = loadIRitem(it.rhs, ASMReg.t2, "load rhs");

        var asmitem = var2ASMItem.get(it.result);
        if (asmitem instanceof ASMReg) {
            curBlock.addIns(new ASMArithIns(op, (ASMReg) asmitem, rs1, rs2), it.toString() + getVarASMitem(it));
        } else {
            curBlock.addIns(new ASMArithIns(op, ASMReg.t1, rs1, rs2), it.toString() + getVarASMitem(it));
            handleASMAddrStore(ASMReg.t1, (ASMAddr) asmitem, ASMReg.t2, "store result");
        }
        return null;
    }



    @Override
    public ASMHelper visit(branchIns it) {
        String tureLabel = getLabel(it.trueLabel);
        String falseLabel = getLabel(it.falseLabel);
        String tmpLabel = getLabel("L.branch." + branchLabelCnt++);
        
        var cond = loadIRitem(it.cond, ASMReg.t1, "load cond");
        curBlock.addJumpIns(new ASMBeqzIns(cond, tmpLabel), it.toString() + getVarASMitem(it));
        curBlock.addJumpIns(new ASMLoadAddrIns(ASMReg.t1, tureLabel));
        curBlock.addJumpIns(new ASMJumpIns(ASMReg.t1));

        ASMBlock tmpblock = new ASMBlock(tmpLabel);
        curFunc.addBlock(tmpblock);
        curBlock = tmpblock;
        curBlock.addJumpIns(new ASMLoadAddrIns(ASMReg.t1, falseLabel));
        curBlock.addJumpIns(new ASMJumpIns(ASMReg.t1));
        return null;
    }



    @Override
    public ASMHelper visit(callIns it) {
        // Save caller-save registers
        int regSaveCur = curFunc.regSaveCur;
        for (var reg : regMap.values()) {
            if (reg.isCallerSave()) {
                regSaveCur -= 4;
                var addr = new ASMAddr(ASMReg.sp, regSaveCur);
                callerSaveMap.put(reg, addr);
                handleASMAddrStore(reg, addr, ASMReg.t1, "save caller-save registers");
            }
        }
        
        int spOffset = 0;
        HashMap<ASMReg, ASMAddr> aAddr = new HashMap<>();
        
        for (int i = 0; i < it.args.size(); ++i) {
            var iritem = it.args.get(i);
            
            if (iritem instanceof IRLiteral) {
                if (i < 8) {
                    curBlock.addIns(new ASMLoadImmIns(ASMReg.a(i), ((IRLiteral) iritem).getInt()), "load arg %d imm".formatted(i));
                } else {
                    curBlock.addIns(new ASMLoadImmIns(ASMReg.t1, ((IRLiteral) iritem).getInt()), "load arg %d imm".formatted(i));
                    handleASMAddrStore(ASMReg.t1, new ASMAddr(ASMReg.sp, spOffset), ASMReg.t2, "(to stack)");
                    spOffset += 4;
                }
                
            } else if (((IRvar)iritem).isGlobal()) {
                if (i < 8) {
                    curBlock.addIns(new ASMLoadAddrIns(ASMReg.a(i), ((IRvar)iritem).name.substring(1)), "load arg %d global".formatted(i));
                } else {
                    curBlock.addIns(new ASMLoadAddrIns(ASMReg.t1, ((IRvar)iritem).name.substring(1)), "load arg global".formatted(i));
                    handleASMAddrStore(ASMReg.t1, new ASMAddr(ASMReg.sp, spOffset), ASMReg.t2, "(to stack)");
                    spOffset += 4;
                }
            } else {
                var asmitem = var2ASMItem.get(iritem);
                if (asmitem.inReg() && aAddr.containsKey((ASMReg)asmitem)) {
                    asmitem = aAddr.get((ASMReg)asmitem); // load from stack
                }
                if (i < 8) {
                    handlemove(ASMReg.a(i), asmitem, "load arg %d var".formatted(i));
                } else {
                    handlemove(new ASMAddr(ASMReg.sp, spOffset), asmitem, "load arg %d var".formatted(i));
                    spOffset += 4;
                }
            }

            if (i < 8) {
                aAddr.put(ASMReg.a(i), callerSaveMap.get(ASMReg.a(i)));
            }
        }

        

        curBlock.addIns(new ASMCallIns(it.func), it.toString() + getVarASMitem(it));

        ASMReg udpreg = null;
        if (it.result != null) {
            var asmitem = var2ASMItem.get(it.result);
            handlemove(asmitem, ASMReg.a0, "call: move return value");
            if (asmitem.inReg()) {
                udpreg = (ASMReg) asmitem;
            }
        }

        // Restore caller-save registers
        for (var entry : callerSaveMap.entrySet()) {
            if (entry.getKey().equals(udpreg)) continue;
            handleASMAddrLoad(entry.getKey(), entry.getValue(), "restore caller-save registers");
        }
        
        return null;
    }



    @Override
    public ASMHelper visit(icmpIns it) {
        var rs1 = loadIRitem(it.lhs, ASMReg.t1, "load lhs");
        var rs2 = loadIRitem(it.rhs, ASMReg.t2, "load rhs");

        var asmitem = var2ASMItem.get(it.result);
        var rd = asmitem instanceof ASMReg ? (ASMReg) asmitem : ASMReg.t1;

        switch (it.op) {
            case "eq" -> {
                curBlock.addIns(new ASMArithIns("xor", ASMReg.t1, rs1, rs2), it.toString() + getVarASMitem(it));
                curBlock.addIns(new ASMUnaryIns("seqz", rd, ASMReg.t1), it.toString() + getVarASMitem(it));
            }
            case "ne" -> {
                curBlock.addIns(new ASMArithIns("xor", ASMReg.t1, rs1, rs2), it.toString() + getVarASMitem(it));
                curBlock.addIns(new ASMUnaryIns("snez", rd, ASMReg.t1), it.toString() + getVarASMitem(it));
            }
            case "slt" -> curBlock.addIns(new ASMArithIns("slt", rd, rs1, rs2), it.toString() + getVarASMitem(it));
            case "sgt" -> curBlock.addIns(new ASMArithIns("slt", rd, rs2, rs1), it.toString() + getVarASMitem(it));
            case "sle" -> {
                // a <= b <=> !(a>b) <=> !(b<a)
                curBlock.addIns(new ASMArithIns("slt", ASMReg.t1, rs2, rs1), it.toString() + getVarASMitem(it));
                curBlock.addIns(new ASMArithiIns("xori", rd, ASMReg.t1, 1), it.toString() + getVarASMitem(it));
            }
            case "sge" -> {
                // a >= b <=> !(a<b)
                curBlock.addIns(new ASMArithIns("slt", ASMReg.t1, rs1, rs2), it.toString() + getVarASMitem(it));
                curBlock.addIns(new ASMArithiIns("xori", rd, ASMReg.t1, 1), it.toString() + getVarASMitem(it));
            }
            default -> throw new UnsupportedOperationException("Unknown icmpIns op");
        }
        if (asmitem instanceof ASMAddr) {
            handleASMAddrStore(rd, (ASMAddr) asmitem, ASMReg.t2, "store icmp result");
        }
        return null;
    }



    @Override
    public ASMHelper visit(loadIns it) {
        var asmitem = var2ASMItem.get(it.result);
        var rd = asmitem instanceof ASMReg ? (ASMReg) asmitem : ASMReg.t1;

        if (it.pointer.isGlobal()) {
            curBlock.addIns(new ASMLoadIns(rd, it.pointer.name.substring(1)), it.toString() + getVarASMitem(it));
            if (asmitem instanceof ASMAddr) handleASMAddrStore(rd, (ASMAddr) asmitem, ASMReg.t2, "store load result");
        } else {
            var rs = loadIRitem(it.pointer, ASMReg.t2, "load pointer");
            curBlock.addIns(new ASMLoadIns(rd, new ASMAddr(rs, 0)), it.toString() + getVarASMitem(it));
            if (asmitem instanceof ASMAddr) handleASMAddrStore(rd, (ASMAddr) asmitem, ASMReg.t2, "store load result");
        }
        return null;
    }

    @Override
    public ASMHelper visit(storeIns it) {
        var rs = loadIRitem(it.value, ASMReg.t1, "load value");

        if (it.pointer.isGlobal()) {
            curBlock.addIns(new ASMStoreIns(rs, it.pointer.name.substring(1), ASMReg.t2), it.toString() + getVarASMitem(it));
        } else {
            var ptr = loadIRitem(it.pointer, ASMReg.t2, "load pointer");
            curBlock.addIns(new ASMStoreIns(rs, new ASMAddr(ptr, 0)), it.toString() + getVarASMitem(it));
        }
        return null;   
    }



    @Override
    public ASMHelper visit(getelementptrIns it) {
        var base = loadIRitem(it.pointer, ASMReg.t1, "load base");

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
            handleASMAddi(rd, base, offset, it.toString() + getVarASMitem(it));
        } else {
            var offset = loadIRitem(item, ASMReg.t2, "load offset");
            curBlock.addIns(new ASMArithiIns("slli", ASMReg.t2, offset, 2), "offset *= 4"); // offset *= 4
            curBlock.addIns(new ASMArithIns("add", rd, base, ASMReg.t2), it.toString() + getVarASMitem(it));
        }
        if (asmitem instanceof ASMAddr) handleASMAddrStore(rd, (ASMAddr) asmitem, ASMReg.t1, "store gep result");
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
        // handle return value
        if (!it.value.type.equals(IRType.IRvoidType)) {
            if (it.value instanceof IRLiteral) {
                curBlock.addIns(new ASMLoadImmIns(ASMReg.a0, ((IRLiteral)it.value).getInt()), "load return value (imm)");
            } else {
                var rs = loadIRitem(it.value, ASMReg.a0, "load return value");
                curBlock.addIns(new ASMMoveIns(ASMReg.a0, rs), "return: move return value");
            }
        }
        // restore callee-save registers
        for (var entry : calleeSaveMap.entrySet()) {
            handleASMAddrLoad(entry.getKey(), entry.getValue(), "restore callee-save registers");
        }

        handleASMAddrLoad(ASMReg.ra, new ASMAddr(ASMReg.sp, curFunc.stackSize - 4), "restore ra");
        handleASMAddi(ASMReg.sp, ASMReg.sp, curFunc.stackSize, "return stackSize");
        curBlock.addIns(new ASMReturnIns(), it.toString() + getVarASMitem(it));
        return null;
    }



    



    @Override
    public ASMHelper visit(IRFuncDec it) {
        throw new UnsupportedOperationException("Should Not visit IRFuncDec");
    }

    private void initRegMap(int maxUsedReg) {
        // init regMap
        regMap.clear();
        int regCnt = 0;
        for (int i = 0; i < 8; ++i) {
            if (regCnt >= maxUsedReg) return;
            regMap.put(regCnt++, ASMReg.a(i));
        }
        for (int i = 0; i < 12; ++i) {
            if (regCnt >= maxUsedReg) return;
            regMap.put(regCnt++, ASMReg.s(i));
        }
        for (int i = 3; i < 7; ++i) {
            if (regCnt >= maxUsedReg) return;
            regMap.put(regCnt++, ASMReg.t(i));
        }
    }

    @Override
    public ASMHelper visit(IRFuncDef it) {
        curFunc = new ASMFuncDefNode(it.getName().substring(1), it.params.size());
        root.funcs.add(curFunc);

        initRegMap(it.maxUsedReg);

        // calculate stack size
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

        var2ASMItem = new HashMap<>();
        calleeSaveMap = new HashMap<>();
        callerSaveMap = new HashMap<>();
        blockMap = new HashMap<>();


        for (var entry : it.regOfVar.entrySet()) {
            var2ASMItem.put(entry.getKey(), regMap.get(entry.getValue()));   
        }
        curFunc.stackSpillOffset = curFunc.stackSize - (4 + 4 * it.spilledVar.size());
        curFunc.regSaveCur = curFunc.stackSpillOffset;

        // !! curFunc.stackSize - 4  save ra
        for (var entry : it.spilledVar.entrySet()) {
            var2ASMItem.put(entry.getKey(), new ASMAddr(ASMReg.sp, curFunc.stackSpillOffset + 4 * entry.getValue()));
            assert curFunc.stackSpillOffset + 4 * entry.getValue() < curFunc.stackSize - 4 : "ASMBuilder: spilled var overflow";
        }

        for (var block : it.blocks.values()) {
            if (block.empty()) throw new RuntimeException("ASMBuiler: block empty");
            curBlock = new ASMBlock(getLabel(block.getLabel()));
            curFunc.addBlock(curBlock);
            blockMap.put(block, curBlock);

            if (block.getLabel().equals("entry")) {
                handleASMAddi(ASMReg.sp, ASMReg.sp, -curFunc.stackSize, "fetch stackSize");
                handleASMAddrStore(ASMReg.ra, new ASMAddr(ASMReg.sp, curFunc.stackSize - 4), ASMReg.t1, "save ra");

                // handle params
                for (int i = 0; i < it.params.size(); ++i) {
                    var asmitem = var2ASMItem.get(it.params.get(i));
                    if (asmitem == null) {
                        // no use param
                        continue;
                    }
                    if (i < 8) {
                        // a0 - a7 : 10 - 17
                        handlemove(asmitem, ASMReg.a(i), "load param %d".formatted(i));
                    } else {
                        var par = new ASMAddr(ASMReg.sp, curFunc.stackSize + 4 * (i - 8));
                        handlemove(asmitem, par, "load param %d (from stack)".formatted(i));
                    }
                }

                // save callee-save registers
                for (var reg : regMap.values()) {
                    if (reg.isCalleeSave()) {
                        curFunc.regSaveCur -= 4;
                        var addr = new ASMAddr(ASMReg.sp, curFunc.regSaveCur);
                        calleeSaveMap.put(reg, addr);
                        handleASMAddrStore(reg, addr, ASMReg.t1, "save callee-save registers");
                    }
                }
            }

            for (var ins : block.insList) {
                ins.accecpt(this);
            }
            block.endIns.accecpt(this);
        }

        // handle phiIns
        for (var block : it.blocks.values()) {
            if (block.empty()) throw new RuntimeException("ASMBuiler: block empty");
            if (block.phiList.isEmpty()) continue;
            for (var prev : block.getPrevBlocks()) {
                var prevBlock = blockMap.get(prev);

                List<Pair<IRitem, IRitem>> moves = new ArrayList<>();
                for (var phi : block.phiList) {
                    moves.add(new Pair<>(phi.getValue(prev.getLabel()), phi.result));
                }

                insertMove(prevBlock, moves);
            }

        }
    

        return null;
    }

    private String getVarASMitem(IRIns ins) {
        String str = "     map: ";
        if (ins.getDef() != null) {
            str += ins.getDef().toString() + "->" + var2ASMItem.get(ins.getDef()).toString() + ", ";
        }
        for (var item : ins.getUses()) {
            str += item.toString() + "->" + var2ASMItem.get(item).toString() + ", ";
        }
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }

    private void insertMove(ASMBlock block, List<Pair<IRitem, IRitem>> moves) {
        curBlock = block;

        List<Pair<ASMItem, ASMItem>> moveQueue = new ArrayList<>();
        List<Pair<IRitem, ASMItem>> literalMoves = new ArrayList<>();

        HashMap<ASMItem, Integer> outDeg = new HashMap<>();
        HashMap<ASMItem, ASMItem> dest2src = new HashMap<>();
        Queue<ASMItem> worklist = new ArrayDeque<>();


        for (var move : moves) {
            if (move.first == null) continue;
            if (move.first instanceof IRLiteral) {
                literalMoves.add(new Pair<>((IRLiteral) move.first, var2ASMItem.get(move.second)));
            } else if (((IRvar)move.first).isGlobal()) {
                literalMoves.add(new Pair<>((IRvar) move.first, var2ASMItem.get(move.second)));

            } else {
                var src = var2ASMItem.get((IRvar)move.first);
                var dst = var2ASMItem.get((IRvar)move.second);
                if (src.equals(dst)) continue;
                dest2src.put(dst, src);
                if (!outDeg.containsKey(src)) outDeg.put(src, 0);
                if (!outDeg.containsKey(dst)) outDeg.put(dst, 0);
                outDeg.put(src, outDeg.get(src) + 1);
            }
        }

        // get initial worklist outdeg = 0
        for (var entry : outDeg.entrySet()) {
            if (entry.getValue() == 0) {
                worklist.add(entry.getKey());
            }
        }

        // topu
        while (!worklist.isEmpty()) {
            var dst = worklist.poll();
            if (dest2src.containsKey(dst)) {
                var src = dest2src.get(dst);
                moveQueue.add(new Pair<ASMItem,ASMItem>(src, dst));
                outDeg.put(src, outDeg.get(src) - 1);
                if (outDeg.get(src) == 0) {
                    worklist.add(src);
                }
            }
        }

        // handle loops 
        List<ASMItem> rest = new ArrayList<>();
        for (var entry : outDeg.entrySet()) {
            if (entry.getValue() != 0) {
                assert entry.getValue() == 1;
                rest.add(entry.getKey());
            }
        }

        for (var item : rest) if (outDeg.get(item) != 0) {
            List<ASMItem> loop = new ArrayList<>();
            loop.add(item);
            outDeg.put(item, 0);
            var tmp = dest2src.get(item);
            while (!tmp.equals(item)) {
                loop.add(tmp);
                outDeg.put(tmp, 0);
                tmp = dest2src.get(tmp);
            }

            moveQueue.add(new Pair<ASMItem,ASMItem>(item, ASMReg.t0));
            for (int i = 1; i < loop.size(); ++i) {
                moveQueue.add(new Pair<ASMItem,ASMItem>(loop.get(i), loop.get(i - 1)));
            }
            moveQueue.add(new Pair<ASMItem,ASMItem>(ASMReg.t0, loop.getLast()));
        }

        for (var entry : moveQueue) {
            handlemove(entry.second, entry.first, "Phi insert move");
        }

        for (var entry : literalMoves) {
            if (entry.first instanceof IRLiteral) {
                var x = (IRLiteral)entry.first;
                if (entry.second.inReg()) {
                    curBlock.addIns(new ASMLoadImmIns((ASMReg)entry.second, x.getInt()), "Phi insert literal move");
                } else {
                    curBlock.addIns(new ASMLoadImmIns(ASMReg.t1, x.getInt()), "Phi insert literal move");
                    handleASMAddrStore(ASMReg.t1, (ASMAddr)entry.second, ASMReg.t2, "Phi insert literal move (store to stack)");
                }
            } else {
                var x = (IRvar)entry.first;
                if (entry.second.inReg()) {
                    curBlock.addIns(new ASMLoadAddrIns((ASMReg)entry.second, x.name.substring(1)), "Phi insert Globalvar move");
                } else {
                    curBlock.addIns(new ASMLoadAddrIns(ASMReg.t1, x.name.substring(1)), "Phi insert Globalvar move");
                    handleASMAddrStore(ASMReg.t1, (ASMAddr)entry.second, ASMReg.t2, "Phi insert Globalvar move (store to stack)");
                }
            }

        }

    }

}
