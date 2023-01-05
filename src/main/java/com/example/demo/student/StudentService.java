package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void insert(Student student) {

        if(this.validateStudent(student.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        studentRepository.save(student);
    }

    public void delete(Long studentID ) {
        if(!this.validateStudent(studentID)){
            throw new IllegalStateException("This Student does not exist");
        }

        studentRepository.deleteById(studentID);
    }

    @Transactional
    public void update(Long studentID, String name, String email) {

        Student student = studentRepository.findById(studentID).orElseThrow(() -> new IllegalStateException(
                "Student does not exist"
        ));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){
                    if(this.validateStudent(email)){
                        throw new IllegalStateException("This email is already linked to another user");
                    }
            student.setEmail(email);
        }

    }

    private boolean validateStudent(Long studentID){
        return studentRepository.existsById(studentID);
    }

    private boolean validateStudent(String email){
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
        return studentOptional.isPresent();
    }
}
