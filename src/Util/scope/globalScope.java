package Util.scope;

import java.util.HashMap;

import Util.error.MultipleDefinitionsError;
import Util.info.*;


public class globalScope extends Scope {
    private HashMap<String, ClassInfo> ClassDefs;
    private HashMap<String, FuncInfo> FunDefs;
    
    public globalScope() {
        super(null);
        ClassDefs = new HashMap<>();
        FunDefs = new HashMap<>();
    }

    public void AddFunc(String name, FuncInfo func) {
        if (FunDefs.containsKey(name)) {
            throw new MultipleDefinitionsError(name, func.defpos);
        }
        FunDefs.put(name, func);
    }

    public void AddClass(String name, ClassInfo classInfo) {
        if (ClassDefs.containsKey(name)) {
            throw new MultipleDefinitionsError(name, classInfo.defpos);
        }
        ClassDefs.put(name, classInfo);
    }

    public FuncInfo GetFunc(String name) {
        if (FunDefs.containsKey(name)) {
            return FunDefs.get(name);
        }
        return null;
    }

    public ClassInfo GetClass(String name) {
        if (ClassDefs.containsKey(name)) {
            return ClassDefs.get(name);
        }
        return null;
    }

    public TypeInfo GetClassMemberType(String className, String memberName) {
        if (ClassDefs.containsKey(className)) {
            return ClassDefs.get(className).GetMemberType(memberName);
        }
        return null;
    }

    public FuncInfo GetClassMethod(String className, String methodName) {
        if (ClassDefs.containsKey(className)) {
            return ClassDefs.get(className).GetMethod(methodName);
        }
        return null;
    }    
}

