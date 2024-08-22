package ASM.node;

import java.util.ArrayList;

import ASM.ASMVisitor;
import ASM.node.global.*;


public class ASMRoot extends ASMNode {
    public ArrayList<ASMFuncDefNode> funcs;
    public ArrayList<ASMglobalVarDef> globalVars;
    public ArrayList<ASMglobalStrDef> globalStrs;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ASMFuncDefNode func : funcs) {
            sb.append(func.toString());
        }
        for (ASMglobalVarDef globalVar : globalVars) {
            sb.append(globalVar.toString());
        }
        return sb.toString();
    }
    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    


}
