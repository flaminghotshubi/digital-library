package com.minorproject.digitallibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minorproject.digitallibrary.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

	Author findByEmail(String email);
	
}
