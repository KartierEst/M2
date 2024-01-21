package org.example;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main {
    public static void main(String[] args) {
        // Récupérer une instance de Jedis depuis le pool
        try (JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost")) {
            var jedis = jedisPool.getResource();
            // Exemple de lecture de données depuis Redis
            String id = jedis.get("id");
            String name = jedis.get("name");
            String firstName = jedis.get("first name");

            if (id != null && name != null && firstName != null) {
                System.out.println("id " + id + " name : " + name + " firstName : " + firstName);
            }
        }
    }
}