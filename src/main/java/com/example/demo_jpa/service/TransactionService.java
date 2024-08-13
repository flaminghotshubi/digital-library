package com.example.demo_jpa.service;

import com.example.demo_jpa.dto.SearchBookRequest;
import com.example.demo_jpa.model.*;
import com.example.demo_jpa.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    BookService bookService;

    @Autowired
    StudentService studentService;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${student.issue.limit:5}")
    private int bookLimit;

    @Value("${student.issue.numberOfDaysOfIssuance:7}")
    private int numberOfDaysOfIssuance;

    public String issueTxn(String name, int sid) {
        List<Book> availableBooks = bookService.search(SearchBookRequest.builder()
                .isAvailable(true)
                .searchKey("name")
                .searchValue(name)
                .operator("=")
                .build());

        if (availableBooks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        // Validations
        Student student = studentService.get(sid);
        List<Book> issuedBooks = student.getBookList();
        if (null != issuedBooks && student.getBookList().size() == bookLimit)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Max book limit reached");
        if (new Date().after(student.getValidity()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Student access expired");

        // Issue
        Book book = availableBooks.get(0);
        Transaction transaction = transactionRepository.save(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .book(book)
                .student(student)
                .transactionStatus(TransactionStatus.PENDING)
                .build());

        book = bookService.assignBookToStudent(student, book.getId());
        if (null != book) {
            transaction.setBook(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            return transactionRepository.save(transaction).getTransactionId();
        } else {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error occurred while assigning book to student");
        }
    }

    public String returnTxn(int bookId, int studentId) {

        // Validations
        List<Book> availableBooks = bookService.search(SearchBookRequest.builder()
                .searchKey("id")
                .searchValue(String.valueOf(bookId))
                .operator("=")
                .build());

        if (availableBooks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        Book book = availableBooks.get(0);
        if (book.getStudent().getId() != studentId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Student student = studentService.get(studentId);

        // Initiate transaction
        Transaction returnTransaction = transactionRepository.save(Transaction.builder()
                .student(student)
                .book(book)
                .transactionType(TransactionType.RETURN)
                .transactionStatus(TransactionStatus.PENDING)
                .transactionId(UUID.randomUUID().toString())
                .build());

        TransactionStatus status = TransactionStatus.SUCCESS;
        try {
            List<Transaction> transactionList = transactionRepository
                    .findFirstByBookAndStudentAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(
                            book,
                            student,
                            TransactionType.ISSUE,
                            TransactionStatus.SUCCESS);
            if (transactionList.isEmpty()) {
                status = TransactionStatus.FAILED;
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue transaction details not found");
            }

            // Calculate fine;
            Transaction issueTransaction = transactionList.get(0);
            long diff = returnTransaction.getTransactionTime().getTime() - issueTransaction.getTransactionTime().getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            double fine = calculateFine(days);
            returnTransaction.setFine(fine);

            book = bookService.unassignBookFromStudent(bookId);
            if (null != book) {
                returnTransaction.setBook(book);
            } else {
                status = TransactionStatus.FAILED;
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Error occurred while returning book");
            }
        } finally {
            returnTransaction.setTransactionStatus(status);
            returnTransaction = transactionRepository.save(returnTransaction);
        }
        return returnTransaction.getTransactionId();
    }

    private double calculateFine(long days) {
        double fine = 0.0;
        double finePerDay = 1.0;
        while (days > 0) {
            if (days >= 7) {
                fine += 7 * finePerDay;
            } else {
                fine += days * finePerDay;
            }
            days -= 7;
            finePerDay++;
        }
        return fine;
    }
}
