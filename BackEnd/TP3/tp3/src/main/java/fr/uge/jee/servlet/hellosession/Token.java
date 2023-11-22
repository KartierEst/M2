package fr.uge.jee.servlet.hellosession;

import jakarta.servlet.http.Cookie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Token {

    private final Object lock = new Object();
    private final HashMap<UUID, Integer> hashMap = new HashMap<>();

    public void addToken(UUID token){
        synchronized (lock) {
            hashMap.merge(token, 1, Integer::sum);
        }
    }

    public int getNb(UUID key){
        synchronized (lock) {
            return hashMap.get(key);
        }
    }
}
