package fr.uge.poo.logger.q4;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class LoggerProxy implements MyLogger {
    private final PathLogger pathLogger;
    public LoggerProxy(PathLogger pathLogger) {
        this.pathLogger =  pathLogger;
    }

    @Override
    public void log(Level level, String msg) {
        if(filter(level1 -> level1.ordinal() <= Level.INFO.ordinal(), level)) {
            pathLogger.log(level, msg);
        }
        SystemLoggerSingleton.getInstance().log(level, msg);
    }

    public static void main(String[] args) {
        Path path = Paths.get("test.txt");
        var logger = new PathLogger(path);
        var proxy = new LoggerProxy(logger);
        proxy.log(Level.INFO, "test");
        proxy.log(Level.ERROR, "test");
    }
}
