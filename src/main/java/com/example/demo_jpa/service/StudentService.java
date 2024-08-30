package com.example.demo_jpa.service;

import com.example.demo_jpa.model.Student;
import com.example.demo_jpa.model.User;
import com.example.demo_jpa.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Value("${student.authorities}")
    private String authorities;

    public Student createOrGet(Student student) {

        // Check if student is already present else create a new student
        Optional<Student> studentOptional = studentRepository.findByContact(student.getContact());
        if (studentOptional.isPresent()) {
            return studentOptional.get();

        } else {
            try {
                // create user with credentials
                User user = student.getUser();
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setAuthorities(authorities);
                user = userService.createUser(user);

                // create student
                student.setUser(user);
                return studentRepository.save(student);

            } catch (Exception e) {
                logger.error("Error occurred while creating student: ", e);
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }
    }

    public Student get(int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) return studentOptional.get();
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found");
    }

    public String delete(User currentUser) {
        studentRepository.deleteById(currentUser.getStudent().getId());
        return userService.deleteUser(currentUser);
    }
}
