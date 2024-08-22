package IR.node;

import java.util.ArrayList;

import IR.IRvisitor;
import IR.node.def.IRFuncDec;
import IR.node.def.IRFuncDef;
import IR.node.def.IRStrDef;
import IR.node.def.IRStructDef;
import IR.node.def.IRglobalVarDef;

public class IRRoot extends IRNode {
    public ArrayList<IRglobalVarDef> gVars;
    public ArrayList<IRStrDef> gStrs;
    public ArrayList<IRStructDef> gStrusts;
    public ArrayList<IRFuncDef> funcs;
    public ArrayList<IRFuncDec> builtinfuncs;


    public IRRoot() {
        gVars = new ArrayList<>();
        funcs = new ArrayList<>();
        gStrusts = new ArrayList<>();
        gStrs = new ArrayList<>();
        builtinfuncs = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (var func : builtinfuncs) {
            str.append(func.toString());
        }
        str.append("\n");
        for (var gStrust : gStrusts) {
            str.append(gStrust.toString()).append('\n');
        }
        for (var gVar : gVars) {
            str.append(gVar.toString()).append('\n');
        }
        for (var gStr : gStrs) {
            str.append(gStr.toString()).append('\n');
        }
        str.append("\n");
        for (var func : funcs) {
            str.append(func.toString()).append('\n');
        }
        return str.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }


    
}
