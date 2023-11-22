package fr.uge.jee.printers.ex3;

public class CustomizableMessagePrinter implements MessagePrinter {

    // appel du constructeur par defaut

    private String message;

    public CustomizableMessagePrinter(){
        this.message="Custom Hello World";
    }

    public CustomizableMessagePrinter(String message){
        this.message=message;
    }

    @Override
    public void printMessage() {
        System.out.println(message);
    }
}