package IR.node.def;

import IR.item.IRvar;
import IR.node.IRblock;
import IR.type.IRType;
import java.util.ArrayList;

public class IRFuncDef {
    String name;
    IRType returnType;
    ArrayList<IRvar> params;
    public IRblock entryBlock;
    public ArrayList<IRblock> blockList;


    public IRFuncDef(String name, IRType returnType) {
        this.name = name;
        this.returnType = returnType;
        this.params = new ArrayList<>();
        this.entryBlock = new IRblock("entry");
        this.blockList = new ArrayList<>();
        this.blockList.add(this.entryBlock);
    }

    public void addParam(IRvar param) {
        params.add(param);
    }

    public IRblock newBlock(String label) {
        IRblock block = new IRblock(label);
        blockList.add(block);
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
            str.append(params.get(i).type.toString() +  params.get(i).toString());
        }
        str.append(") {\n");
        for (IRblock block : blockList) {
            str.append(block.toString());
        }
        str.append("}\n");   
        return str.toString();
    }

}