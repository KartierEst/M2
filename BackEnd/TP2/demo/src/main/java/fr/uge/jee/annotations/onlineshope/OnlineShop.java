package fr.uge.jee.annotations.onlineshope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OnlineShop {

    private String name;
    @Autowired
    private Set<Delivery> deliveryOptions;
    @Autowired
    private Set<Insurance> insurances;

/*    public OnlineShop(String name, Set<Delivery> deliveryOptions, Set<Insurance> insurances) {
        this.name = name;
        this.deliveryOptions = deliveryOptions;
        this.insurances = insurances;
    }*/

    public OnlineShop(@Value("${onlineshop.name}") String name) {
        this.name = name;
/*        this.deliveryOptions = deliveryOptions;
        this.insurances = insurances;*/
    }

/*    public OnlineShop(@Value("${onlineshop.name}") String name, Set<Delivery> deliveryOptions, Set<Insurance> insurances) {
        this.name = name;
        this.deliveryOptions = deliveryOptions;
        this.insurances = insurances;
    }*/

    public void printDescription(){
        System.out.println(name);
        System.out.println("Delivery options");
        deliveryOptions.forEach(opt -> System.out.println("\t"+opt.description()));
        System.out.println("Insurance options");
        insurances.forEach(insurance -> System.out.println("\t"+insurance.description()));
    }
}