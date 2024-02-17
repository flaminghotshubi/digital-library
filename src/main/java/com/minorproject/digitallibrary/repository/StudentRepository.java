package com.minorproject.digitallibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minorproject.digitallibrary.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
}
