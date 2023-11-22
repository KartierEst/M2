package fr.uge.jee.printers.ex2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        //var printer = new SimpleMessagePrinter();
        ApplicationContext context = new ClassPathXmlApplicationContext("config-printers.xml");
        MessagePrinter printer = context.getBean("printer", MessagePrinter.class);
        FrenchMessagePrinter frenchMessagePrinter = context.getBean("frenchMessagePrinter", FrenchMessagePrinter.class);
        SimpleMessagePrinter simpleMessagePrinter = context.getBean( "messagePrinter2", SimpleMessagePrinter.class);
        CustomizableMessagePrinter messagePrinter = context.getBean("customizableMessagePrinter", CustomizableMessagePrinter.class);
        frenchMessagePrinter.printMessage();
        simpleMessagePrinter.printMessage();
        messagePrinter.printMessage();
        printer.printMessage();
    }
}
