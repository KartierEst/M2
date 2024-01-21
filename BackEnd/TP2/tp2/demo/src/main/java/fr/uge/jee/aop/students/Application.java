package fr.uge.jee.aop.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class Application {
    public static void main(String[] args) throws InterruptedException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        RegistrationService registrationService = applicationContext.getBean(RegistrationService.class);


        //fr.uge.jee.aop.students.RegistrationService registrationService = new fr.uge.jee.aop.students.RegistrationService();
        registrationService.loadFromDB();
        long idHarry = registrationService.createStudent("Harry","Potter");
        long idHermione = registrationService.createStudent("Hermione","Granger");
        long idRon = registrationService.createStudent("Ron","Wesley");
        registrationService.saveToDB();
        long idPotions = registrationService.createLecture("Potions");
        registrationService.register(idHarry,idPotions);
        registrationService.register(idHermione,idPotions);
        registrationService.register(idRon,idPotions);
        registrationService.saveToDB();
        long idMalfoy = registrationService.createStudent("Draco","Malfoy");
        registrationService.saveToDB();
        registrationService.loadFromDB();
        long idDetention = registrationService.createLecture("Detention");
        registrationService.register(idHarry,idDetention);
        registrationService.register(idMalfoy,idDetention);

        registrationService.printReport();
        registrationService.printTimeDb();
    }
}
