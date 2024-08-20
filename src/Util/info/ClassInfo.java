package Util.info;

import java.util.HashMap;
import java.util.LinkedHashMap;

import Util.position;
import Util.error.MultipleDefinitionsError;

public class ClassInfo extends BaseInfo {
    public LinkedHashMap<String, TypeInfo> members;
    public HashMap<String, Integer> memberId; 
    public LinkedHashMap<String, FuncInfo> methods;
    public position defpos;

    public ClassInfo(String label, position defpos) {
        super(label);
        this.defpos = defpos;
        members = new LinkedHashMap<>();
        methods = new LinkedHashMap<>();
        memberId = new HashMap<>();
    }

    public ClassInfo(String label, FuncInfo ...methods) {
        super(label);
        this.defpos = new position();
        members = new LinkedHashMap<>();
        this.methods = new LinkedHashMap<>();
        for (FuncInfo method : methods) {
            String name = method.label;
            method.label = label + "." + method.label;
            this.AddMethod(name, method, new position());
        }
    }

    

    public void AddMember(String name, TypeInfo type, position pos) {
        if (members.containsKey(name) || methods.containsKey(name)) {
            throw new MultipleDefinitionsError(name, pos);
        }
        members.put(name, type);
        memberId.put(name, memberId.size());
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

    public int GetMemberId(String name) {
        if (memberId.containsKey(name)) {
            return memberId.get(name);
        }
        return -1;
    }
    
    public LinkedHashMap<String, TypeInfo> GetMembers() {
        return members;
    }

    public LinkedHashMap<String, FuncInfo> GetMethods() {
        return methods;
    }
}
