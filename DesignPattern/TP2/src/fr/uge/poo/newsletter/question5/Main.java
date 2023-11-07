package fr.uge.poo.newsletter.question5;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var mail = Arrays.asList(args).contains("-gmail");
        var email = mail ? new GmailerAdapted() : new EemailAdapted();


        var potter = new Newsletter("Potter 4ever", List.of(User.Nationality.BRITISH), 17, email);
        var java = new Newsletter("Java 4ever",List.of(User.Nationality.BRITISH, User.Nationality.FRENCH),21, email);
        var me = new Newsletter("Why me!", (user -> user.email().contains("@univ-eiffel.fr") && (user.age()%2) == 0), email);

        var samy = new User("Samy", "samy@gmail.com",23, User.Nationality.FRENCH);
        var momo = new User("Momo", "momo@gmail.com",14, User.Nationality.BRITISH);
        var souley = new User("Souley", "souley@etud.univ-eiffel.fr",20, User.Nationality.BRITISH);
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

        potter.sendMessageBulk("test","test encore");
        java.sendMessageBulk("test","test encore");
        me.sendMessageBulk("test","test encore");
    }
}
