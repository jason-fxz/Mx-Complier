package AST.Node;

import Util.position;
import java.util.ArrayList;

import AST.Node.def.ClassDefNode;
import AST.Node.def.FuncDefNode;
import AST.Node.def.VarDefNode;

public class RootNode {
    public ArrayList<ClassDefNode> classDefs = new ArrayList<>();
    public ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
    public ArrayList<VarDefNode> varDefs = new ArrayList<>();

    
}
