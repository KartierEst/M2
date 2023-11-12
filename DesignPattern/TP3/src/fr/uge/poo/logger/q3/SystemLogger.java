package fr.uge.poo.logger.q3;

import java.util.List;
import java.util.function.Predicate;

public class SystemLogger implements MyLogger {

    public enum Level {
        ERROR, WARNING, INFO
    }

    public void log(Level level, String message) {
        System.err.println(level + " " + message);
    }
}