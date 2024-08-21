package IR.node.def;

import IR.IRvisitor;

public class IRStrDef extends IRDefNode {
    String name;
    String value;

    public IRStrDef(String name, String str) {
        this.name = name;
        this.value = str;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int len = 0;
        for (int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (i + 1 < value.length() && c == '\\') {
                char next = value.charAt(i + 1);
                if (next == 'n') {
                    sb.append("\\0A");
                    ++i;
                } else if (next == '\"') {
                    sb.append("\\22");
                    ++i;
                } else {
                    sb.append("\\\\");
                    ++i;
                }
            } else {
                sb.append(c);
            }
            ++len;
        }
        ++len;
        return name + " = private unnamed_addr constant [" + (len) + " x i8] c\"" + sb.toString() + "\\00\"";
    }


    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

}
