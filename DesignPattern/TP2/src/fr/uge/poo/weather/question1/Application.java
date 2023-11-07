package fr.uge.poo.weather.question1;

import com.evilcorp.weatherservice.WeatherService;
import com.evilcorp.weatherservice.WeatherServiceTS;

public class Application {

    public void start() throws InterruptedException {


        System.out.println("Warming  up");
        Thread.sleep(5_000);
        System.out.println("Starting");


        Thread.ofPlatform().start(() -> {
            for(;;){
                //WeatherService service = new WeatherServiceTS();
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new AssertionError();
                }
                System.out.println("Paris : "+WeatherSingleton.getInstance().query("Paris"));
            }
        });

        Thread.ofPlatform().start(() -> {
            for(;;){
                //WeatherService service = new WeatherServiceTS();
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new AssertionError();
                }
                System.out.println("Madrid : "+ WeatherSingleton.getInstance().query("Madrid"));
            }
        });
    }
    public static void main(String[] args) throws InterruptedException {
        var application = new Application();
        application.start();
    }
}
