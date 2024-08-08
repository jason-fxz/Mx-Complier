package AST.Node;

import Util.position;
import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.def.*;
import AST.Node.stmt.VarDefStmtNode;

public class RootNode extends ASTNode {
    public ArrayList<DefNode> Defs = new ArrayList<>();

    public RootNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = "";
        for (ClassDefNode classDef : classDefs) {
            str += classDef.toString() + "\n";
        }
        for (FuncDefNode funcDef : funcDefs) {
            str += funcDef.toString() + "\n";
        }
        for (VarDefStmtNode varDef : varDefs) {
            str += varDef.toString() + "\n";
        }
        return str;
    }

}
