package IR;

import IR.item.IRitem;
import IR.item.IRvar;

public class IRhelper {
    public IRitem exprVar = null;
    public IRvar exprAddr = null;
    public String funName = null;
    public IRvar  funthis = null;
    
    public IRhelper(IRitem exprVar) {
        this.exprVar = exprVar;
    }
    public IRhelper(IRitem exprVar, IRvar exprAddr) {
        this.exprVar = exprVar;
        this.exprAddr = exprAddr;
    }
    public IRhelper(String funcName) {
        this.funName = funcName;
    }

    public IRhelper() {
    }
}
