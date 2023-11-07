package fr.uge.poo.newsletter.question5;

import java.util.List;
import java.util.Objects;

public class SendMail {

    private final EMail email;

    public SendMail(EMail email){
        Objects.requireNonNull(email);
        this.email = email;
    }

    void sendWelcomeMail(NewsletterInfo info, String userMail){
        email.send(userMail, "Welcome","Welcome to " + info.name());
    }

    void sendMoreThan100(NewsletterInfo info, String content){
        email.send("sales@goodcorp.com", "[" + info.name() + "] ", "You have exceeded 100 users : " + content);
    }

    void sendToUniv(NewsletterInfo info){
        email.send("spy@nsa.org", "[" + info.name() + "] ", info.name());

        //users.stream().map(User::email).collect(Collectors.joining(", ")
    }

    void sendErrorSubscribe(NewsletterInfo info, String userName){
        email.send("support@goodcorp.com", "[" + userName + "] ", userName + " " + info.name());
    }
}
