package fr.uge.poo.newsletter.question4;
import com.evilcorp.eemailer.EEMailer;
import com.goodcorp.gmailer.GMailer;

import java.util.List;

public class GmailerAdapted implements EMail {

    private final GMailer mailer = new GMailer();

    @Override
    public void send(String recipient, String subject, String content){
        var mail = new GMailer.Mail(subject, content);
        mailer.send(recipient,mail);
    }

    @Override
    public void sendBulk(List<String> emails, String subject, String content){
        var mail = new GMailer.Mail(subject, content);
        mailer.sendBulk(emails, mail);
    }

}