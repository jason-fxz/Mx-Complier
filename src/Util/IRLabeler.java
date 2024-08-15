package Util;

import java.util.HashMap;

public class IRLabeler {

    static HashMap<String, Integer> IdMap = new HashMap<>();
    
    public static String getIdLabel(String name) {
        if (IdMap.containsKey(name)) {
            int cnt = IdMap.get(name);
            IdMap.put(name, cnt + 1);
            return name + "." + cnt;
        } else {
            IdMap.put(name, 1);
            return name;
        }
    }
}
