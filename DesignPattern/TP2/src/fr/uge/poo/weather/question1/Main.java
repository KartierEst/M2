package fr.uge.poo.weather.question1;

import com.evilcorp.weatherservice.WeatherServiceTS;

public class Main {
    public static void main(String[] args) {
        var service = new WeatherServiceTS();
        System.out.println(service.query("Madrid"));
    }
}
