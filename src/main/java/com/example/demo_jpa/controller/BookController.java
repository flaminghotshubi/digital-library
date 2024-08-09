package com.example.demo_jpa.controller;

import com.example.demo_jpa.dto.CreateBookRequest;
import com.example.demo_jpa.dto.SearchBookRequest;
import com.example.demo_jpa.model.Book;
import com.example.demo_jpa.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("")
    public Book create(@RequestBody @Valid CreateBookRequest createBookRequest) {
        return bookService.create(createBookRequest.to());
    }

    @DeleteMapping("/{bookId}")
    public void delete(@PathVariable int bookId) {
        bookService.delete(bookId);
    }

    @GetMapping("/search")
    public List<Book> search(@RequestBody @Valid SearchBookRequest searchBookRequest) {
        if(!searchBookRequest.validate()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid search parameters");
        }
        return bookService.search(searchBookRequest);
    }

}
