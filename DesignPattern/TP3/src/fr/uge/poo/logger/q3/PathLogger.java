package fr.uge.poo.logger.q3;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Predicate;

public class PathLogger implements Closeable, MyLogger {
    private final Path path;
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
    public boolean filter(Predicate<SystemLogger.Level> predicate, SystemLogger.Level level) {
        return false;
    }


    @Override
    public void close() throws IOException {
        bufferedWriter.close();
    }
}
