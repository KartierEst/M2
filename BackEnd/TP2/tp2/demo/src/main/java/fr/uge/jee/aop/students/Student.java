package fr.uge.jee.aop.students;

import fr.uge.jee.aop.students.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Student {

    private final long id;
    private final String firstName;
    private final String lastName;

    private final Set<Lecture> lectures = new HashSet<>();

    public Student(long id,String firstName,String lastName) {
        this.id = id;
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void registerToLecture(Lecture lecture){
        Objects.requireNonNull(lecture);
        if (lectures.contains(lecture)){
            throw new IllegalStateException("fr.uge.jee.aop.students.Student"+toString()+" is already registered in lecture "+lecture);
        }
        lectures.add(lecture);
        lecture.registerStudent(this);
    }

    public Set<Lecture> getLectures() {
        return Set.copyOf(lectures);
    }

    @Override
    public String toString() {
        return "fr.uge.jee.aop.students.Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lectures=" + lectures.stream().map(Lecture::getTitle).collect(Collectors.joining(" ; ")) +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
