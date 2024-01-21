package fr.uge.jee.hibernate.employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.SpringApplication;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Application {
    public static void main(String[] args) {
        var repository = new EmployeeRepository();
        var idMoran = repository.create("Bob", "Moran", 500);
        var idDylan =repository.create("Bob", "Dylan", 500);
        var idLisa = repository.create("Lisa", "Simpson", 500);
        var idMarge = repository.create("Marge", "Simpson", 500);
        var idHomer = repository.create("Homer", "Simpson", 500);

        BiConsumer<Employee,Integer> consumer = (x, sal) -> {
            if (x.getSalary() < sal) {
                x.setSalary(100 + x.getSalary());
            } else {
                x.setSalary((int) (x.getSalary() * 1.10));
            }
        };

        repository.delete(idLisa);

        repository.update(idHomer, 100);

        repository.updateSalary(idLisa, consumer, 550);
        repository.updateSalary(idMoran, consumer, 550);
        repository.updateSalary(idDylan, consumer, 550);
        repository.updateSalary(idHomer, consumer, 550);
        repository.updateSalary(idMarge, consumer, 550);


        System.out.println(repository.getAll());
        System.out.println(repository.getAllByFirstName("Homer"));

/*      SpringApplication.run(Application.class, args);
        EntityManagerFactory emf = PersistenceUtils.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        var tx = em.getTransaction();
        try{
            tx.begin();
            var harry = new Employee("Harry","Potter",1000);
            em.persist(harry);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }*/
    }
}
