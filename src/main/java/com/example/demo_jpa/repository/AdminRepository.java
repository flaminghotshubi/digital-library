package com.example.demo_jpa.repository;

import com.example.demo_jpa.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
