package fr.uge.jee.annotations.messenger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Messenger {
    private final String token;
    public Messenger(@Value("#{systemEnvironment['MESSENGER_TOKEN']}") String token){
        this.token = token;
    }

/*    @Value("A123G32G234H34245234")
    private String token;*/

    public void send(String message){
        System.out.println("Using the super secret token "+token+" to send the message : "+message);
    }
}
