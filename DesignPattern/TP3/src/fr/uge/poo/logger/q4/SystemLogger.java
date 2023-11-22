package fr.uge.poo.logger.q4;

public class SystemLogger implements MyLogger {
    public void log(Level level, String message) {
        System.err.println(level + " " + message);
    }
}