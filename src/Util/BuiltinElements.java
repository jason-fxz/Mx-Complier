package Util;

import Util.info.TypeInfo;
import Util.info.ClassInfo;
import Util.info.FuncInfo;

public class BuiltinElements {
    // builtin types
    static public TypeInfo voidType = new TypeInfo("void", 0);
    static public TypeInfo intType = new TypeInfo("int", 0);
    static public TypeInfo boolType = new TypeInfo("bool", 0);
    static public TypeInfo stringType = new TypeInfo("string", 0);
    static public TypeInfo nullType = new TypeInfo("null", 0);
    static public TypeInfo thisType = new TypeInfo("this", 0);

    static public TypeInfo[] BuiltinTypes = { voidType, intType, boolType, stringType, nullType, thisType };

    // builtin functions
    static public FuncInfo printFunc = new FuncInfo("print", voidType, stringType);
    static public FuncInfo printlnFunc = new FuncInfo("println", voidType, stringType);
    static public FuncInfo printIntFunc = new FuncInfo("printInt", voidType, intType);
    static public FuncInfo printlnIntFunc = new FuncInfo("printlnInt", voidType, intType);
    static public FuncInfo getStringFunc = new FuncInfo("getString", stringType);
    static public FuncInfo getIntFunc = new FuncInfo("getInt", intType);
    static public FuncInfo toStringFunc = new FuncInfo("toString", stringType, intType);

    static public FuncInfo[] BuiltinFuncs = { printFunc, printlnFunc, printIntFunc, printlnIntFunc, getStringFunc,
            getIntFunc, toStringFunc };

    // builtin methods for array and string
    static public FuncInfo arraySizeFunc = new FuncInfo("size", intType);
    static public FuncInfo stringLengthFunc = new FuncInfo("length", intType);
    static public FuncInfo stringSubstringFunc = new FuncInfo("substring", intType);
    static public FuncInfo stringParseIntFunc = new FuncInfo("parseInt", intType);
    static public FuncInfo stringOrdFunc = new FuncInfo("ord", intType);

    // builtin classes
    static public ClassInfo intClass = new ClassInfo("int");
    static public ClassInfo boolClass = new ClassInfo("bool");
    static public ClassInfo stringClass = new ClassInfo("string", stringLengthFunc, stringSubstringFunc,
            stringParseIntFunc, stringOrdFunc);

    static public ClassInfo[] BuiltinClasses = {stringClass};
}
