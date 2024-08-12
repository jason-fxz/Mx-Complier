package Util.info;

import java.util.HashMap;

import Util.position;
import Util.error.MultipleDefinitionsError;

public class ClassInfo extends BaseInfo {
    private HashMap<String, TypeInfo> members;
    private HashMap<String, FuncInfo> methods;
    public position defpos;

    public ClassInfo(String label, position defpos) {
        super(label);
        this.defpos = defpos;
        members = new HashMap<>();
        methods = new HashMap<>();
    }

    public ClassInfo(String label, FuncInfo ...methods) {
        super(label);
        this.defpos = new position();
        members = new HashMap<>();
        this.methods = new HashMap<>();
        for (FuncInfo method : methods) {
            this.AddMethod(method.label, method, new position());
        }
    }

    

    public void AddMember(String name, TypeInfo type, position pos) {
        if (members.containsKey(name)) {
            throw new MultipleDefinitionsError(name, pos);
        }
        members.put(name, type);
    }

    public void AddMethod(String name, FuncInfo func, position pos) {
        if (methods.containsKey(name) || members.containsKey(name)) {
            throw new MultipleDefinitionsError(name, pos);
        }
        var type = new TypeInfo(func.retType);
        type.isFunc = true;
        members.put(name, type);
        methods.put(name, func);
    }

    public TypeInfo GetMemberType(String name) {
        if (members.containsKey(name)) {
            return members.get(name);
        }
        return null;
    }

    public FuncInfo GetMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }
        return null;
    }
    
    public HashMap<String, TypeInfo> GetMembers() {
        return members;
    }

    public HashMap<String, FuncInfo> GetMethods() {
        return methods;
    }
}
