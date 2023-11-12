package fr.uge.poo.logger.q0;

public class SystemLogger {
    public enum Level {
        ERROR, WARNING, INFO
    }

    public void log(Level level, String message) {
        System.err.println(level + " " + message);
    }
}