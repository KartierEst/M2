package fr.uge.poo.logger.q4;


import java.nio.file.Paths;
import java.util.Objects;

public class LoggerProxy implements MyLogger {
    private final PathLogger pathLogger;
    public LoggerProxy(String file) {
        this.pathLogger =  new PathLogger(Paths.get(Objects.requireNonNull(file)));
    }

    public LoggerProxy(PathLogger pathLogger) {
        this.pathLogger =  pathLogger;
    }

    @Override
    public void log(SystemLogger.Level level, String msg) {
        if(filter(level1 -> level1 != SystemLogger.Level.INFO, level)) {
            pathLogger.log(level, msg);
        }
        SystemLoggerSingleton.getInstance().log(level, msg);
    }

    public static void main(String[] args) {
        var proxy = new LoggerProxy("test.txt");
        proxy.log(SystemLogger.Level.INFO, "test");
    }
}
