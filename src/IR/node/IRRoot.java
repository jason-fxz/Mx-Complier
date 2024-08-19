package IR.node;

import java.util.ArrayList;

import IR.node.def.IRFuncDec;
import IR.node.def.IRFuncDef;
import IR.node.def.IRStrDef;
import IR.node.def.IRStructDef;
import IR.node.def.IRglobalVarDef;

public class IRRoot {
    public ArrayList<IRglobalVarDef> gVars;
    public ArrayList<IRStrDef> gStr;
    public ArrayList<IRStructDef> gStrust;
    public ArrayList<IRFuncDef> funcs;
    public ArrayList<IRFuncDec> builtinfuncs;


    public IRRoot() {
        gVars = new ArrayList<>();
        funcs = new ArrayList<>();
        gStrust = new ArrayList<>();
        gStr = new ArrayList<>();
        builtinfuncs = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (var gStrust : gStrust) {
            str.append(gStrust.toString()).append('\n');
        }
        for (var gVar : gVars) {
            str.append(gVar.toString()).append('\n');
        }
        for (var gStr : gStr) {
            str.append(gStr.toString()).append('\n');
        }
        for (var func : builtinfuncs) {
            str.append(func.toString()).append('\n');
        }
        str.append("\n");
        for (var func : funcs) {
            str.append(func.toString()).append('\n');
        }
        return str.toString();
    }


    
}
