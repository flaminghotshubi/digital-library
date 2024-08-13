package com.example.demo_jpa.service;

import com.example.demo_jpa.model.Book;
import com.example.demo_jpa.model.Student;
import com.example.demo_jpa.model.Transaction;
import com.example.demo_jpa.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class) //how you want to execute your test cases
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    StudentService studentService;

    @Mock
    BookService bookService;

    @Mock
    TransactionRepository transactionRepository;

    // Happy flow
    @Test
    public void issueTxnTest() {
        String bookName = "ABC";
        int studentId = 2;
        String transactionId = UUID.randomUUID().toString();

        Book book = Book.builder()
                .name("ABC")
                .id(2)
                .build();

        List<Book> bookList = List.of(book);

        Student student = Student.builder()
                .id(studentId)
                .name("Peter")
                .validity(new Date(new Date().getTime() + 31536000000L))
                .build();

        Transaction transaction = Transaction.builder()
                .transactionId(transactionId)
                .book(book)
                .student(student)
                .build();

        Mockito.when(bookService.search(Mockito.any())).thenReturn(bookList);
        Mockito.when(studentService.get(studentId)).thenReturn(student);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
        Mockito.when(bookService.assignBookToStudent(student, book.getId())).thenReturn(book);
        Assert.assertEquals(transactionId, transactionService.issueTxn(bookName, studentId));
        //Mockito.verify
    }
    
}
