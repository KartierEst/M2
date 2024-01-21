package fr.uge.jee.hibernate.studentsbi.model;

import fr.uge.jee.hibernate.video.model.User;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private long id;

    private String commentaire;
    @ManyToOne
    private User userWrite;

    @ManyToOne
    private User forUser;

    public Comment() {}

    public Comment(long id, String commentaire, User userWrite, User forUser) {
        this.id = id;
        this.commentaire = commentaire;
        this.userWrite = userWrite;
        this.forUser = forUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserWrite() {
        return userWrite;
    }

    public void setUserWrite(User userWrite) {
        this.userWrite = userWrite;
    }

    public User getForUser() {
        return forUser;
    }

    public void setForUser(User forUser) {
        this.forUser = forUser;
    }
}
