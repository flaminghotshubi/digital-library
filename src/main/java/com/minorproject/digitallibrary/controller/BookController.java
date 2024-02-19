package com.minorproject.digitallibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minorproject.digitallibrary.dto.BookRequest;
import com.minorproject.digitallibrary.dto.BookResponse;
import com.minorproject.digitallibrary.dto.ResponseObject;
import com.minorproject.digitallibrary.dto.ResponseStatus;
import com.minorproject.digitallibrary.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;
	
	@PostMapping("")
	public ResponseObject createBook(@RequestBody @Valid BookRequest createBookRequest) {
		ResponseObject ret;
		try {
			ret = ResponseObject.builder().status(ResponseStatus.SUCCESS).body(bookService.create(createBookRequest)).message("Book created successfully!").build();
		} catch(Exception e) {
			ret = ResponseObject.builder().status(ResponseStatus.FAILED).message(e.getMessage()).build();
		}
		return ret;
	}
	
	@DeleteMapping("/{bookId}")
	public ResponseObject deleteBook(@PathVariable("bookId") int id) {
		ResponseObject ret;
		try {
			bookService.delete(id);
			ret = ResponseObject.builder().status(ResponseStatus.SUCCESS).message("Book deleted successfully!").build();
		} catch(Exception e) {
			ret = ResponseObject.builder().status(ResponseStatus.FAILED).message(e.getMessage()).build();
		}
		return ret;
	}
	
	@GetMapping("/all")
	public ResponseObject getAllBooks() {
		ResponseObject ret;
		try {
			ret = ResponseObject.builder().status(ResponseStatus.SUCCESS).message("All books fetched successfully!").body(bookService.getAll()).build();
		} catch(Exception e) {
			ret = ResponseObject.builder().status(ResponseStatus.FAILED).message(e.getMessage()).build();
		}
		return ret;
	}
	
	@GetMapping("/{bookId}")
	public ResponseObject getBookById(@PathVariable("bookId") int id) {
		ResponseObject ret;
		try {
			BookResponse book = bookService.getBookById(id);
			if(null == book) {
				throw new Exception("Book details not found!");
			}
			ret = ResponseObject.builder().status(ResponseStatus.SUCCESS).message("Book details fetched successfully!").body(book).build();
			
		} catch(Exception e) {
			ret = ResponseObject.builder().status(ResponseStatus.FAILED).message(e.getMessage()).build();
		}
		return ret;
	}
	 
//	@GetMapping("/search")
//	public List<Book> searchBooks(@RequestParam("searchKey") String searchKey, @RequestParam("searchValue") String searchValue, @RequestParam("operator") String operator) {
//		return bookService.search(searchKey, searchValue, operator);
//	}
}
