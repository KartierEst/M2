package fr.uge.jee.hibernate.students.repository;

import fr.uge.jee.hibernate.employees.Employee;
import fr.uge.jee.hibernate.employees.PersistenceUtils;
import fr.uge.jee.hibernate.students.model.Comment;
import fr.uge.jee.hibernate.students.model.Lecture;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CommentRepository implements CRUDRepository<Comment, Long> {
    @Override
    public Long create(Comment entity) {
        var comment = new Lecture(entity.getId(), entity.getCommentaire());
        PersistenceUtils.inTransaction(x -> {x.persist(comment);});
        return comment.getId();
    }

    @Override
    public List<Comment> getAll() {
        return PersistenceUtils.inTransaction(x -> {
            var q = "SELECT e from Comment e";
            TypedQuery<Comment> query = x.createQuery(q,Comment.class);
            return query.getResultList();
        });
    }

    @Override
    public <R> List<R> getAllByEntity(Comment entity) {
        return null;
    }
}
