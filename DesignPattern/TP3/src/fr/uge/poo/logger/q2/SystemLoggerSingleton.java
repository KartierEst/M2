package fr.uge.poo.logger.q2;

import fr.uge.poo.logger.q0.SystemLogger;

// on utilise un singleton
public class SystemLoggerSingleton {
    private static final SystemLogger INSTANCE = new SystemLogger();

    private SystemLoggerSingleton(){}

    public static SystemLogger getInstance(){
        return INSTANCE;
    }

}
