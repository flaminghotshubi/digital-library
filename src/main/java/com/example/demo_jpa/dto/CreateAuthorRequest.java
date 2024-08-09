package com.example.demo_jpa.dto;

import com.example.demo_jpa.model.Author;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuthorRequest {

    @NotBlank
    private String name;

    private String country;

    @NotNull
    @Email
    private String email;

    public Author to() {
        return Author.builder().name(name).country(country).email(email).build();
    }
}
