package com.minorproject.digitallibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minorproject.digitallibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
