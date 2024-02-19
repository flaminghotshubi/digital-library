package com.minorproject.digitallibrary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minorproject.digitallibrary.dto.BookRequest;
import com.minorproject.digitallibrary.dto.BookResponse;
import com.minorproject.digitallibrary.model.Author;
import com.minorproject.digitallibrary.model.Book;
import com.minorproject.digitallibrary.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	AuthorService authorService;

	public BookResponse create(BookRequest bookRequest) {
		Book book = bookRequest.to();
		Author author = authorService.createOrGet(book.getAuthor());
		book.setAuthor(author);
		return BookResponse.from(bookRepo.save(book));
	}

	public void delete(int id) {
		bookRepo.deleteById(id);
	}
	
	public List<BookResponse> getAll() {
		List<Book> bookList = bookRepo.findAll();
		List<BookResponse> ret = new ArrayList<>(bookList.size());
		bookList.forEach(book -> ret.add(BookResponse.from(book)));
		return ret;
	}
	
	public BookResponse getBookById(int id) {
		Book b = bookRepo.findById(id).orElse(null);
		return BookResponse.from(b);
	}
	
//	public List<Book> search(String searchKey, String searchValue, String operator) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
