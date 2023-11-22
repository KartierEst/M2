package fr.uge.jee.bookstore;

import fr.uge.jee.bookstore.Book;

import java.util.Set;
import java.util.stream.Collectors;

public class Library {
    // autowire prend tout les book qu'il voit pour faire le set
    private Set<Book> books;

    public Library(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString(){
        return books.stream().map(Book::toString).collect(Collectors.joining("\n"));
    }
}
