package com.example.demo_jpa.controller;

import com.example.demo_jpa.dto.CreateAdminRequest;
import com.example.demo_jpa.model.Admin;
import com.example.demo_jpa.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("")
    public Admin create(@RequestBody @Valid CreateAdminRequest createAdminRequest) {
        return adminService.createAdmin(createAdminRequest.to());
    }
}
