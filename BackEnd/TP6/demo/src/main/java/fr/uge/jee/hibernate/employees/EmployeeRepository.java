package fr.uge.jee.hibernate.employees;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class EmployeeRepository {
    private final EntityManagerFactory entityManagerFactory = PersistenceUtils.getEntityManagerFactory();

    /**
     * Create an employee with the given information
     *
     * @param firstName
     * @param lastName
     * @param salary
     * @return the id of the newly created employee
     */
    public long create(String firstName, String lastName, int salary) {
        var empl = new Employee(firstName,lastName,salary);
        PersistenceUtils.inTransaction(x -> {x.persist(empl);});
        return empl.getId();
    }

    /**
     * Remove the employee with the given id from the DB
     * @param id
     * @return true if the removal was successful
     */
    public boolean delete(long id) {
        return PersistenceUtils.inTransaction(x -> {
            var val = get(id);
            if(val.isPresent()) {
                x.remove(val);
                return true;
            }
            return false;
        });
    }

    /**
     * Update the salary of the employee with the given id
     * @param id
     * @param salary
     * @return true if the removal was successful
     */

    public boolean update(long id, int salary) {
        return PersistenceUtils.inTransaction(x -> {
            var rem = x.find(Employee.class, id);
            if(rem != null){
                rem.setSalary(rem.getSalary() + salary);
                return true;
            }
            return false;
        });
    }

    public boolean updateSalary(long id, BiConsumer<Employee, Integer> employeeConsumer, int salaryLimit) {
        PersistenceUtils.inTransaction(x -> {
            var rem = x.find(Employee.class, id);
            if(rem != null){
                employeeConsumer.accept(rem,salaryLimit);
            }
        });
        return true;
    }

    /**
     * Retrieve the employee with the given id
     * @param id
     * @return the employee wrapped in an {@link Optional}
     */

    public Optional<Employee> get(long id) {
        return Optional.ofNullable(PersistenceUtils.inTransaction(x -> {
            return x.find(Employee.class, id);
        }));
    }

    /**
     * Return the list of the employee in the DB
     */

    public List<Employee> getAll() {
        return PersistenceUtils.inTransaction(x -> {
            var q = "SELECT e from Employee e";
            TypedQuery<Employee> query = x.createQuery(q,Employee.class);
            return query.getResultList();
        });
    }

    public List<Employee> getAllByFirstName(String firstName){
        return PersistenceUtils.inTransaction(x -> {
            var q = "SELECT e FROM Employee e WHERE e.firstName= :firstname";
            TypedQuery<Employee> query = x.createQuery(q,Employee.class);
            query.setParameter("firstname", firstName);
            return query.getResultList();
        });
    }

}