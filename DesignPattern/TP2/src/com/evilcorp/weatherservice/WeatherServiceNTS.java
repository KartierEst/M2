package com.evilcorp.weatherservice;

/* Assume that this class is not thread-safe */
public class WeatherServiceNTS implements WeatherService {

    public WeatherServiceNTS() {
        System.out.println("Creating a connection to WeatherServiceTS");
    }

    public int query(String city){
        return city.hashCode()%30;
    }
}
