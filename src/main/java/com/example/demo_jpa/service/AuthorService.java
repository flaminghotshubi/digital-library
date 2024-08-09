package com.example.demo_jpa.service;

import com.example.demo_jpa.model.Author;
import com.example.demo_jpa.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author createOrReturn(Author authorRequest) {
        return authorRepository
                .findByEmail(authorRequest.getEmail())
                .orElseGet(() -> authorRepository.save(authorRequest));
    }
}
