package IR.node.def;

import IR.IRvisitor;
import IR.item.IRvar;
import IR.node.IRblock;
import IR.type.IRType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class IRFuncDef extends IRDefNode {
    public String name;
    public IRType returnType;
    public ArrayList<IRvar> params;
    public IRblock entryBlock;
    public LinkedHashMap<String, IRblock> blockList;


    public IRFuncDef(String name, IRType returnType) {
        this.name = name;
        this.returnType = returnType;
        this.params = new ArrayList<>();
        this.entryBlock = new IRblock("entry");
        this.blockList = new LinkedHashMap<>();
        this.blockList.put("entry", this.entryBlock);
    }

    public void addParam(IRvar param) {
        params.add(param);
    }

    public IRblock newBlock(String label) {
        IRblock block = new IRblock(label);
        blockList.put(label, block);
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
        for (var entry : blockList.entrySet()) {
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

    // public int StackVarCount()  {
    //     int count = 0, mxcall = 0;
    //     for (IRblock block : blockList) {
    //         count += block.StackVarCount();
    //         mxcall = Math.max(mxcall, block.maxCallparams());
    //     }
    //     return count + mxcall - 8;
    // }
    

}