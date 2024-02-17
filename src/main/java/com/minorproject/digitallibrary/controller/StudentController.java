package com.minorproject.digitallibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minorproject.digitallibrary.dto.ResponseObject;
import com.minorproject.digitallibrary.dto.ResponseStatus;
import com.minorproject.digitallibrary.dto.StudentRequest;
import com.minorproject.digitallibrary.dto.StudentResponse;
import com.minorproject.digitallibrary.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@PostMapping("")
	// check to be added in case student already exists for same contact
	public ResponseObject createStudent(@RequestBody @Valid StudentRequest createStudentRequest) {
		ResponseObject ret;
		try {
			ret = ResponseObject.builder().status(ResponseStatus.SUCCESS).message("Student created successfully!").body(studentService.create(createStudentRequest)).build();
		} catch(Exception e) {
			ret = ResponseObject.builder().status(ResponseStatus.FAILED).message(e.getMessage()).build();
		}
		return ret;
	}
	
	@GetMapping("/{studentId}")
	public ResponseObject getStudent(@PathVariable("studentId") int id) {
		ResponseObject ret;
		try {
			StudentResponse student = studentService.getById(id);
			if(null == student) {
				throw new Exception("Student details not found!");
			}
			ret = ResponseObject.builder().status(ResponseStatus.SUCCESS).message("Student details fetched successfully!").body(student).build();
		} catch (Exception e) {
			ret = ResponseObject.builder().status(ResponseStatus.FAILED).message(e.getMessage()).build();
		}
		return ret;
	}
	
	@DeleteMapping("/{studentId}")
	public ResponseObject delete(@PathVariable("studentId") int id) {
		ResponseObject ret;
		try {
			studentService.deleteById(id);
			ret = ResponseObject.builder().status(ResponseStatus.SUCCESS).message("Student deleted successfully!").build();
		} catch (Exception e) {
			ret = ResponseObject.builder().status(ResponseStatus.FAILED).message(e.getMessage()).build();
		}
		return ret;
	}
	
}
