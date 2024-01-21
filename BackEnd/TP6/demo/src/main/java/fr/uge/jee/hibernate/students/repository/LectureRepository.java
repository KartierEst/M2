package fr.uge.jee.hibernate.students.repository;

import fr.uge.jee.hibernate.employees.Employee;
import fr.uge.jee.hibernate.employees.PersistenceUtils;
import fr.uge.jee.hibernate.students.model.Lecture;
import fr.uge.jee.hibernate.students.model.Student;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class LectureRepository implements CRUDRepository<Lecture, Long> {

    @Override
    public Long create(Lecture entity) {
        var newLecture = new Lecture(entity.getId(), entity.getName());
        PersistenceUtils.inTransaction(x -> {x.persist(newLecture);});
        return newLecture.getId();
    }

    @Override
    public List<Lecture> getAll() {
        return PersistenceUtils.inTransaction(x -> {
            var q = "SELECT e from Lecture e";
            TypedQuery<Lecture> query = x.createQuery(q,Lecture.class);
            return query.getResultList();
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> List<R> getAllByEntity(Lecture entity) {
        return PersistenceUtils.inTransaction(x -> {
            var q = "SELECT e FROM Student e WHERE e.id = :id";
            TypedQuery<R> query = (TypedQuery<R>) x.createQuery(q, Student.class);
            query.setParameter("id", entity.getId());
            return query.getResultList();
        });
    }

}
