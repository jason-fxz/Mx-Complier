package ASM;

import ASM.node.*;
import ASM.node.global.*;
import ASM.node.ins.*;

public interface ASMVisitor<T> {

    T visit(ASMFuncDefNode node);

    T visit(ASMBeqzIns asmBeqzIns);

    T visit(ASMBinaryIns asmBinaryIns);

    T visit(ASMBlock asmBlock);

    T visit(ASMCallIns asmCallIns);

    T visit(ASMJumpIns asmJumpIns);

    T visit(ASMLoadAddrIns asmLoadAddrIns);

    T visit(ASMLoadImmIns asmLoadImmIns);

    T visit(ASMLoadIns asmLoadIns);

    T visit(ASMMoveIns asmMoveIns);

    T visit(ASMReturnIns asmReturnIns);

    T visit(ASMStoreIns asmStoreIns);
    
}