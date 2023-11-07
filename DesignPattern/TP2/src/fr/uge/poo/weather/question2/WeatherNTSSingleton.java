package fr.uge.poo.weather.question2;

import com.evilcorp.weatherservice.WeatherService;
import com.evilcorp.weatherservice.WeatherServiceNTS;

public class WeatherNTSSingleton {

    private WeatherNTSSingleton(){
        // initialisation des champs
    }

    private static final WeatherService INSTANCE = new WeatherService() {
        private final Object lock = new Object();
        private final WeatherServiceNTS serviceNTS = new WeatherServiceNTS();
        @Override
        public int query(String city){
            synchronized (lock) {
                return serviceNTS.query(city);
            }
        }
    };
    public static WeatherService getInstance(){
        return INSTANCE;
    }
}
