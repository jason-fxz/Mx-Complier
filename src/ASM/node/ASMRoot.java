package ASM.node;

import java.util.ArrayList;

import ASM.ASMVisitor;
import ASM.node.global.*;


public class ASMRoot extends ASMNode {
    public ArrayList<ASMFuncDefNode> funcs;
    public ArrayList<ASMglobalVarDef> globalVars;
    public ArrayList<ASMglobalStrDef> globalStrs;

    public ASMRoot() {
        funcs = new ArrayList<>();
        globalVars = new ArrayList<>();
        globalStrs = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    .text\n");
        for (ASMFuncDefNode func : funcs) {
            sb.append(func.toString());
        }
        sb.append("\n\n    .section .data\n");
        for (ASMglobalVarDef globalVar : globalVars) {
            sb.append(globalVar.toString());
            sb.append("\n");
        }
        sb.append("\n\n    .section .rodata\n");
        for (ASMglobalStrDef globalStr : globalStrs) {
            sb.append(globalStr.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    


}
