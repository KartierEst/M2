package fr.uge.jee.hibernate.students.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class University {
    @Id
    @GeneratedValue
    private long id;

    private String name;

/*    List<Student> students;

    List<Lecture> students;*/

    public University() {}

    public University(long id, String name) {
        this.id = id;
        this.name = name;
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
}
