package com.example.demo_jpa.controller;

import com.example.demo_jpa.dto.CreateStudentRequest;
import com.example.demo_jpa.model.Student;
import com.example.demo_jpa.model.User;
import com.example.demo_jpa.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("")
    public Student create(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
        return studentService.createOrGet(createStudentRequest.to());
    }

    @GetMapping("/details") // for students
    public Student get(@CurrentSecurityContext(expression = "authentication.principal") User currentUser) {
        // get studentId from security context
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        user.getStudent().getId();
        return studentService.get(currentUser.getStudent().getId());
    }

    @GetMapping("/{studentId}") // this can be invoked by admin only
    public Student get(@PathVariable int studentId) {
        return studentService.get(studentId);
    }

    @DeleteMapping("")   // only accessible to student
    public String delete(@CurrentSecurityContext(expression = "authentication.principal") User currentUser) {
        return studentService.delete(currentUser);
    }

}
