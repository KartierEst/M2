package fr.uge.jee.printers.ex3;

public class SlowConstructionMessagePrinter implements MessagePrinter {

    SlowConstructionMessagePrinter() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("test");
        }
    }
    @Override
    public void printMessage() throws InterruptedException {
        System.out.println("slow hello !");
    }
}
