package fr.uge.poo.weather.question3;

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
                if(WeatherFailSingleton.getInstance().isPresent()) {
                    System.out.println("Paris : " + WeatherFailSingleton.getInstance().get().query("Paris"));
                }
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
                if(WeatherFailSingleton.getInstance().isPresent()) {
                    System.out.println("Madrid : " + WeatherFailSingleton.getInstance().get().query("Paris"));
                }
            }
        });
    }
    public static void main(String[] args) throws InterruptedException {
        var application = new Application();
        application.start();
    }
}
