package fr.uge.poo.newsletter.question5_obs;

import com.evilcorp.eemailer.EEMailer;
import com.goodcorp.gmailer.GMailer;

import java.util.List;
import java.util.stream.Collectors;

public class EemailAdapted implements EMail {

    private final EEMailer mailer = new EEMailer();


    @Override
    public void send(String recipient, String subject, String content) {
        var mail = new EEMailer.Mail(recipient, subject, content);
        mailer.send(mail);
    }
}



