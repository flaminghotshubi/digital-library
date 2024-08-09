package com.example.demo_jpa.service;

import com.example.demo_jpa.dto.SearchBookRequest;
import com.example.demo_jpa.model.Author;
import com.example.demo_jpa.model.Book;
import com.example.demo_jpa.model.Genre;
import com.example.demo_jpa.model.Student;
import com.example.demo_jpa.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    public Book create(Book book) {
        Author author = authorService.createOrReturn(book.getAuthor());
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book assignBookToStudent(Student student, int bookId) {
        int updatedCount = bookRepository.assignBookToStudent(student, bookId);
        if(updatedCount > 0) {
            return bookRepository.findById(bookId).orElse(null);
        }
        return null;
    }

    @Transactional
    public Book unassignBookFromStudent(int bookId) {
        int updatedCount = bookRepository.unassignBookFromStudent(bookId);
        if(updatedCount > 0) {
            return bookRepository.findById(bookId).orElse(null);
        }
        return null;
    }

    /*
    The map method in Optional allows you to transform the value contained
    within the Optional if it is present. It applies a function to the value
    and returns a new Optional containing the result of the function.
    If the Optional is empty, map simply returns an empty Optional.
    * */
    public List<Book> search(SearchBookRequest searchBookRequest) {
        List<Book> bookList;
        String searchValue = searchBookRequest.getSearchValue();
        try {
            if (searchBookRequest.isAvailable()) {
                bookList = switch (searchBookRequest.getSearchKey()) {
                    case "id" -> bookRepository.findByIdAndStudentIsNull(Integer.parseInt(searchValue))
                            .map(Arrays::asList)
                            .orElseGet(Collections::emptyList);
                    case "genre" -> bookRepository.findByGenreAndStudentIsNull(Genre.valueOf(searchValue));
                    case "name" -> searchBookRequest.getOperator().equals("=") ?
                            bookRepository.findByNameAndStudentIsNull(searchValue) :
                            bookRepository.findByNameContainingAndStudentIsNull(searchValue);
                    case "author" -> searchBookRequest.getOperator().equals("=") ?
                            bookRepository.findByAuthorAndStudentIsNull(searchValue) :
                            bookRepository.findByAuthorContainingAndStudentIsNull(searchValue);
                    default -> Collections.emptyList();
                };
            } else {
                bookList = switch (searchBookRequest.getSearchKey()) {
                    case "id" -> bookRepository.findById(Integer.parseInt(searchValue))
                            .map(Arrays::asList)
                            .orElseGet(Collections::emptyList);
                    case "genre" -> bookRepository.findByGenre(Genre.valueOf(searchValue));
                    case "name" -> searchBookRequest.getOperator().equals("=") ?
                            bookRepository.findByName(searchValue) :
                            bookRepository.findByNameContaining(searchValue);
                    case "author" -> searchBookRequest.getOperator().equals("=") ?
                            bookRepository.findByAuthor(searchValue) :
                            bookRepository.findByAuthorContaining(searchValue);
                    default -> Collections.emptyList();
                };
            }
        } catch (Exception e) {
            logger.error("Exception occurred: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return bookList;
    }
}
