package fr.uge.jee.bookstore;

import fr.uge.jee.printers.ex1.SimpleMessagePrinter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config-bookstore.xml");
        Library library = context.getBean("library", Library.class);
        System.out.println(library);
    }
}
