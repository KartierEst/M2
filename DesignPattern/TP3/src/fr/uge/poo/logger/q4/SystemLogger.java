package fr.uge.poo.logger.q4;

public class SystemLogger implements MyLogger {

    public enum Level {
        ERROR, WARNING, INFO
    }

    public void log(Level level, String message) {
        System.err.println(level + " " + message);
    }
}