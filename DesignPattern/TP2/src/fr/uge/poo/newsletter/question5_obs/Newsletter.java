package fr.uge.poo.newsletter.question5_obs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Newsletter {
    private final String name;
    private final List<User> users = new ArrayList<>();
    private Predicate<User> predicate = null;
    private List<User.Nationality> nationalities = new ArrayList<>();
    private int limitAge = 0;
    private EMail email;

    // private final List<Observer> observers = new ArrayList<>();

    private Observer mailObserver;
    
    public Newsletter(String name){
        Objects.requireNonNull(name);
        this.name = name;
    }

    public Newsletter(String name, Predicate<User> predicate){
        this(name);
        Objects.requireNonNull(predicate);
        this.predicate = predicate;
    }

    public Newsletter(String name, List<User.Nationality> nationalities, int limitAge){
        this(name);
        Objects.requireNonNull(nationalities);
        if(limitAge < 12){
            throw new IllegalStateException("nobody have a mail at 12 and lower");
        }
        this.nationalities = nationalities;
        this.limitAge = limitAge;
    }

    public Newsletter(String name, List<User.Nationality> nationalities, int limitAge, EMail email){
        this(name,nationalities,limitAge);
        Objects.requireNonNull(email);
        this.email = email;
    }

    public Newsletter(String name, Predicate<User> predicate , EMail email){
        this(name,predicate);
        Objects.requireNonNull(email);
        this.email = email;
    }

    private NewsletterInfo infos() {
        return new NewsletterInfo(name);
    }

    boolean constraintsTest(User user){
        if(users.stream().map(User::email).toList().contains(user.email())){
            System.err.println("the email already exist");
            mailObserver.sendErrorSubscribe(infos(), user.name());
            return false;
        }
        if(predicate == null){
            if(!nationalities.contains(user.nationality()) || user.age() <= limitAge){
                System.err.println("you don't valid the nationality or/ang the age constraints " + user.name() );
                mailObserver.sendErrorSubscribe(infos(), user.name());
                return false;
            }
        }
        else {
            if (!predicate.test(user)) {
                System.err.println("you don't valid the constraints " + user.name());
                mailObserver.sendErrorSubscribe(infos(), user.name());
                return false;
            }
        }
        return true;
    }

    void subscribe(User user){
        Objects.requireNonNull(user);
        mailObserver = new MailObserver(email);
        if(constraintsTest(user)) {
            if (user.email().contains("etud.univ-eiffel.fr")) {
                mailObserver.sendToUniv(infos());
            }
            mailObserver.sendWelcomeMail(infos(), user.email());
            users.add(user);
            if (users.size() == 100) {
                mailObserver.sendMoreThan100(infos(), users.stream().map(User::email).collect(Collectors.joining(", ")));
            }
        }
    }

    void unsubscribe(fr.uge.poo.newsletter.question5.User user){
        Objects.requireNonNull(user);
        mailObserver = null;
        users.remove(user);
    }

    void sendMessage(String title, String content){
        for(var user : users){
            email.send(user.email(), title,content);
        }
    }

    void sendMessageBulk(String title, String content){
        email.sendBulk(users.stream().map(User::email).toList(), title, content);
    }
}
