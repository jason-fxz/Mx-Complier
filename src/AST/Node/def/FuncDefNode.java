package AST.Node.def;

import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.stmt.BlockStmtNode;
import Util.position;
import Util.info.TypeInfo;

public class FuncDefNode extends DefNode {
    final public ArrayList<VarDefNode> params = new ArrayList<>();
    final public BlockStmtNode body;
    public String name;
    public TypeInfo type;


    public FuncDefNode(position pos, BlockStmtNode body) {
        super(pos);
        this.body = body;
    }
    
    @Override
    public String toString() {
        String str = type.GetTypeName() + " " + name + "(";
        if (params != null) {
            for (VarDefNode param : params) {
                str += param.toString() + ", ";
            }
        }
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        str += ") " + body.toString0();
        return str;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
