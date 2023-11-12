package fr.uge.poo.duck;

public class RegularDuck implements Duck {

    @Override
    public void quack() {
        System.out.println("Regular Quack !");
    }

    @Override
    public void quackManyTimes(int n) {
        for(int i=0; i < n; i++){
            quack();
        }
    }
}