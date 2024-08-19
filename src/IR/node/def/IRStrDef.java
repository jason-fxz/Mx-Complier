package IR.node.def;

public class IRStrDef {
    String name;
    String value;

    public IRStrDef(String name, String str) {
        this.name = name;
        this.value = str;

        // replace escape characters
        this.value = this.value.replace("\n", "\\0A");
        this.value = this.value.replace("\"", "\\22");
    }


    @Override
    public String toString() {
        return name + " = private unnamed_addr constant [" + (value.length() + 1) + " x i8] c\"" + value + "\\00\"";
    }

}
