package ASM.item;

public class ASMReg {
    private int index;
    private String name;

    public ASMReg(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    static public ASMReg zero = new ASMReg(0, "zero"); // Hard-wired zero
    static public ASMReg ra   = new ASMReg(1, "ra");   // Return address
    static public ASMReg sp   = new ASMReg(2, "sp");   // Stack pointer
    static public ASMReg gp   = new ASMReg(3, "gp");   // Global pointer
    static public ASMReg tp   = new ASMReg(4, "tp");   // Thread pointer
    static public ASMReg t0   = new ASMReg(5, "t0");   // Temporary/alternate link register
    static public ASMReg t1   = new ASMReg(6, "t1");   // Temporaries
    static public ASMReg t2   = new ASMReg(7, "t2");   // Temporaries
    static public ASMReg s0   = new ASMReg(8, "s0");   // Saved register/frame pointer
    static public ASMReg s1   = new ASMReg(9, "s1");   // Saved register
    static public ASMReg a0   = new ASMReg(10, "a0");  // Function arguments/return values
    static public ASMReg a1   = new ASMReg(11, "a1");  // Function arguments/return values
    static public ASMReg a2   = new ASMReg(12, "a2");  // Function arguments
    static public ASMReg a3   = new ASMReg(13, "a3");  // Function arguments
    static public ASMReg a4   = new ASMReg(14, "a4");  // Function arguments
    static public ASMReg a5   = new ASMReg(15, "a5");  // Function arguments
    static public ASMReg a6   = new ASMReg(16, "a6");  // Function arguments
    static public ASMReg a7   = new ASMReg(17, "a7");  // Function arguments
    static public ASMReg s2   = new ASMReg(18, "s2");  // Saved registers
    static public ASMReg s3   = new ASMReg(19, "s3");  // Saved registers
    static public ASMReg s4   = new ASMReg(20, "s4");  // Saved registers
    static public ASMReg s5   = new ASMReg(21, "s5");  // Saved registers
    static public ASMReg s6   = new ASMReg(22, "s6");  // Saved registers
    static public ASMReg s7   = new ASMReg(23, "s7");  // Saved registers
    static public ASMReg s8   = new ASMReg(24, "s8");  // Saved registers
    static public ASMReg s9   = new ASMReg(25, "s9");  // Saved registers
    static public ASMReg s10  = new ASMReg(26, "s10"); // Saved registers
    static public ASMReg s11  = new ASMReg(27, "s11"); // Saved registers
    static public ASMReg t3   = new ASMReg(28, "t3");  // Temporaries
    static public ASMReg t4   = new ASMReg(29, "t4");  // Temporaries
    static public ASMReg t5   = new ASMReg(30, "t5");  // Temporaries
    static public ASMReg t6   = new ASMReg(31, "t6");  // Temporaries

    static public ASMReg x(int id) {
        switch (id) {
            case 0: return zero;
            case 1: return ra;
            case 2: return sp;
            case 3: return gp;
            case 4: return tp;
            case 5: return t0;
            case 6: return t1;
            case 7: return t2;
            case 8: return s0;
            case 9: return s1;
            case 10: return a0;
            case 11: return a1;
            case 12: return a2;
            case 13: return a3;
            case 14: return a4;
            case 15: return a5;
            case 16: return a6;
            case 17: return a7;
            case 18: return s2;
            case 19: return s3;
            case 20: return s4;
            case 21: return s5;
            case 22: return s6;
            case 23: return s7;
            case 24: return s8;
            case 25: return s9;
            case 26: return s10;
            case 27: return s11;
            case 28: return t3;
            case 29: return t4;
            case 30: return t5;
            case 31: return t6;
            default: return null;
        }
    }
    /*
     * x0 zero Hard-wired zero —
     * x1 ra Return address Caller
     * x2 sp Stack pointer Callee
     * x3 gp Global pointer —
     * x4 tp Thread pointer —
     * x5 t0 Temporary/alternate link register Caller
     * x6–7 t1–2 Temporaries Caller
     * x8 s0/fp Saved register/frame pointer Callee
     * x9 s1 Saved register Callee
     * x10–11 a0–1 Function arguments/return values Caller
     * x12–17 a2–7 Function arguments Caller
     * x18–27 s2–11 Saved registers Callee
     * x28–31 t3–6 Temporaries Caller
     */


}
