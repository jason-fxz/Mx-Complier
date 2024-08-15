package IR;

import IR.item.IRvar;

public class IRhelper {
    public IRvar exprVar;
    public IRhelper(IRvar exprVar) {
        this.exprVar = exprVar;
    }
    public IRhelper() {
        this.exprVar = null;
    }
}
