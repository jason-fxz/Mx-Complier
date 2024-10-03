package ASM.item;

public class ASMReg extends ASMItem {
    private int index;
    private String name;
    private String Saver;

    private ASMReg(int index, String name, String Saver) {
        super(true);
        this.index = index;
        this.name = name;
        this.Saver = Saver;
        if (!Saver.equals("Caller") && !Saver.equals("Callee") && !Saver.equals("None")) {
            throw new RuntimeException("Invalid Saver");
        }
    }

    public boolean isCallerSave() {
        return Saver.equals("Caller");
    }

    public boolean isCalleeSave() {
        return Saver.equals("Callee");
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ASMReg) {
            ASMReg reg = (ASMReg) obj;
            return index == reg.index;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return index;
    }

    static public int MAX_REG = 32;

    static public ASMReg zero = new ASMReg(0, "zero", "None"); // Hard-wired zero
    static public ASMReg ra   = new ASMReg(1, "ra", "Caller");   // Return address
    static public ASMReg sp   = new ASMReg(2, "sp", "Callee");   // Stack pointer
    static public ASMReg gp   = new ASMReg(3, "gp", "None");   // Global pointer
    static public ASMReg tp   = new ASMReg(4, "tp", "None");   // Thread pointer
    static public ASMReg t0   = new ASMReg(5, "t0", "Caller");   // Temporary/alternate link register
    static public ASMReg t1   = new ASMReg(6, "t1", "Caller");   // Temporaries
    static public ASMReg t2   = new ASMReg(7, "t2", "Caller");   // Temporaries
    static public ASMReg s0   = new ASMReg(8, "s0", "Callee");   // Saved register/frame pointer
    static public ASMReg s1   = new ASMReg(9, "s1", "Callee");   // Saved register
    static public ASMReg a0   = new ASMReg(10, "a0", "Caller");  // Function arguments/return values
    static public ASMReg a1   = new ASMReg(11, "a1", "Caller");  // Function arguments/return values
    static public ASMReg a2   = new ASMReg(12, "a2", "Caller");  // Function arguments
    static public ASMReg a3   = new ASMReg(13, "a3", "Caller");  // Function arguments
    static public ASMReg a4   = new ASMReg(14, "a4", "Caller");  // Function arguments
    static public ASMReg a5   = new ASMReg(15, "a5", "Caller");  // Function arguments
    static public ASMReg a6   = new ASMReg(16, "a6", "Caller");  // Function arguments
    static public ASMReg a7   = new ASMReg(17, "a7", "Caller");  // Function arguments
    static public ASMReg s2   = new ASMReg(18, "s2", "Callee");  // Saved registers
    static public ASMReg s3   = new ASMReg(19, "s3", "Callee");  // Saved registers
    static public ASMReg s4   = new ASMReg(20, "s4", "Callee");  // Saved registers
    static public ASMReg s5   = new ASMReg(21, "s5", "Callee");  // Saved registers
    static public ASMReg s6   = new ASMReg(22, "s6", "Callee");  // Saved registers
    static public ASMReg s7   = new ASMReg(23, "s7", "Callee");  // Saved registers
    static public ASMReg s8   = new ASMReg(24, "s8", "Callee");  // Saved registers
    static public ASMReg s9   = new ASMReg(25, "s9", "Callee");  // Saved registers
    static public ASMReg s10  = new ASMReg(26, "s10", "Callee"); // Saved registers
    static public ASMReg s11  = new ASMReg(27, "s11", "Callee"); // Saved registers
    static public ASMReg t3   = new ASMReg(28, "t3", "Caller");  // Temporaries
    static public ASMReg t4   = new ASMReg(29, "t4", "Caller");  // Temporaries
    static public ASMReg t5   = new ASMReg(30, "t5", "Caller");  // Temporaries
    static public ASMReg t6   = new ASMReg(31, "t6", "Caller");  // Temporaries

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

    static public ASMReg a(int id) {
        switch (id) {
            case 0: return a0;
            case 1: return a1;
            case 2: return a2;
            case 3: return a3;
            case 4: return a4;
            case 5: return a5;
            case 6: return a6;
            case 7: return a7;
            default: return null;
        }
    }

    static public ASMReg s(int id) {
        switch (id) {
            case 0: return s0;
            case 1: return s1;
            case 2: return s2;
            case 3: return s3;
            case 4: return s4;
            case 5: return s5;
            case 6: return s6;
            case 7: return s7;
            case 8: return s8;
            case 9: return s9;
            case 10: return s10;
            case 11: return s11;
            default: return null;
        }
    }

    static public ASMReg t(int id) {
        switch (id) {
            case 0: return t0;
            case 1: return t1;
            case 2: return t2;
            case 3: return t3;
            case 4: return t4;
            case 5: return t5;
            case 6: return t6;
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
