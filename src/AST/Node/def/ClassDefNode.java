package AST.Node.def;

import Util.position;
import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.stmt.StmtNode;
import AST.Node.stmt.VarDefStmtNode;

public class ClassDefNode extends DefNode {
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public ArrayList<VarDefStmtNode> varDefs = new ArrayList<>();
    public FuncDefNode constructor = null;
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
