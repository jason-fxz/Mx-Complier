package IR.node;

import java.util.ArrayList;

import IR.node.def.IRFuncDef;
import IR.node.def.IRStrDef;
import IR.node.def.IRStructDef;
import IR.node.def.IRglobalVarDef;

public class IRRoot {
    public ArrayList<IRglobalVarDef> gVars;
    public ArrayList<IRStrDef> gStr;
    public ArrayList<IRStructDef> gStrust;
    public ArrayList<IRFuncDef> funcs;


    public IRRoot() {
        gVars = new ArrayList<>();
        funcs = new ArrayList<>();
    }
}
