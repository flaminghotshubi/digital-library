package com.minorproject.digitallibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minorproject.digitallibrary.dto.StudentRequest;
import com.minorproject.digitallibrary.dto.StudentResponse;
import com.minorproject.digitallibrary.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepo;
	
	public StudentResponse create(StudentRequest createStudentRequest) {
		return StudentResponse.from(studentRepo.save(createStudentRequest.to()));
	}
	
	public StudentResponse getById(int id) {
		return StudentResponse.from(studentRepo.findById(id).orElse(null));
	}
	
	public void deleteById(int id) {
		studentRepo.deleteById(id);
	}
}
