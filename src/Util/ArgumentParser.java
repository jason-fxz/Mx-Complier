package Util;

import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {
    private final Map<String, String> arguments = new HashMap<>();

    public ArgumentParser(String[] args) {
        parse(args);
    }

    private void parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    arguments.put(args[i], args[i + 1]);
                    i++;
                } else {
                    arguments.put(args[i], null);
                }
            }
        }
    }

    public String getArgument(String key) {
        return arguments.get(key);
    }

    public boolean hasArgument(String key) {
        return arguments.containsKey(key);
    }
}