package fr.uge.poo.composite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// on utilise composite
public sealed interface FileSystemEntry permits FileSystemEntry.Directory, FileSystemEntry.File {

    <C> Boolean accept(Visitor<C> visitor, C context);

    default Boolean test(String extension){
        return switch (this){
            case File file -> file.extension().equals(extension);
            case Directory directory -> false;
        };
    }
    record File(Path path, String name, String extension) implements FileSystemEntry{
        @Override
        public <C> Boolean accept(Visitor<C> visitor, C context) {
            return visitor.visit(this,context);
        }

        static File of(Path path){
            if(!Files.isRegularFile(path)){
                throw new IllegalArgumentException("it's not a file");
            }
            var name = path.getFileName().toString().split("\\.");
            return new File(path, name[0], name[1]);
        }

        @Override
        public String toString(){
            return name();
        }
    };
    record Directory(Path path, String Name, List<FileSystemEntry> files) implements FileSystemEntry{
        @Override
        public <C> Boolean accept(Visitor<C> visitor, C context) {
            return visitor.visit(this,context);
        }
        static Directory of(Path path){
            if(!Files.isDirectory(path)){
                throw new IllegalArgumentException("it's not a file");
            }
            try(var f = Files.list(path)) {
                return new Directory(path, path.getFileName().toString(),
                        f.map(FileSystemEntry::of).toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };

    record LazyFile(Path path) {
        static LazyFile of(Path path){
            return new LazyFile(path);
        }

        public FileSystemEntry resolve(){
            return FileSystemEntry.of(path);
        }
    }

    static FileSystemEntry of(Path path) {
        if(Files.isDirectory(path)){
            return Directory.of(path);
        }
        return File.of(path);
    }

    static FileSystemEntry ofLazy(Path path) {
        return LazyFile.of(path).resolve();
    }
}
