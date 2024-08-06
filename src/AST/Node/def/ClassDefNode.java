package AST.Node.def;

import Util.position;
import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.ASTNode;
import AST.Node.stmt.StmtNode;

public class ClassDefNode extends ASTNode {
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public ArrayList<VarDefNode> varDefs = new ArrayList<>();
    FuncDefNode constructor;
    public String name;
    
    public ClassDefNode(position pos, String name) {
        super(pos);
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = "Class " + name + "{\n";
        StmtNode.indentDepth++;
        if (constructor != null) {
            str += " " + constructor.toString() + "\n";
        }
        str += varDefs.toString();
        str += funcDefs.toString();
        str += "\n}";
        StmtNode.indentDepth--;
        return str;
    }
}
