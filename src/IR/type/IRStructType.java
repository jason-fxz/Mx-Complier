package IR.type;

import java.util.ArrayList;


public class IRStructType extends IRType {
    private ArrayList<IRType> members;

    public IRStructType(String typeName, ArrayList<IRType> members) {
        super(typeName);
        this.members = members;
    }

    public IRStructType(String typeName) {
        super(typeName);
        this.members = new ArrayList<>();
    }

    public void AddMember(IRType member) {
        members.add(member);
    }

    @Override
    public String toString() {
        String str = "{ ";
        for (IRType member : members) {
            str += member.toString() + ", ";
        }
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        str += " }";
        return str;
    }

    
}
