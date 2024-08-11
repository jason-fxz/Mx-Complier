package AST.Node.def;

import Util.position;
import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.stmt.StmtNode;

public class ClassDefNode extends DefNode {
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public ArrayList<VarsDefNode> varsDefs = new ArrayList<>();
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
        String str = "Class " + name + " {\n";
        StmtNode.indentDepth++;
        if (constructor != null) {
            str += "    " + constructor.toString() + "\n";
        }
        for (VarsDefNode varDef : varsDefs) {
            str += "    " + varDef.toString() + "\n";
        }
        for (FuncDefNode funcDef : funcDefs) {
            str += "    " + funcDef.toString() + "\n";
        }
        str += "}";
        StmtNode.indentDepth--;
        return str;
    }
}
