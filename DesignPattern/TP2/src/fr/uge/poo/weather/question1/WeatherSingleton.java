package fr.uge.poo.weather.question1;

import com.evilcorp.weatherservice.WeatherService;
import com.evilcorp.weatherservice.WeatherServiceTS;

public class WeatherSingleton {



    private WeatherSingleton(){
        // initialisation des champs
    }

    private static final WeatherServiceTS INSTANCE = new WeatherServiceTS();
    public static WeatherServiceTS getInstance(){
        return INSTANCE;
    }
}
