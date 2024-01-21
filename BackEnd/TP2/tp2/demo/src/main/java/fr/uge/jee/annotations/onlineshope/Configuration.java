package fr.uge.jee.annotations.onlineshope;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@org.springframework.context.annotation.Configuration
@ComponentScan
@PropertySource("classpath:onlineshop.properties")
public class Configuration {
/*    @Bean
    DroneDelivery droneDelivery(){
        return new DroneDelivery();
    }

    @Bean
    StandardDelivery standardDelivery(){
        return new StandardDelivery(5);
    }

    @Bean
    ReturnInsurance returnInsurance() {
        return new ReturnInsurance();
    }

    @Bean
    ThiefInsurance thiefInsurance() {
        return new ThiefInsurance();
    }*/

/*    @Bean
    OnlineShop shop(Set<Delivery> deliveries, Set<Insurance> insurances){
        //return new OnlineShop("AhMaZone", Set.of(standardDelivery()), Set.of(returnInsurance(),thiefInsurance()));
        return new OnlineShop("AhMaZone", deliveries, insurances);
    }*/
/*    @Bean
    OnlineShop shop(){
        //return new OnlineShop("AhMaZone", Set.of(standardDelivery()), Set.of(returnInsurance(),thiefInsurance()));
        return new OnlineShop("AhMaZone");
    }*/
}
