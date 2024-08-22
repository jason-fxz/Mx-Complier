package IR;

import IR.node.*;
import IR.node.def.*;
import IR.node.ins.*;

public interface IRvisitor<T> {

    T visit(jumpIns it);

    T visit(IRblock it);

    T visit(allocaIns it);

    T visit(arithIns it);

    T visit(branchIns it);

    T visit(callIns it);


    T visit(icmpIns it);

    T visit(loadIns it);

    T visit(getelementptrIns it);

    T visit(IRRoot it);

    T visit(phiIns it);

    T visit(selectIns it);

    T visit(returnIns it);

    T visit(storeIns it);

    T visit(IRFuncDec it);

    T visit(IRFuncDef it);

    T visit(IRglobalVarDef it);

    T visit(IRStrDef it);

    T visit(IRStructDef it);


    
    
}
