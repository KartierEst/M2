package fr.uge.poo.newsletter.question5_obs;

public interface Observer {

    default void subscribe(User user){}

    default void unsubscribe(User user){}

    default void sendMessage(User user){}

}
