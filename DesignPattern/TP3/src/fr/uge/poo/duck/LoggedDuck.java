package fr.uge.poo.duck;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggedDuck implements Duck {
    private static final Logger LOGGER = Logger.getLogger(LoggedDuck.class.getClass().getName());
    private final Duck duck;
    public LoggedDuck(Duck duck){
        this.duck = duck;
    }
    @Override
    public void quack(){
        LOGGER.log(Level.INFO, "Call to Quack !");
        duck.quack();
    }

    @Override
    public void quackManyTimes(int n) {
        // aucun log car on appel pas le quack du Logged Duck mais du duck donc pas de Logger
        // duck.quackManyTimes(n)
        // la on appel le quack qui log
        for(int i=0; i < n; i++){
            this.quack();
        }
    }
}
