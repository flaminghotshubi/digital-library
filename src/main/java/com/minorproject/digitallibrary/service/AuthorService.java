package com.minorproject.digitallibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minorproject.digitallibrary.model.Author;
import com.minorproject.digitallibrary.repository.AuthorRepository;

@Service
public class AuthorService {
	
	@Autowired
	AuthorRepository authorRepo;
	
	public Author createOrGet(Author authorDetails) {
		Author authorObj = authorRepo.findByEmail(authorDetails.getEmail());
		if(null == authorObj) {
			authorObj = authorRepo.save(authorDetails);
		}
		return authorObj;
	}
	
}
