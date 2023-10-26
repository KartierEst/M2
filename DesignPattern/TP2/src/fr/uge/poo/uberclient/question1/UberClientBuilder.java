package fr.uge.poo.uberclient.question1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class UberClientBuilder {
    private String firstName;
    private String lastName;
    private long uid;
    private List<Integer> grades = new ArrayList<>();
    private List<String> emails = new ArrayList<>();
    private List<String> phoneNumbers = new ArrayList<>();

    public UberClientBuilder firstName(String firstName){
        this.firstName = Objects.requireNonNull(firstName);
        return this;
    }

    public UberClientBuilder lastname(String lastName) {
        this.lastName = Objects.requireNonNull(lastName);
        return this;
    }

    public UberClientBuilder uid(long uid){
        if (uid<0) {
            throw new IllegalArgumentException("UID must be positive");
        }
        this.uid = uid;
        return this;
    }

    public UberClientBuilder randomUid(){
        this.uid = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
        return this;
    }

    public UberClientBuilder grade(int grade){
        if (grade < 1 || grade > 5) {
            throw new IllegalArgumentException("All grades must be between 1 and 5");
        }
        grades.add(grade);
        return this;
    }

    public UberClientBuilder grades(List<Integer> grades){
        Objects.requireNonNull(grades);
        for (var gr : grades) {
            this.grades.add(Objects.requireNonNull(gr));
        }
        return this;
    }

    public UberClientBuilder email(String email){
        emails.add(Objects.requireNonNull(email));
        return this;
    }

    public UberClientBuilder phoneNumber(String phoneNumer){
        phoneNumbers.add(Objects.requireNonNull(phoneNumer));
        return this;
    }

    public UberClient build(){
        if(firstName == null || lastName == null || uid < 0){
            throw new IllegalStateException("firstname or lastname or uid are mandatory");
        }
        if (grades.size()==0){
            throw new IllegalArgumentException("A client must have at least one grade");
        }
        if (emails.size()==0 && phoneNumbers.size()==0) {
            throw new IllegalArgumentException("A client must have at least an email or a phoneNumber");
        }
        return new UberClient(firstName,lastName,grades,emails,phoneNumbers);
    }
}
