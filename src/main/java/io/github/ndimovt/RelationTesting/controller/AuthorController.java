package io.github.ndimovt.RelationTesting.controller;


import io.github.ndimovt.RelationTesting.model.Author;
import io.github.ndimovt.RelationTesting.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class AuthorService
 */
@RestController
public class AuthorController {
    private final AuthorService authorService;

    /**
     * Instantiates AuthorController
     * @param authorService AuthorService object
     */

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Allows adding author record to database. May throw exception if author validation fails.
     * @param author Author object
     * @return ResponseEntity instance
     */
    @PostMapping("/author/add")
    public ResponseEntity<String> addAuthor(@Valid @RequestBody Author author){
        authorService.insertAuthor(author);
        return ResponseEntity.ok("Author inserted successfully!");
    }
}
