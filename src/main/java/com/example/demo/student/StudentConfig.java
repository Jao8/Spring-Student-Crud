package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.APRIL;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student John = new Student(
                    "John",
                    "joaovictorocosta@gmail.com",
                    LocalDate.of(2001, APRIL, 26)
            );

            Student Victor = new Student(
                    "Victor",
                    "victor.2001@gmail.com",
                    LocalDate.of(2009, APRIL, 24)
            );

            studentRepository.saveAll(
                    List.of(John, Victor)
            );
        };
    }
}
