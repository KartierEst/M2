package fr.uge.jee.springmvc.reststudents;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

/*    @Bean
    public CommandLineRunner printFirstStudent() {
        return args -> {
            WebClient webClient = WebClient.create();
            var student = webClient.get()
                    .uri("http://localhost:8080/students/1")
                    .retrieve()
                    .bodyToMono(Student.class)
                    .block();
            System.out.println(student);
        };
    }*/

    @Bean
    public CommandLineRunner printStudents() {
        return args -> {
            WebClient webClient = WebClient.create();
            List<Student> students = webClient.get()
                    .uri("http://localhost:8080/students")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Student>>(){})
                    .block();
            System.out.println(students);
        };
    }
}