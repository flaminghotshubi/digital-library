package com.minorproject.digitallibrary.dto;

import com.minorproject.digitallibrary.model.Book;
import com.minorproject.digitallibrary.model.Genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
	
	private int id;
	
	private String name;
	
	private Genre genre;

	private Integer pages;
	
	private AuthorResponse author;
	
	private StudentResponse student;
	
	public static BookResponse from(Book b) {
		BookResponse response = null;
		if(null != b) {
			BookResponse.builder().id(b.getId()).name(b.getName()).genre(b.getGenre()).pages(b.getPages()).author(AuthorResponse.from(b.getAuthor())).student(StudentResponse.from(b.getStudent())).build();
		}
		return response;
	}
	
}
