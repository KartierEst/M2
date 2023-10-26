package fr.uge.poo.newsletter.question1;

import com.evilcorp.eemailer.EEMailer;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Newsletter {
    private final String name;
    private List<User> users;
    
    public Newsletter(String name){
        Objects.requireNonNull(name);
        this.name = name;
    }

    void subscribe(User user){
        Objects.requireNonNull(user);
        if(users.stream().map(x -> x.email()).toList().contains(user.email())){
            throw new IllegalArgumentException("the email already exist");
        }
        users.add(user);
    }

    void unsubscribe(User user){
        Objects.requireNonNull(user);
        users.remove(user);
    }

    void sendMessage(String title, String content){
        var mailer = new com.evilcorp.eemailer.EEMailer();
        for(var user : users){
            var mail = new EEMailer.Mail(user.email(),"[" + name + "]" + title,content);
        }
    }
}
