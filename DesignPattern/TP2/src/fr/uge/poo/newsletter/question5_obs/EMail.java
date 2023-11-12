package fr.uge.poo.newsletter.question5_obs;

import com.goodcorp.gmailer.GMailer;

import java.util.List;
import java.util.stream.Collectors;

public interface EMail {
    void send(String recipient, String subject, String content);
    default void sendBulk(List<String> emails, String subject, String content){
        System.out.println("Sending mail to "+emails.stream().collect(Collectors.joining(", ","[ "," ]"))+" with title \""+subject+"\" and content \""+content+"\"");
    }
}
