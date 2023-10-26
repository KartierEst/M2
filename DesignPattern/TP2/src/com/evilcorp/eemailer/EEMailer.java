package com.evilcorp.eemailer;

import java.util.Objects;

/**
 * This is a dummy class meant to represent an real mailer API but which
 * of course does not send any email.
 */

public class EEMailer {

    public record Mail(String recipient, String subject, String content){
        public Mail {
            Objects.requireNonNull(recipient);
            Objects.requireNonNull(subject);
            Objects.requireNonNull(content);
        }
    }

    /**
     * Send a mail
     */

    public void send(Mail mail){
        Objects.requireNonNull(mail);
        System.out.println("EEMailer : Sending mail to "+mail.recipient+" with title \""+mail.subject+"\" and content \""+mail.content+"\"");
    }

    /**
     * A simple usage example
     */

    public static void main(String[] args) {
        var mailer = new EEMailer();
        var mail = new Mail("arnaud.carayol@univ-eiffel.fr","Test","Body of the mail");
        mailer.send(mail);
    }
}
