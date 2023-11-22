package fr.uge.jee.printers.ex3;

public class CountMessagePrinter implements MessagePrinter {
    // le signleton est utilisé
    private int count = 0;
    @Override
    public void printMessage() {
        System.out.println("This message number " + count);
        count++;
    }
}
