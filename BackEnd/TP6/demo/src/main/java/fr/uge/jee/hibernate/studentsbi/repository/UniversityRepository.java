package fr.uge.jee.hibernate.studentsbi.repository;

import fr.uge.jee.hibernate.employees.Employee;
import fr.uge.jee.hibernate.employees.PersistenceUtils;
import fr.uge.jee.hibernate.students.model.Lecture;
import fr.uge.jee.hibernate.students.model.University;
import fr.uge.jee.hibernate.students.repository.CRUDRepository;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class UniversityRepository implements CRUDRepository<University, Long> {

    @Override
    public Long create(University entity) {
        var newUniv = new University(entity.getId(), entity.getName());
        PersistenceUtils.inTransaction(x -> {x.persist(newUniv);});
        return newUniv.getId();
    }

    @Override
    public List<University> getAll() {
        return PersistenceUtils.inTransaction(x -> {
            var q = "SELECT e from University e";
            TypedQuery<University> query = x.createQuery(q,University.class);
            return query.getResultList();
        });
    }

    @Override
    public <R> List<R> getAllByEntity(University entity) {
        return null;
    }
}
