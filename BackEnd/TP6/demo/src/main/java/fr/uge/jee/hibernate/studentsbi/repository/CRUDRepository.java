package fr.uge.jee.hibernate.studentsbi.repository;

import fr.uge.jee.hibernate.employees.Employee;
import fr.uge.jee.hibernate.employees.PersistenceUtils;
import fr.uge.jee.hibernate.students.model.Student;
import fr.uge.jee.hibernate.students.model.University;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface CRUDRepository<T,ID> {
    ID create(T entity);
    default boolean delete(Class<T> classEntity, ID id){
        return PersistenceUtils.inTransaction(x -> {
            var val = get(classEntity,id);
            if(val.isPresent()) {
                x.remove(val);
                return true;
            }
            return false;
        });
    }

    default boolean update(Class<T> entityClass, ID id, Consumer<T> updater){
        return PersistenceUtils.inTransaction(x -> {
            var rem = x.find(entityClass, id);
            if(rem != null){
                // ajout d'un cours
                updater.accept(rem);
                return true;
            }
            return false;
        });
    }
    default Optional<T> get(Class<T> entityClass, ID id){
        return Optional.ofNullable(PersistenceUtils.inTransaction(x -> {
            return x.find(entityClass, id);
        }));
    }

    List<T> getAll();

    <R> List<R> getAllByEntity(T entity);
}
