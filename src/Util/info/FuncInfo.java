package Util.info;

import java.util.ArrayList;

import Util.position;

public class FuncInfo extends BaseInfo {
    public TypeInfo retType;
    public ArrayList<TypeInfo> argsType;
    public position defpos;

    public FuncInfo(TypeInfo retType, position defpos) {
        this.retType = retType;
        argsType = new ArrayList<>();
    } 

    public void AddArguement(TypeInfo arg) {
        argsType.add(arg);
    }
}
