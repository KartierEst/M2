package fr.uge.jee.hibernate.video.model;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.List;
import java.util.Set;

@Entity
public class Video {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @Lob
    private Blob path;
    @ManyToOne
    private User user;

    @OneToMany
    private Set<UpVote> upVote;

    @OneToMany
    private Set<DownVote> downVote;

    public Video() {}
    public Video(long id, String name, Blob path, User user) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getPath() {
        return path;
    }

    public void setPath(Blob path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
