package fr.uge.poo.composite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Application {
    public static List<String> findFilesWithExtension(Path directory, String txt) throws IOException {
        var visitor = new Visitor.TreatmentVisitor();
        if(!Files.isDirectory(directory)){
            throw new IllegalArgumentException("it's not a directory");
        }
        var stream = FileSystemEntry.Directory.of(directory);
        return stream.files().stream().filter(x -> x.accept(visitor, txt)).map(Object::toString).toList();
        // return stream.files().stream().filter(x -> x.test(txt)).map(Object::toString).toList();
    }

    public static void main(String[] args) throws IOException {
        //System.out.println(path.getFileName().toString());
        System.out.println(findFilesWithExtension(Paths.get("src/fr/uge/poo/composite"), "java"));
    }
}
