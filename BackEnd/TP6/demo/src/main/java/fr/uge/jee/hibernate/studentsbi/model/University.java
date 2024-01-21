package fr.uge.jee.hibernate.studentsbi.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class University {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany
    Set<Student> students;

    @ManyToMany
    Set<Lecture> lectures;

    public University() {}

    public University(long id, String name, Set<Student> students, Set<Lecture> lectures) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.lectures = lectures;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
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
