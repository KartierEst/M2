package fr.uge.poo.newsletter.question5_obs;

import java.util.List;

public interface EMail {
    public void send(String recipient, String subject, String content);
    public void sendBulk(List<String> emails, String subject, String content);
}
