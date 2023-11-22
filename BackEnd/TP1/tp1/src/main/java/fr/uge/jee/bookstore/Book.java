package fr.uge.jee.bookstore;

import java.util.StringJoiner;

public class Book {
    private final String title;
    private final long ref;

    public Book(String title,long ref){
        this.title = title;
        this.ref = ref;
    };

    @Override
    public String toString(){
        return "Titre : " + title + ", Ref : " + ref;
    }
}
