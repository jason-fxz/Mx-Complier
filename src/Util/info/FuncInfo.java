package Util.info;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Util.position;

public class FuncInfo extends BaseInfo {
    public TypeInfo retType;
    public ArrayList<TypeInfo> argsType;
    public position defpos;

    public FuncInfo(String label, TypeInfo retType, position defpos) {
        super(label);
        this.retType = retType;
        this.defpos = defpos;
        argsType = new ArrayList<>();
    } 

    public FuncInfo(String label, TypeInfo reypInfo, TypeInfo... params) {
        super(label);
        this.retType = reypInfo;
        this.argsType = new ArrayList<>();
        this.defpos = new position();
        for (TypeInfo param : params) {
            this.argsType.add(param);
        }
    }

    public void AddArguement(TypeInfo arg) {
        argsType.add(arg);
    }
}
