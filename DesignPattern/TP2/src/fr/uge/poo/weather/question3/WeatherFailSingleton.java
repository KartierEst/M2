package fr.uge.poo.weather.question3;

import com.evilcorp.weatherservice.WeatherService;
import com.evilcorp.weatherservice.WeatherServiceNTS;
import com.evilcorp.weatherservice.WeatherServiceTSFail;

import java.util.Optional;

public class WeatherFailSingleton {
    // le constructeur va renvoyer une erreur
    // si la valeur alétoire est vrai.
    // Elle ne résseyera pas pour lancer le singleton
    // impossible que c'est le code qui renvoit une erreur
    // et pas l'entrée de l'utilisateur
    // un constructeur va verifier les parametres


    private static final Object lock = new Object();
    private static WeatherService INSTANCE = null;

    private static void tryInstance(){
        synchronized (lock){
            try{
                INSTANCE = new WeatherServiceTSFail();
            } catch (IllegalStateException e){
                System.out.println("cassé");
            }
        }
    }

    public static Optional<WeatherService> getInstance(){
        synchronized (lock) {
            if(INSTANCE == null) {
                tryInstance();
            }
            return Optional.ofNullable(INSTANCE);
        }
    }

    // faire une méthode qui retry jusqu'à que ca marche
    // et virer l'exception du constructeur en passant par
    // une méthode d'intermediaire

}
