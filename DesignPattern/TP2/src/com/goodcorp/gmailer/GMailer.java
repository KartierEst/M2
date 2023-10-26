package com.goodcorp.gmailer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This is a dummy class meant to represent an real mailer API but which
 * of course does not send any email.
 */

public class GMailer {

    /**
     * Mail is a class used by GMailer to represent the content of an email.
     */

    static public class Mail{
        private final String subject;
        private final String content;

        public Mail(String subject, String content) {
            this.subject = Objects.requireNonNull(subject);
            this.content = Objects.requireNonNull(content);
        }
    }

    /**
     * Send a mail to all the given emails.
     * This method is much faster than a repeated calls to the send method
     *
     * @param emails the list of the emails the mail is sent to:
     * @param mail the content of the mail
     */

    public void sendBulk(List<String> emails, Mail mail){
        Objects.requireNonNull(emails);
        System.out.println("Sending mail to "+emails.stream().collect(Collectors.joining(", ","[ "," ]"))+" with title \""+mail.subject+"\" and content \""+mail.content+"\"");
    }

    /**
     * Sends a mail to the email address
     *
     * @param email the email address the mail is sent to
     * @param mail content of the mail
     */

    public void send(String email, Mail mail){
        Objects.requireNonNull(email);
        System.out.println("Sending mail to "+email+" with title \""+mail.subject+"\" and content \""+mail.content+"\"");
    }

    /**
     * A simple usage example
     */

    public static void main(String[] args) {
        GMailer emailer = new GMailer();
        Mail mail = new Mail("Examen rattrapage","Je commence !");
        emailer.sendBulk(List.of("carayol@u-pem.fr","duris@u-pem.fr"),mail);
        emailer.send("forax@u-pem.fr",mail);
    }
}
