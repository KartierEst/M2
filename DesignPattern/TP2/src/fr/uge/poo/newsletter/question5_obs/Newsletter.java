package fr.uge.poo.newsletter.question5_obs;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Newsletter {
    private final String name;
    private final List<User> users;
    private final Predicate<User> predicate;
    private final List<User.Nationality> nationalities;
    private final EMail email;
    private final List<Observer> observers = new ArrayList<>();

    // impossible de tout codé la dedans ca ne respecte pas le principe SOLID :
    // Open closed responsability principle
    // on ne veut pas modifier la classe mais augmenter le code

    //private Observer mailObserver;

    public Newsletter(NewsletterBuilder builder){
        this.name = builder.name;
        this.email = builder.email;
        this.predicate = builder.predicate;
        this.nationalities = builder.nationalities;
        this.users = builder.users;
    }

    Observer WELCOME_MAIL = new Observer() {
        @Override
        public void subscribe(User user) {
            if(constraintsTest(user) && !users.stream().map(User::email).toList().contains(user.email())){
                email.send(user.email(), "Welcome", "Welcome to " + name);
            }
        }
    };

    Observer MOST_THAN_100_USERS = new Observer() {
        @Override
        public void subscribe(User user) {
            var content = users.stream().map(User::email).collect(Collectors.joining(", "));
            if(users.size() == 100) {
                email.send("sales@goodcorp.com", "[" + name + "] ", "You have exceeded 100 users : " + content);
            }
        }
    };

    Observer ERROR_SUBSCRIBE = new Observer() {
        @Override
        public void subscribe(User user) {
            if(!constraintsTest(user)) {
                email.send("support@goodcorp.com", "[" + user.name() + "] ", user.name() + " " + name);
            }
        }
    };

    Observer STUDENT_MAIL = new Observer() {
        @Override
        public void sendMessage(User user) {
            if(user.email().contains("etud.univ-eiffel.fr")){
                email.send("spy@nsa.org", "[" + user.email() + "] ", name);
            }
        }
    };


    public static NewsletterBuilder with(){
        return new NewsletterBuilder();
    }

    public static class NewsletterBuilder {
        private String name;
        private List<User> users = new ArrayList<>();
        private Predicate<User> predicate;
        private List<User.Nationality> nationalities = new ArrayList<>();
        private EMail email = new EemailAdapted();

        public NewsletterBuilder name(String name){
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public NewsletterBuilder user(User user){
            users.add(Objects.requireNonNull(user));
            return this;
        }

        public NewsletterBuilder users(List<User> users){
            this.users = Objects.requireNonNull(users);
            return this;
        }

        public NewsletterBuilder nationality(User.Nationality nationality){
            nationalities.add(Objects.requireNonNull(nationality));
            return this;
        }

        public NewsletterBuilder nationalities(List<User.Nationality> nationalities){
            this.nationalities = Objects.requireNonNull(nationalities);
            return this;
        }

        public NewsletterBuilder predicate(Predicate<User> predicate){
            this.predicate = Objects.requireNonNull(predicate);
            return this;
        }

        public NewsletterBuilder age(int age){
            if(age < 0){
                throw new IllegalArgumentException("age is negative");
            }
            this.predicate = this.predicate == null ? (user -> user.age() >= age) : predicate.and(user -> user.age() >= age);
            return this;
        }

        public NewsletterBuilder mail(EMail email){
            this.email = Objects.requireNonNull(email);
            return this;
        }

        public Newsletter build(){
            if(name == null){
                throw new IllegalArgumentException("you don't have name");
            }
            return new Newsletter(this);
        }
    }
    public void register(Observer obs){
        observers.add(Objects.requireNonNull(obs));
    }

    public void unregister(Observer obs){
        if(!observers.remove(Objects.requireNonNull(obs))){
            throw new IllegalStateException("problem to remove observer");
        }
    }

    boolean constraintsTest(User user){
        if(!nationalities.isEmpty()){
            return nationalities.contains(user.nationality()) && predicate.test(user);
        }
        else {
            return predicate.test(user);
        }
    }

    void subscribe(User user){
        Objects.requireNonNull(user);
        if(constraintsTest(user) && !users.stream().map(User::email).toList().contains(user.email())) {
            users.add(user);
            for(var obs : observers){
                obs.subscribe(user);
            }
        } else if (users.stream().map(User::email).toList().contains(user.email())) {
            System.err.println("the email already exist " + user.email());
        } else {
            System.err.println("you don't valid the nationality or/and the age constraints " + user.email() + " to " + name);
        }
    }

    void unsubscribe(User user){
        Objects.requireNonNull(user);
        users.remove(user);
        for(var obs : observers){
            obs.unsubscribe(user);
        }
    }

    void sendMessage(String title, String content){
        for(var user : users){
            email.send(user.email(), title,content);
            for(var obs : observers){
                obs.sendMessage(user);
            }
        }
    }

    void sendMessageBulk(String title, String content){
        email.sendBulk(users.stream().map(User::email).toList(), title, content);
    }
}
