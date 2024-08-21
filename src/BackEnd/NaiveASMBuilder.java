package BackEnd;

import ASM.ASMHelper;
import IR.IRvisitor;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDec;
import IR.node.def.IRFuncDef;
import IR.node.def.IRStrDef;
import IR.node.def.IRStructDef;
import IR.node.def.IRglobalVarDef;
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

public class NaiveASMBuilder implements IRvisitor<ASMHelper> {

    @Override
    public ASMHelper visit(jumpIns jumpIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(IRblock iRblock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(allocaIns allocaIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(arithIns arithIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
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
    public ASMHelper visit(IRRoot irRoot) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
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
    public ASMHelper visit(returnIns returnIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(storeIns storeIns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(IRFuncDec irFuncDec) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(IRFuncDef irFuncDef) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(IRglobalVarDef iRglobalVarDef) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(IRStrDef irStrDef) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public ASMHelper visit(IRStructDef irStructDef) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }
    
}
