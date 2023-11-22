package fr.uge.jee.onlineshop;

public class StandardDelivery implements Delivery{
    private final int delay;

    public StandardDelivery(int delay) {
        this.delay = delay;
    }

    @Override
    public String description() {
        return "Standard Delivery with a delay of " + delay + " days";
    }
}
