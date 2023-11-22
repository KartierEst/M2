package fr.uge.poo.logger.q3;

import fr.uge.poo.logger.q0.SystemLogger;
import fr.uge.poo.logger.q1.PathLogger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("test.txt");
        var logger = new PathLogger(path);
        logger.log(SystemLogger.Level.ERROR, "test");
        logger.close();
        logger.log(SystemLogger.Level.ERROR, "test");
    }
}
