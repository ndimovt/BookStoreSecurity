package io.github.ndimovt.RelationTesting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Name;
import lombok.Data;

@Entity
@Data
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Name("id")
    private Long id;

    @Name("name")
    @NotBlank(message = "Author name can't be empty!")
    @Pattern(regexp = "[a-z A-Z]+$", message = "Author name must contain only letters!")
    private String name;

    @ManyToOne
    private Book books;
}
