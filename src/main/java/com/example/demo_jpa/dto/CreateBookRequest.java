package com.example.demo_jpa.dto;

import com.example.demo_jpa.model.Book;
import com.example.demo_jpa.model.Genre;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {

    @NotBlank
    private String name;

    private Genre genre;

    private Integer pages;

    @NotNull
    @Valid
    private CreateAuthorRequest author;

    public Book to() {
        return Book.builder().name(name).genre(genre).pages(pages).author(author.to()).build();
    }
}
