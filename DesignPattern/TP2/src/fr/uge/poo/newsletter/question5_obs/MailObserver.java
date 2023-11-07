package fr.uge.poo.newsletter.question5_obs;

import java.util.Objects;

public class MailObserver implements Observer {
    private final EMail email;

    public MailObserver(EMail email){
        Objects.requireNonNull(email);
        this.email = email;
    }

    @Override
    public void sendWelcomeMail(NewsletterInfo info, String userMail){
        email.send(userMail, "Welcome","Welcome to " + info.name());
    }

    @Override
    public void sendMoreThan100(NewsletterInfo info, String content){
        email.send("sales@goodcorp.com", "[" + info.name() + "] ", "You have exceeded 100 users : " + content);
    }

    @Override
    public void sendToUniv(NewsletterInfo info){
        email.send("spy@nsa.org", "[" + info.name() + "] ", info.name());
    }

    @Override
    public void sendErrorSubscribe(NewsletterInfo info, String userName){
        email.send("support@goodcorp.com", "[" + userName + "] ", userName + " " + info.name());
    }
}
