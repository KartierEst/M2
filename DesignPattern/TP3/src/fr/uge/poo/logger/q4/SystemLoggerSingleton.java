package fr.uge.poo.logger.q4;

// on utilise un singleton
public class SystemLoggerSingleton {
    private static final SystemLogger INSTANCE = new SystemLogger();

    private SystemLoggerSingleton(){}

    public static SystemLogger getInstance(){
        return INSTANCE;
    }

}
