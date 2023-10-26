package fr.uge.poo.newsletter.question2;

import com.evilcorp.eemailer.EEMailer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Newsletter {
    private final String name;
    private List<User> users = new ArrayList<>();
    private final Predicate<User> predicate;
    
    public Newsletter(String name){
        Objects.requireNonNull(name);
        this.name = name;
        predicate = null;
    }

    public Newsletter(String name, Predicate<User> predicate){
        Objects.requireNonNull(name);
        Objects.requireNonNull(predicate);
        this.name = name;
        this.predicate = predicate;
    }

    void subscribe(User user){
        Objects.requireNonNull(user);
        if(users.stream().map(x -> x.email()).toList().contains(user.email())){
            System.err.println("the email already exist");
            return;
        }
        if(!predicate.test(user)){
            System.err.println("impossible to add you " + user.name() );
            return;
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
        var potter = new Newsletter("Potter 4ever", (user -> user.nationality() == User.Nationality.BRITISH && user.age() > 17));
        var java = new Newsletter("Java 4ever",(user -> user.nationality() != User.Nationality.SPANISH && user.age() > 21) );
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
