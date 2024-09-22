package IR.node.def;

import IR.IRvisitor;
import IR.item.IRvar;
import IR.node.IRblock;
import IR.node.ins.allocaIns;
import IR.type.IRType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;


public class IRFuncDef extends IRDefNode {
    public String name;
    public IRType returnType;
    public ArrayList<IRvar> params;
    public IRblock entryBlock;
    public LinkedHashMap<String, IRblock> blocks;

    public HashMap<IRvar, Integer> spilledVar;
    public HashMap<IRvar, Integer> regOfVar;


    public IRFuncDef(String name, IRType returnType) {
        this.name = name;
        this.returnType = returnType;
        this.params = new ArrayList<>();
        this.entryBlock = new IRblock("entry");
        this.blocks = new LinkedHashMap<>();
        this.blocks.put("entry", this.entryBlock);
    }

    public void addParam(IRvar param) {
        params.add(param);
    }

    public IRblock newBlock(String label) {
        IRblock block = new IRblock(label);
        blocks.put(label, block);
        return block;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("define " + returnType.toString() + " " + name + "(");
        for (int i = 0; i < params.size(); i++) {
            if (i != 0) {
                str.append(", ");
            }
            str.append(params.get(i).type.toString() + " " + params.get(i).toString());
        }
        str.append(") {\n");
        for (var entry : blocks.entrySet()) {
            str.append(entry.getValue().toString());
            str.append("\n");
        }
        str.append("}\n");   
        return str.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public ArrayList<IRvar> getAllocas() {
        ArrayList<IRvar> result = new ArrayList<>();
        for (var ins : entryBlock.insList) {
            if (ins instanceof allocaIns) {
                var allocins = (allocaIns) ins;
                IRvar var = new IRvar(allocins.type, allocins.result.name);
                result.add(var);
            }
        }
        return result;
    }

    // public int StackVarCount()  {
    //     int count = 0, mxcall = 0;
    //     for (IRblock block : blockList) {
    //         count += block.StackVarCount();
    //         mxcall = Math.max(mxcall, block.maxCallparams());
    //     }
    //     return count + mxcall - 8;
    // }
    

}