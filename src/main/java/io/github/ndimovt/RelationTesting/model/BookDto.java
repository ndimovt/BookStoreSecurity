package io.github.ndimovt.RelationTesting.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class BookDto {
    private String title;
    private String genre;
    private List<String> authors;
}
