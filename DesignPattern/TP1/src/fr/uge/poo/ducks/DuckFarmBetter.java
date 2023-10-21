package fr.uge.poo.ducks;

import java.util.ServiceLoader;

public class DuckFarmBetter {
    public static void main(String[] args) {
        var riri  = new RegularDuckFactory().withName("Riri");
        var fifi  = new RegularDuckFactory().withName("Fifi");
        var loulou   = new RegularDuckFactory().withName("Loulou");

        System.out.println(riri.quack());
        System.out.println(fifi.quack());
        System.out.println(loulou.quack());

    }


}
