package fr.uge.jee.printers.ex3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    // le bean est créer au moment de l'appel du premier getBean
    // Faux -> tout les beans sont crées au moment de la création de la variable
    // si un bean utilise un autre, il aura besoin de l'attendre ou ca va peut créer une erreur.
    public static void main(String[] args) throws InterruptedException {
        //var printer = new SimpleMessagePrinter();
        ApplicationContext context = new ClassPathXmlApplicationContext("config-printers.xml");
        var printer2 = context.getBean("messagePrinter3", MessagePrinter.class);
        printer2.printMessage();
        MessagePrinter printer = context.getBean("slowMessagePrinter",MessagePrinter.class);
        printer.printMessage();
/*        printer.printMessage();
        printer.printMessage();
        MessagePrinter printer2 =  context.getBean("countMessagePrinter",MessagePrinter.class);
        printer2.printMessage();*/
    }
}
