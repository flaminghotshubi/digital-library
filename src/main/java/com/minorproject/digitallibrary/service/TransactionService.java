package com.minorproject.digitallibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minorproject.digitallibrary.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepository transactionRepo;
	
	public String issue(String bookId, String studentId) throws Exception {
		String txnId = null;
		
		return txnId;
	}
	
}
