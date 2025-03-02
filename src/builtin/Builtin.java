package builtin;

import java.util.HashSet;

public class Builtin {
    static HashSet<String> builtinFuncs = new HashSet<>();
        
    static {
        // Input / Output
        builtinFuncs.add("@print");
        builtinFuncs.add("@println");
        builtinFuncs.add("@printInt");
        builtinFuncs.add("@printlnInt");
        builtinFuncs.add("@getString");
        builtinFuncs.add("@getInt");
        
        // String 
        builtinFuncs.add("@toString");
        builtinFuncs.add("@string.length");
        builtinFuncs.add("@string.substring");
        builtinFuncs.add("@string.parseInt");
        builtinFuncs.add("@string.ord");
        builtinFuncs.add("@__mx_string_compare");
        builtinFuncs.add("@__mx_string_concat");
        builtinFuncs.add("@__mx_bool_to_string");
        
        // Memory
        builtinFuncs.add("@__mx_allocate");
        builtinFuncs.add("@__mx_allocate_array");
        builtinFuncs.add("@__mx_array_size");
    }


    
    public static boolean isBuiltinFunc(String name) {
        return builtinFuncs.contains(name);
    }
}
