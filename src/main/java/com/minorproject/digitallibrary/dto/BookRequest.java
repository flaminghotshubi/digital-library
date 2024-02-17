package com.minorproject.digitallibrary.dto;

import com.minorproject.digitallibrary.model.Author;
import com.minorproject.digitallibrary.model.Book;
import com.minorproject.digitallibrary.model.Genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BookRequest {

	@NotBlank
	private String name;

	@NotNull
	private Genre genre;

	private Integer pages;

	@NotBlank
	private String authorName;

	private String country;
	
	@NotBlank
	private String email;

	public Book to() {
		return Book.builder().name(name).genre(genre).pages(pages)
				.author(Author.builder().name(authorName).country(country).email(email).build()).build();
	}
}
