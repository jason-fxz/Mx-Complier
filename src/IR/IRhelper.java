package IR;

import IR.item.IRitem;
import IR.item.IRvar;

public class IRhelper {
    public IRitem exprVar;
    public IRvar exprAddr;
    public IRhelper(IRitem exprVar) {
        this.exprVar = exprVar;
        this.exprAddr = null;
    }
    public IRhelper(IRitem exprVar, IRvar exprAddr) {
        this.exprVar = exprVar;
        this.exprAddr = exprAddr;
    }
    public IRhelper() {
        this.exprVar = null;
        this.exprAddr = null;
    }
}
