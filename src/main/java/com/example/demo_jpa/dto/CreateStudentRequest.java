package com.example.demo_jpa.dto;

import com.example.demo_jpa.model.Student;
import com.example.demo_jpa.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String contact;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Student to() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        return Student.builder()
                .name(name)
                .contact(contact)
                .user(User.builder().password(password).username(username).build())
                .validity(calendar.getTime())
                .build();
    }
}
