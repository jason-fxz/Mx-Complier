package IR.node.def;

public class IRStrDef {
    String name;
    String value;

    public IRStrDef(String name, String str) {
        this.name = name;
        this.value = str;

        // // replace escape characters
        // this.value = this.value.replace("\n", "\\0A");
        // this.value = this.value.replace("\"", "\\22");
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
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
            ++len;
        }
        ++len;
        return name + " = private unnamed_addr constant [" + (len) + " x i8] c\"" + sb.toString() + "\\00\"";
    }

}
