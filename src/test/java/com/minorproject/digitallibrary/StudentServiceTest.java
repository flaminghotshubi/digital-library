package com.minorproject.digitallibrary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import com.minorproject.digitallibrary.service.StudentService;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class) // how you want to execute your test cases
public class StudentServiceTest {
	
	@InjectMocks
	StudentService studentService;
	
	@Test
	public void getById_test() {
		studentService.getById(5);
	}

}
