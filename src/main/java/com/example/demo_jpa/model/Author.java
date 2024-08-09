package com.example.demo_jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String country;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties({"author"})
    private List<Book> bookList;

    @CreationTimestamp
    private Date addedOn;
}
