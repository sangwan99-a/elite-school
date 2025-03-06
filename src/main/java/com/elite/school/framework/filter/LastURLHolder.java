package com.elite.school.framework.filter;


public class LastURLHolder {

    private LastURLHolder() {
        throw new IllegalStateException("Utility class - instantiation not allowed");
    }

    private static final ThreadLocal<String> lastURL = ThreadLocal.withInitial(() -> null);

    public static void setLastURL(String url) {
        lastURL.set(url);
    }

    public static String getLastURL() {
        return lastURL.get();
    }

    public static void clearLastURL() {
        lastURL.remove();
    }
}

