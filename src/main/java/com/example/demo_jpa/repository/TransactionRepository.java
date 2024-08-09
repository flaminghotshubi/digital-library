package com.example.demo_jpa.repository;

import com.example.demo_jpa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findFirstByBookAndStudentAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc
            (Book book, Student student, TransactionType type,
             TransactionStatus transactionStatus);

}
