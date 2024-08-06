package AST.Node.def;

import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.ASTNode;
import AST.Node.stmt.BlockStmtNode;
import Util.position;
import Util.Type;

public class FuncDefNode extends ASTNode {
    final public ArrayList<VarDefNode> params = new ArrayList<>();
    final public BlockStmtNode body;
    public String name;
    public Type type;


    public FuncDefNode(position pos, BlockStmtNode body) {
        super(pos);
        this.body = body;
    }

    @Override
    public String toString() {
        String str = type.GetName() + name + "(";
        if (params != null) {
            for (VarDefNode param : params) {
                str += param.toString() + ", ";
            }
        }
        str += ") " + body.toString();
        return str;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
