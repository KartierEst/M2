package fr.uge.poo.composite;

import java.nio.file.Path;

public interface Visitor<C> {
    Boolean visit(FileSystemEntry.File file, C context);

    Boolean visit(FileSystemEntry.Directory directory, C context);

    class TreatmentVisitor implements Visitor<String>{
        public Boolean visit(FileSystemEntry.File file, String extension){
            return file.extension().equals(extension);
        }
        public Boolean visit(FileSystemEntry.Directory directory, String extension){
            return false;
        }
    }
}
