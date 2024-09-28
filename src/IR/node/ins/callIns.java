package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class callIns extends IRIns {
    public IRvar result = null;
    public String func;
    public ArrayList<IRitem> args;

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

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).equals(old)) {
                args.set(i, nw);
            }
        }
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        for (int i = 0; i < args.size(); i++) {
            if (map.containsKey(args.get(i))) {
                args.set(i, map.get(args.get(i)));
            }
        }
    }

    @Override
    public Set<IRvar> getUses() {
        Set<IRvar> res = new HashSet<>();
        for (var arg : args) {
            if (arg instanceof IRvar && ((IRvar) arg).isLocal()) {
                res.add((IRvar) arg);
            }
        }
        return res;
    }

    @Override
    public IRvar getDef() {
        return result;
    }
}
