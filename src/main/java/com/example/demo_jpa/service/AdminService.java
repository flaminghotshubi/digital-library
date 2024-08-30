package com.example.demo_jpa.service;

import com.example.demo_jpa.model.Admin;
import com.example.demo_jpa.model.User;
import com.example.demo_jpa.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {

    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Value("${admin.authorities}")
    private String authorities;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    public Admin createAdmin(Admin admin) {
        try {
            // create user with credentials
            User user = admin.getUser();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuthorities(authorities);
            user = userService.createUser(user);

            // create admin
            admin.setUser(user);
            return adminRepository.save(admin);

        } catch (Exception e) {
            logger.error("Error occurred while creating admin: ", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
