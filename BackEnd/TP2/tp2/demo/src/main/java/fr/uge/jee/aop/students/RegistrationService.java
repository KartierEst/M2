package fr.uge.jee.aop.students;

import fr.uge.jee.aop.students.Lecture;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RegistrationService {
    private final HashMap<Long, Student> studentsById = new HashMap<>();
    private final HashMap<Long, Lecture> lecturesById = new HashMap<>();
    private long currentStudentId;
    private long currentLectureId;

    @Autowired
    private MyAspect aspect;


    /**
     *
     * @return studentId
     */

    public long createStudent(String firstName,String lastName){
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Student student = new Student(currentStudentId,firstName,lastName);
        studentsById.put(currentStudentId,student);
        return currentStudentId++;
    }

    public long createLecture(String title){
        Lecture lecture = new Lecture(currentLectureId,title);
        lecturesById.put(currentLectureId,lecture);
        return currentLectureId++;
    }


    public void register(long studentId, long lectureId){
        Lecture lecture = lecturesById.get(lectureId);
        if (lecture==null){
            throw new IllegalStateException("No such lecture "+lectureId);
        }
        Student student = studentsById.get(studentId);
        if (student==null){
            throw new IllegalStateException("No student created with id "+studentId);
        }
        student.registerToLecture(lecture);
    }

    public void loadFromDB() throws InterruptedException {
        Thread.sleep(100);
    }

    public void saveToDB() throws InterruptedException {
        Thread.sleep(200);
    }

    public void printReport() {
        for(Lecture lecture : lecturesById.values()){
            System.out.println(lecture.getTitle());
            for(Student student : lecture.getStudents()){
                System.out.println("\t"+student);
            }
        }
    }

    public void printTimeDb(){
        System.out.println(aspect.stringTimer());
    }
}
