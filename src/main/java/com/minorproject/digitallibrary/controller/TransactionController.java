package com.minorproject.digitallibrary.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minorproject.digitallibrary.dto.ResponseObject;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@PostMapping
	public ResponseObject issueTxn(@RequestParam("studentId") String studentId, @RequestParam("bookId") String bookId) {
		ResponseObject ret = null;
		try {
			
		} catch(Exception e) {
			
		}
		return ret;
	}
	
}
