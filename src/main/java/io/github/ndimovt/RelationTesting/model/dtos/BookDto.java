package io.github.ndimovt.RelationTesting.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {
    private String title;
    private String genre;
    private List<String> authors;
}
