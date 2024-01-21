package fr.uge.jee.hibernate.students.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;

    @Embedded
    private Address address;

    @ManyToOne
    private University university;

    @OneToMany
    private List<Comment> commentList;

    @ManyToMany
    private Set<Lecture> lectureList;

    public Student() {}

    public Student(long id, String firstName, String lastName, Address address, University university, List<Comment> commentList, Set<Lecture> lectureList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.university = university;
        this.commentList = commentList;
        this.lectureList = lectureList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Set<Lecture> getLectureList() {
        return lectureList;
    }

    public void setLectureList(Set<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

    public void addLecture(Lecture lecture) {
        lectureList.add(Objects.requireNonNull(lecture));
    }

    public void addComment(Comment comment) {
        commentList.add(Objects.requireNonNull(comment));
    }

    public void deleteComment(Comment comment) {
        commentList.remove(comment);
    }
}