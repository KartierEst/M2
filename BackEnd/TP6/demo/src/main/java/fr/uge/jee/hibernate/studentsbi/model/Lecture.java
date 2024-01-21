package fr.uge.jee.hibernate.studentsbi.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Lecture {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToMany
    private Set<Student> students;
    @ManyToMany
    private Set<University> universities;

    public Lecture(long id, String name) {}

    public Lecture(long id, String name, Set<Student> students, Set<University> universities) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.universities = universities;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<University> getUniversities() {
        return universities;
    }

    public void setUniversities(Set<University> universities) {
        this.universities = universities;
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
