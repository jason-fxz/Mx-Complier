package IR.node.ins;

import IR.item.IRitem;
import IR.item.IRvar;
import java.util.ArrayList;

public class callIns extends IRIns {
    IRvar result = null;
    String func;
    ArrayList<IRitem> args;

    public callIns(IRvar result, String func, IRitem ... args) {
        this.result = result;
        this.func = func;
        this.args = new ArrayList<>();
        for (var arg : args) {
            this.args.add(arg);
        }
    }

    public callIns(IRvar result, String func, ArrayList<IRitem> args) {
        this.result = result;
        this.func = func;
        this.args = args;
    }

    public callIns(String func, IRitem ... args) {
        this.func = func;
        this.args = new ArrayList<>();
        for (var arg : args) {
            this.args.add(arg);
        }
    }

    public callIns(String func, ArrayList<IRitem> args) {
        this.func = func;
        this.args = args;
    }

    @Override
    public String toString() {
        String str;
        if (result == null) {
            str = "call void @" + func + "(";
        } else {
            str = result.toString() + " = call " + result.type.toString() + " @" + func + "(";
        }

        for (var arg : args) {
            str += arg.type.toString() + " " + arg.toString() + ", ";
        }
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        str += ")";
        return str;

    }

}
