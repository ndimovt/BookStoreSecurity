package io.github.ndimovt.RelationTesting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Name;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Name("id")
    private Long id;

    @Name("title")
    @NotBlank(message = "Title can't be empty!")
    @Pattern(regexp = "^[a-zA-Z'\\s]+${5,}", message = "Title must contain at least 5 characters and only letters, spaces, or '")
    private String title;

    @Name("genre")
    @NotBlank(message = "Genre can't be empty!")
    @Pattern(regexp = "^[a-zA-Z,\\s]{5,}$", message = "Genre must contain at least 5 characters and only letters, spaces, or commas!")
    private String genre;
    @OneToMany(targetEntity = Author.class, orphanRemoval = true)
    @JoinColumn(name = "books_id", referencedColumnName = "id")
    private Set<Author> authors;

    public Book() {
    }
}
