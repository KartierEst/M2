package fr.uge.jee.printers.ex1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        //var printer = new SimpleMessagePrinter();
        ApplicationContext context = new ClassPathXmlApplicationContext("config-printers.xml");
        SimpleMessagePrinter printer = context.getBean("messagePrinter", SimpleMessagePrinter.class);
        printer.printMessage();
    }
}
