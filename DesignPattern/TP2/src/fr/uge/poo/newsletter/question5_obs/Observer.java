package fr.uge.poo.newsletter.question5_obs;

public interface Observer {

    default void sendWelcomeMail(NewsletterInfo info, String userMail) {}

    default void sendMoreThan100(NewsletterInfo info, String content){}

    default void sendToUniv(NewsletterInfo info){}

    default void sendErrorSubscribe(NewsletterInfo info, String userName){}
}
