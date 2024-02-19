package com.minorproject.digitallibrary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minorproject.digitallibrary.dto.StudentRequest;
import com.minorproject.digitallibrary.dto.StudentResponse;
import com.minorproject.digitallibrary.model.Student;
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
	
	public List<StudentResponse> getAll() {
		List<Student> studentList = studentRepo.findAll();
		List<StudentResponse> ret = new ArrayList<>(studentList.size());
		studentList.forEach(student -> ret.add(StudentResponse.from(student)));
		return ret;
	}
}
