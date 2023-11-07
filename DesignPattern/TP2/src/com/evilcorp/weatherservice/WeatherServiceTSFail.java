package com.evilcorp.weatherservice;

import java.util.concurrent.ThreadLocalRandom;

/* This class is thread-safe */
public class WeatherServiceTSFail implements WeatherService {

    public WeatherServiceTSFail() {
        if (ThreadLocalRandom.current().nextBoolean()){
            throw new IllegalStateException("Connection failed please try again later");
        }
        System.out.println("Creating a connection to WeatherServiceTSFail");
    }
    public int query(String city){
        return city.hashCode()%30;
    }
}
