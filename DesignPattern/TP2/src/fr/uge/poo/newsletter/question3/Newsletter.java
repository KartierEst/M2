package fr.uge.poo.newsletter.question3;

import com.evilcorp.eemailer.EEMailer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Newsletter {
    private final String name;
    private List<User> users = new ArrayList<>();
    private Predicate<User> predicate = null;
    private List<User.Nationality> nationalities = new ArrayList<>();
    private int limitAge = 0;
    
    public Newsletter(String name){
        Objects.requireNonNull(name);
        this.name = name;
    }

    public Newsletter(String name, Predicate<User> predicate){
        Objects.requireNonNull(name);
        Objects.requireNonNull(predicate);
        this.name = name;
        this.predicate = predicate;
    }

    public Newsletter(String name, List<User.Nationality> nationalities, int limitAge){
        Objects.requireNonNull(name);
        Objects.requireNonNull(nationalities);
        if(limitAge < 12){
            throw new IllegalStateException("nobody have a mail at 12 and lower");
        }
        this.name = name;
        this.nationalities = nationalities;
        this.limitAge = limitAge;
    }

    void subscribe(User user){
        Objects.requireNonNull(user);
        if(users.stream().map(x -> x.email()).toList().contains(user.email())){
            System.err.println("the email already exist");
            return;
        }
        if(predicate == null){
            if(!nationalities.contains(user.nationality()) || user.age() <= limitAge){
                System.err.println("you don't valid the nationality or/ang the age constraints " + user.name() );
                return;
            }
        }
        else {
            if (!predicate.test(user)) {
                System.err.println("you don't valid the constraints " + user.name());
                return;
            }
        }
        users.add(user);
    }

    void unsubscribe(User user){
        Objects.requireNonNull(user);
        users.remove(user);
    }

    void sendMessage(String title, String content){
        var mailer = new EEMailer();
        for(var user : users){
            System.out.println(user.email());
            var mail = new EEMailer.Mail(user.email(),"[" + name + "] " + title,content);
            mailer.send(mail);
        }
    }

    public static void main(String[] args) {
        var potter = new Newsletter("Potter 4ever",List.of(User.Nationality.BRITISH), 17);
        var java = new Newsletter("Java 4ever",List.of(User.Nationality.BRITISH, User.Nationality.FRENCH),21);
        var me = new Newsletter("Why me!", (user -> user.email().contains("@univ-eiffel.fr") && (user.age()%2) == 0));

        var samy = new User("Samy", "samy@gmail.com",23, User.Nationality.FRENCH);
        var momo = new User("Momo", "momo@gmail.com",14, User.Nationality.BRITISH);
        var souley = new User("Souley", "souley@univ-eiffel.fr",20, User.Nationality.BRITISH);
        var jylian = new User("Jylian", "jylian@univ-eiffel.fr",18, User.Nationality.SPANISH);
        var jojo = new User("Jojo", "jojo@gmail.fr",20, User.Nationality.FRENCH);

        potter.subscribe(samy);
        potter.subscribe(momo);
        potter.subscribe(souley);
        potter.subscribe(jylian);
        potter.subscribe(jojo);

        java.subscribe(samy);
        java.subscribe(momo);
        java.subscribe(souley);
        java.subscribe(jylian);
        java.subscribe(jojo);

        me.subscribe(samy);
        me.subscribe(momo);
        me.subscribe(souley);
        me.subscribe(jylian);
        me.subscribe(jojo);

        potter.sendMessage("test","test encore");
        java.sendMessage("test","test encore");
        me.sendMessage("test","test encore");

    }
}
