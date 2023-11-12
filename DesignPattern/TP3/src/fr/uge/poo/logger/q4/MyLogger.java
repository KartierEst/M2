package fr.uge.poo.logger.q4;

import java.util.function.Predicate;

public interface MyLogger {
    void log(SystemLogger.Level level, String msg);
    default boolean filter(Predicate<SystemLogger.Level> predicate, SystemLogger.Level level){
        return predicate.test(level);
    }
}
