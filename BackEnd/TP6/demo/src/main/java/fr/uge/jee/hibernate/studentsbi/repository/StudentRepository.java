package fr.uge.jee.hibernate.studentsbi.repository;

import fr.uge.jee.hibernate.employees.Employee;
import fr.uge.jee.hibernate.employees.PersistenceUtils;
import fr.uge.jee.hibernate.studentsbi.model.*;
import fr.uge.jee.hibernate.students.repository.CRUDRepository;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class StudentRepository implements CRUDRepository<Student, Long> {
    @Override
    public Long create(Student entity) {
        var stud = new Student(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getAddress(), entity.getUniversity(), entity.getCommentList(), entity.getLectureList());
        PersistenceUtils.inTransaction(x -> {x.persist(stud);});
        return stud.getId();
    }

    public boolean updateLecture(Long id, Lecture lecture){
        return update(Student.class, id, (x) -> {
            var stud = get(Student.class, id);
            stud.ifPresent(student -> student.addLecture(lecture));
        });
    }

    public boolean updateUnivAdrr(Long id, University university, Address address){
        return update(Student.class, id, (x) -> {
            var stud = get(Student.class, id);
            if(stud.isPresent()) {
                stud.get().setAddress(address);
                stud.get().setUniversity(university);
            }
        });
    }

    public boolean addComments(Long id, Comment comment){
        return update(Student.class, id, (x) -> {
            var stud = get(Student.class, id);
            stud.ifPresent(student -> student.addComment(comment));
        });
    }

    public boolean deleteCommand(Long id, Comment comment){
        return update(Student.class, id, (x) -> {
            var stud = get(Student.class, id);
            stud.ifPresent(student -> student.deleteComment(comment));
        });
    }

    @Override
    public List<Student> getAll() {
        return PersistenceUtils.inTransaction(x -> {
            var q = "SELECT e from Student e";
            TypedQuery<Student> query = x.createQuery(q,Student.class);
            return query.getResultList();
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> List<R> getAllByEntity(Student entity) {
        return PersistenceUtils.inTransaction(x -> {
            var q = "SELECT e FROM Lecture e WHERE e.id = :id";
            TypedQuery<R> query = (TypedQuery<R>) x.createQuery(q, Lecture.class);
            query.setParameter("id", entity.getId());
            return query.getResultList();
        });
    }

}
