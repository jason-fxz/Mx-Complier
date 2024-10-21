package builtin;

public class BuiltinASM {
    static String print = """
    # Begin builtin print
    mv      a1, a0
    lui	    a0, %hi(.L.str)
    addi    a0, a0, %lo(.L.str)
    call    printf
    # End builtin print
    """;

    static String println = """
    # Begin builtin println
    mv      a1, a0
    lui     a0, %hi(.L.str.1)
    addi    a0, a0, %lo(.L.str.1)
    call    printf
    # End builtin println
    """;

    static String printInt = """
    # Begin builtin printInt
    mv      a1, a0
    lui     a0, %hi(.L.str.2)
    addi    a0, a0, %lo(.L.str.2)
    call    printf
    # End builtin printInt
    """;

    static String printlnInt = """
    # Begin builtin printlnInt
    mv      a1, a0
    lui     a0, %hi(.L.str.3)
    addi    a0, a0, %lo(.L.str.3)
    call    printf
    # End builtin printlnInt
    """;

    static String getString = """
    # Begin builtin getString
    addi    sp, sp, -16
    sw      s0, 12(sp)
    li      a0, 1024
    call    malloc
    mv      s0, a0
    lui     a0, %hi(.L.str)
    addi    a0, a0, %lo(.L.str)
    mv      a1, s0
    call    scanf
    mv      a0, s0
    lw      s0, 12(sp)
    addi    sp, sp, 16
    # End builtin getString
    """;

    static String getInt = """
    # Begin builtin getInt
    addi    sp, sp, -16
    lui     a0, %hi(.L.str.2)
    addi    a0, a0, %lo(.L.str.2)
    addi    a1, sp, 8
    call    scanf
    lw      a0, 8(sp)
    addi    sp, sp, 16
    # End builtin getInt
    """;

    static String toString = """
    # Begin builtin toString
    addi    sp, sp, -16
    sw      a0, 8(sp)
    li      a0, 12
    call    malloc
    sw      a0, 4(sp)
    lui     a1, %hi(.L.str.2)
    addi    a1, a1, %lo(.L.str.2)
    lw      a2, 8(sp)
    call    sprintf
    lw      a0, 4(sp)
    addi    sp, sp, 16
    # End builtin toString
    """;

    static String __mx_allocate = """
    # Begin builtin __mx_allocate
    call    malloc
    # End builtin __mx_allocate
    """;

    static String __mx_allocate_array = """
    # Begin builtin __mx_allocate_array
    addi    sp, sp, -16
    sw      a0, 8(sp)
    slli    a0, a0, 2
    addi    a0, a0, 4
    call    malloc
    lw      a1, 8(sp)
    sw      a1, 0(a0)
    addi    a0, a0, 4
    """;

    // TODO: this can be easily replace by a simple lw instruction
    static String __mx_array_size = """
    # Begin builtin __mx_array_size
    lw      a0, -4(a0)
    # End builtin __mx_array_size        
    """;

    // TODO: this can be easily inline
    // only a0 is used
    static String __mx_bool_string = """
    # Begin builtin __mx_bool_string
    bnez    a0, .LB.__mx_bool_string.B1
    lui     a0, %hi(.L.str.5)
    addi    a0, a0, %lo(.L.str.5)
    j       .LB.__mx_bool_string.END
.LB.__mx_bool_string.B1
    lui     a0, %hi(.L.str.4)
    addi    a0, a0, %lo(.L.str.4)
.LB.__mx_bool_string.END
    # End builtin __mx_bool_string
    """;

    // TODO : this can be easily inline
    // a2,a1 use t1,t2
    static String string_length = """
    # Begin builtin string.length
    addi    a2, a0, 1
.LB.string.length.L1
    lbu     a1, 0(a0)
    addi    a0, a0, 1
    bnez    a1, .LB.string.length.L1
    sub     a0, a0, a2
    # End builtin string.length
    """;

    static String string_substring = """
    # Begin builtin string.substring

    # End builtin string.substring
    """;

    
}
