package com.minorproject.digitallibrary.dto;


import com.minorproject.digitallibrary.model.Author;

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
public class AuthorResponse {
	
	private int id;
	
	private String name;
	
	private String email;
	
	public static AuthorResponse from(Author a) {
		AuthorResponse response = null;
		if(null != a) {
			response = AuthorResponse.builder().id(a.getId()).name(a.getName()).email(a.getEmail()).build();
		}
		return response;
	}
	
}
