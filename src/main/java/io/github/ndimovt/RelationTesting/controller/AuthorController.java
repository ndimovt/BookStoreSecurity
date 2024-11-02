package io.github.ndimovt.RelationTesting.controller;


import io.github.ndimovt.RelationTesting.model.Author;
import io.github.ndimovt.RelationTesting.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @PostMapping("/author/add")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Author author){
        authorService.insertAuthor(author);
        return ResponseEntity.ok("Success");
    }
}
