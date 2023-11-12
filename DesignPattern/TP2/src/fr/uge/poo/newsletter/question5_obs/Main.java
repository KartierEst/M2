package fr.uge.poo.newsletter.question5_obs;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var mail = Arrays.asList(args).contains("-gmail");
        var email = mail ? new GmailerAdapted() : new EemailAdapted();
        var potter = Newsletter.with().name("Potter 4ever").nationality(User.Nationality.BRITISH).age(18).mail(email).build();
        var java = Newsletter.with().name("Java 4ever").nationalities(List.of(User.Nationality.BRITISH, User.Nationality.FRENCH))
                .age(21).mail(email).build();
        var me = Newsletter.with().name("Why me!").predicate((user -> user.email().contains("@univ-eiffel.fr") && (user.age()%2) == 0)).mail(email).build();

        var samy = new User("Samy", "samy@gmail.com",23, User.Nationality.FRENCH);
        var momo = new User("Momo", "momo@gmail.com",14, User.Nationality.BRITISH);
        var souley = new User("Souley", "souley@univ-eiffel.fr",20, User.Nationality.BRITISH);
        var jylian = new User("Jylian", "jylian@univ-eiffel.fr",18, User.Nationality.SPANISH);
        var jojo = new User("Jojo", "jojo@gmail.fr",20, User.Nationality.FRENCH);

        potter.register(potter.WELCOME_MAIL);
        potter.register(potter.ERROR_SUBSCRIBE);
        potter.register(potter.MOST_THAN_100_USERS);
        potter.register(potter.STUDENT_MAIL);

        java.register(java.WELCOME_MAIL);
        java.register(java.ERROR_SUBSCRIBE);
        java.register(java.MOST_THAN_100_USERS);
        java.register(java.STUDENT_MAIL);

        me.register(me.WELCOME_MAIL);
        me.register(me.ERROR_SUBSCRIBE);
        me.register(me.MOST_THAN_100_USERS);
        me.register(me.STUDENT_MAIL);


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

        potter.sendMessage("potter","test potter");
        java.sendMessage("java","test java");
        me.sendMessage("me","test me");

        potter.sendMessageBulk("test","test encore");
        java.sendMessageBulk("test","test encore");
        me.sendMessageBulk("test","test encore");

    }
}
