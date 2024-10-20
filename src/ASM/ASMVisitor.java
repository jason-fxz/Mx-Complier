package ASM;

import ASM.node.*;
import ASM.node.global.*;
import ASM.node.ins.*;

public interface ASMVisitor<T> {

    T visit(ASMFuncDefNode node);

    T visit(ASMBrzIns asmBeqzIns);

    T visit(ASMArithIns asmBinaryIns);

    T visit(ASMBlock asmBlock);

    T visit(ASMCallIns asmCallIns);

    T visit(ASMJumpIns asmJumpIns);

    T visit(ASMLoadAddrIns asmLoadAddrIns);

    T visit(ASMLoadImmIns asmLoadImmIns);

    T visit(ASMLoadIns asmLoadIns);

    T visit(ASMMoveIns asmMoveIns);

    T visit(ASMReturnIns asmReturnIns);

    T visit(ASMStoreIns asmStoreIns);

    T visit(ASMRoot asmRoot);

    T visit(ASMglobalVarDef asMglobalVarDef);

    T visit(ASMglobalStrDef asMglobalStrDef);

    T visit(ASMArithiIns asmArithiIns);

    T visit(ASMUnaryIns asmUnaryIns);

    T visit(ASMBrIns asmBrIns);
    
}