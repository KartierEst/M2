package fr.uge.poo.newsletter.question5;

import com.evilcorp.eemailer.EEMailer;

import java.util.List;
import java.util.stream.Collectors;

public class EemailAdapted implements EMail {

    private final EEMailer mailer = new EEMailer();


    @Override
    public void send(String recipient, String subject, String content) {
        var mail = new EEMailer.Mail(recipient, subject, content);
        mailer.send(mail);
    }

    @Override
    public void sendBulk(List<String> emails, String subject, String content){
        System.out.println("Sending mail to "+emails.stream().collect(Collectors.joining(", ","[ "," ]"))+" with title \""+subject+"\" and content \""+content+"\"");
    }
}



