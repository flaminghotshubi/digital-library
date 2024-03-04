package com.minorproject.digitallibrary;

import com.minorproject.digitallibrary.dto.StudentResponse;
import com.minorproject.digitallibrary.model.Student;
import com.minorproject.digitallibrary.repository.StudentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import com.minorproject.digitallibrary.service.StudentService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class) // how you want to execute your test cases
public class StudentServiceTest {
	
	@InjectMocks // similar to autowired
	StudentService studentService;

    @Mock
    StudentRepository studentRepo;

	@Test
	public void getById_test() {
        Student s = Student.builder().id(99).name("dummy").contact("100").build();
        Mockito.when(studentRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(s));
		StudentResponse res = studentService.getById(99);
		Assert.assertEquals(s.getId(), res.getId());
		Mockito.verify(studentRepo, Mockito.times(1)).findById(99);
	}

}
