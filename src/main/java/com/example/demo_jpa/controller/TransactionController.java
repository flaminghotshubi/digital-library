package com.example.demo_jpa.controller;

import com.example.demo_jpa.model.TransactionType;
import com.example.demo_jpa.service.TransactionService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Validated
public class TransactionController {

    // issuance
    // return

    // initiateTxn () -> book id, student id, type of txn

    /**
     * Issuance
     *  1. Create a txn with pending status
     *  2. Get the book details and student details
     *  3. Validation
     *  4. Assign the book to that particular student // update book set student_id = ?
     *  5. Update the txn accordingly with status as SUCCESS or FAILED
     */


    /**
     * Return
     * 1. Create a txn with pending status
     * 2. Check the due date and correspondingly evaluate the fine
     * 3. Unassign the book from student // UPDATE BOOK table set student_id = null
     * 4. Update the txn accordingly with status as SUCCESS or FAILED
     */

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public String initiateTxn(
            @RequestParam("name") @NotBlank String name,
            @RequestParam("studentId") @Min(1) int id) {
        return transactionService.issueTxn(name, id);
    }

    @PostMapping("/return")
    public String returnTxn(
            @RequestParam("bookId") @Min(1) int bookId,
            @RequestParam("studentId") @Min(1) int studentId) {
        return transactionService.returnTxn(bookId, studentId);
    }
}
