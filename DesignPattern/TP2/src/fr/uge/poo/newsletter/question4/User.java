package fr.uge.poo.newsletter.question4;

import java.util.Objects;

public record User(String name, String email, int age, Nationality nationality) {
    public enum Nationality {
        FRENCH,BRITISH,SPANISH
    }

    public User {
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        if (age<0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        Objects.requireNonNull(nationality);
    }
}
