package Util;

import java.util.HashMap;

public class ExecutionTimer {
    public static ExecutionTimer timer = new ExecutionTimer();

    private long totalstartTime;
    
    private HashMap<String, Double> timeLog = new HashMap<>();
    private HashMap<String, Long> startTime = new HashMap<>();

    public ExecutionTimer() {
        totalstartTime = System.nanoTime();
    }
    public void start(String taskName) {
        startTime.put(taskName, System.nanoTime());
    }

    public void stop(String taskName) {
        var endTime = System.nanoTime();
        var elapsedTime = endTime - startTime.get(taskName);
        timeLog.put(taskName, timeLog.getOrDefault(taskName, 0.0) + elapsedTime / 1_000_000.0);
    }

    public void printTimeLog() {
        System.err.println("============= Time Log =============");
        
        System.err.println(String.format("%-20s : %.3f ms", "Total", (System.nanoTime() - totalstartTime) / 1_000_000.0));

        timeLog.entrySet().stream()
            .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
            .forEach(entry -> {
                String taskName = entry.getKey();
                Double time = entry.getValue();
                System.err.println(String.format("%-20s : %.3f ms", taskName, time));
            });
        
    }
}