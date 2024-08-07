package AST.Node;

import Util.position;
import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.def.ClassDefNode;
import AST.Node.def.FuncDefNode;
import AST.Node.def.VarDefNode;

public class RootNode extends ASTNode {
    public ArrayList<ClassDefNode> classDefs = new ArrayList<>();
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public ArrayList<VarDefNode> varDefs = new ArrayList<>();

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
        for (VarDefNode varDef : varDefs) {
            str += varDef.toString() + "\n";
        }
        return str;
    }

}
