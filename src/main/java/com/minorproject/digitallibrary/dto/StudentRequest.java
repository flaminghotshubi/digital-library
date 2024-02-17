package com.minorproject.digitallibrary.dto;

import java.util.Date;

import com.minorproject.digitallibrary.model.Student;

import jakarta.validation.constraints.NotBlank;
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
public class StudentRequest {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String contact;
	
	public Student to() {
		return Student.builder().name(name).contact(contact).validity(new Date(System.currentTimeMillis() + 31536000000l)).build();
	}
}
