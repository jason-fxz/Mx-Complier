package IR;

import IR.node.*;
import IR.node.def.*;
import IR.node.ins.*;

public interface IRvisitor<T> {

    T visit(jumpIns jumpIns);

    T visit(IRblock iRblock);

    T visit(allocaIns allocaIns);

    T visit(arithIns arithIns);

    T visit(branchIns branchIns);

    T visit(callIns callIns);


    T visit(icmpIns icmpIns);

    T visit(loadIns loadIns);

    T visit(getelementptr getelementptr);

    T visit(IRRoot irRoot);

    T visit(phiIns phiIns);

    T visit(selectIns selectIns);

    T visit(returnIns returnIns);

    T visit(storeIns storeIns);

    T visit(IRFuncDec irFuncDec);

    T visit(IRFuncDef irFuncDef);

    T visit(IRglobalVarDef iRglobalVarDef);

    T visit(IRStrDef irStrDef);

    T visit(IRStructDef irStructDef);


    
    
}
