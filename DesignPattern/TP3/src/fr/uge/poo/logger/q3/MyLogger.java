package fr.uge.poo.logger.q3;

import java.util.function.Predicate;

public interface MyLogger {

    public enum Level {
        ERROR, WARNING, INFO
    }
    void log(Level level, String msg);
    default boolean filter(Predicate<Level> predicate, Level level){
        return predicate.test(level);
    }
}
