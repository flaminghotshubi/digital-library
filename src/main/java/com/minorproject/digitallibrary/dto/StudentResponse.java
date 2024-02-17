package com.minorproject.digitallibrary.dto;

import java.util.Date;
import java.util.List;

import com.minorproject.digitallibrary.model.Book;
import com.minorproject.digitallibrary.model.Student;

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
public class StudentResponse {

	private int id;

	private String name;

	private String contact;

	private int numberOfBooksIssued;

	private Date validity;

	public static StudentResponse from(Student s) {
		StudentResponse student = null;
		if (null != s) {
			student = StudentResponse.builder().id(s.getId()).contact(s.getContact()).name(s.getName()).numberOfBooksIssued(getNumberOfBooks(s.getBooks())).validity(s.getValidity()).build();
		}
		return student;
	}
	
	private static int getNumberOfBooks(List<Book> bookList) {
		if(null == bookList) return 0;
		return bookList.size();
	}
}
