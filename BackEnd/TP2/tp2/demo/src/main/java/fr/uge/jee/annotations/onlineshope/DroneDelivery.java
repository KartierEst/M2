package fr.uge.jee.annotations.onlineshope;

public class DroneDelivery implements Delivery{
    @Override
    public String description() {
        return "Delivery by drone";
    }
}
