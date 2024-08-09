package com.example.demo_jpa.service;

import com.example.demo_jpa.model.Student;
import com.example.demo_jpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student createOrGet(Student student) {
        return studentRepository
                .findByContact(student.getContact())
                .orElseGet(() -> studentRepository.save(student));
    }

    public Student get(int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent()) return studentOptional.get();
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found");
    }

    public void delete(int id) {
        studentRepository.deleteById(id);
    }
}
