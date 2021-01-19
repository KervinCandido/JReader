package com.github.kervincandido.model;

public enum OS {
    LINUX,
    MAC,
    WINDOWS;

    public static OS getInstance() {
        final String os = System.getProperty("os.name");

        if (os.contains("Mac"))
            return OS.MAC;
        else if (os.contains("Linux"))
            return OS.LINUX;
        else
            return OS.WINDOWS;
    }
}
