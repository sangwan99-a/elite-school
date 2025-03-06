package com.elite.school.framework.filter;



import java.util.ArrayList;
import java.util.List;

public class RequestMethodTimeHolder {

    private static final ThreadLocal<List<String>> requestMethodTimes = ThreadLocal.withInitial(ArrayList::new);

    public static void addRequestMethodLog(String log) {
        requestMethodTimes.get().add(log);
    }

    public static List<String> getRequestMethodTimes() {
        return new ArrayList<>(requestMethodTimes.get());
    }

    public static void clearRequestMethodTimes() {
        requestMethodTimes.remove();  // Ensures proper cleanup
    }
}

