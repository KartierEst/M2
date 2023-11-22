package fr.uge.jee.annotations.onlineshope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Configuration.class);
        //OnlineShop amazon = applicationContext.getBean("shop", OnlineShop.class);
        OnlineShop amazon = applicationContext.getBean(OnlineShop.class);
        amazon.printDescription();
    }
}
