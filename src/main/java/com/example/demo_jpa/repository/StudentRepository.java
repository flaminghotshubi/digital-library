package com.example.demo_jpa.repository;

import com.example.demo_jpa.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByContact(String contact);
}
