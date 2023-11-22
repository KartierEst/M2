package fr.uge.jee.onlineshop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config-online.xml");
        OnlineShop amazon = context.getBean("shop", OnlineShop.class);
        amazon.printDescription();

        ApplicationContext context2 = new ClassPathXmlApplicationContext("config-online2.xml");
        OnlineShop ahmazon = context2.getBean("shop", OnlineShop.class);
        ahmazon.printDescription();

        ApplicationContext context3 = new ClassPathXmlApplicationContext("config-online3.xml");
        OnlineShop shop = context3.getBean(OnlineShop.class);
        shop.printDescription();
    }
}
