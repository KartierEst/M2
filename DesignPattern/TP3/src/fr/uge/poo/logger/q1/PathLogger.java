package fr.uge.poo.logger.q1;

import fr.uge.poo.logger.q0.SystemLogger;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class PathLogger implements Closeable {
    private final Path path;
    private final SystemLogger systemLogger = new SystemLogger();
    BufferedWriter bufferedWriter = null;
    public PathLogger(Path path) {
        this.path = path;
    }

    public void log(SystemLogger.Level level, String msg) {
        try {
            if(bufferedWriter == null){
                bufferedWriter = Files.newBufferedWriter(path,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }

            bufferedWriter.write(level + " : " + msg + "\n");
            //bufferedWriter.flush();

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    @Override
    public void close() throws IOException {
        bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("test.txt");
        var logger = new PathLogger(path);
        logger.log(SystemLogger.Level.ERROR, "test");
        logger.close();
    }
}
