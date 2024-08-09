package com.example.demo_jpa.controller;

import com.example.demo_jpa.dto.CreateStudentRequest;
import com.example.demo_jpa.model.Student;
import com.example.demo_jpa.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("")
    public Student create(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
        return studentService.createOrGet(createStudentRequest.to());
    }

    @GetMapping("/{studentId}")
    public Student get(@PathVariable int studentId) {
        return studentService.get(studentId);
    }

    @DeleteMapping("/{studentId}")
    public void delete(@PathVariable int studentId) {
        studentService.delete(studentId);
    }

}
