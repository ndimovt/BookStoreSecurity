package io.github.ndimovt.RelationTesting.controller;

import io.github.ndimovt.RelationTesting.model.Book;
import io.github.ndimovt.RelationTesting.model.BookDto;
import io.github.ndimovt.RelationTesting.service.BookService;
import io.github.ndimovt.RelationTesting.validator.NameConstraint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping("/book/add")
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book){
        bookService.insertBook(book);
        return ResponseEntity.ok("Success");
    }
    @GetMapping("/book/byAuthorName/{name}")
    public ResponseEntity<List<BookDto>> getBooks(@Valid @PathVariable @NameConstraint String name){
        List<BookDto> books = bookService.getBooksByAuthorName(name);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @DeleteMapping("/book/delete/{bookName}")
    public ResponseEntity<String> delete(@Valid @PathVariable @NameConstraint String bookName){
        if(bookService.deleteBook(bookName)){
            return ResponseEntity.ok("Book deleted");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
