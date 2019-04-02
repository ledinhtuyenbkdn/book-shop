package com.ledinhtuyenbkdn.bookshop.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String title;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new HashSet<>();

    @Past
    private LocalDate publishDate;

    private String publisher;

    @Positive
    private Integer numPage;
}
