package fr.uge.jee.springmvc.reststudents;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class HelloRestController {

    private final Map<Long,Student> students = Map.of(1L, new Student(1,"test","test"), 2L , new Student(2,"ok","ok"), 3L , new Student(3,"moi","moi"));

    @GetMapping("/students/{id}")
    public Student getStudent(@PathVariable("id") long id) {
        var student = students.get(id);
        if (student == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No student with id ("+id+")");
        } else {
            return student;
        }
    }

    @GetMapping("/students")
    public List<Student> getStudent() {
        if (students.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No students)");
        } else {
            return students.values().stream().toList();
        }
    }
}