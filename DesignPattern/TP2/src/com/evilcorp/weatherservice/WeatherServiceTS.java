package com.evilcorp.weatherservice;

/* This class is thread-safe */
public class WeatherServiceTS implements WeatherService {

    public WeatherServiceTS() {
        System.out.println("Creating a connection to WeatherServiceTS");
    }

    public int query(String city){
        return city.hashCode()%30;
    }
}
