package fr.uge.jee.aop.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lecture {

    private final long id;
    private final String title;
    private final List<Student> students = new ArrayList<>();

    public Lecture(Long id,String title) {
        this.id = id;
        this.title = title;
    }

    public void registerStudent(Student student){
        if (students.contains(student)){
            throw new IllegalStateException("fr.uge.jee.aop.students.Student "+student+"is already registered");
        }
        students.add(student);
    }

    public void unregisterStudent(Student student){
        if (!students.contains(student)){
            throw new IllegalStateException("fr.uge.jee.aop.students.Student "+student+"is not registered");
        }
        students.remove(student);
    }

    public String getTitle() {
        return title;
    }

    public List<Student> getStudents() {
        return List.copyOf(students);
    }

    @Override
    public String toString() {
        return "fr.uge.jee.aop.students.Lecture{" +
                "title='" + title + '\'' +
                ", students=" + students.stream().map(s -> s.getFirstName()+" "+s.getLastName()).collect(Collectors.joining(";"))+'\''+
                '}';
    }


}
