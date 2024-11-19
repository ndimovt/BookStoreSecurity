package io.github.ndimovt.RelationTesting.controller;

import io.github.ndimovt.RelationTesting.model.Book;
import io.github.ndimovt.RelationTesting.model.dtos.BookDto;
import io.github.ndimovt.RelationTesting.service.BookService;
import io.github.ndimovt.RelationTesting.validator.NameConstraint;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book/add")
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book, @AuthenticationPrincipal UserDetails userDetails){
        if(userDetails != null){
            bookService.insertBook(book);
            return ResponseEntity.ok("Record successfully added!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
    }
    @GetMapping("/book/byAuthorName/{name}")
    public ResponseEntity<List<BookDto>> getBooks(@Valid @PathVariable @NameConstraint String name){
        List<BookDto> books = bookService.getBooksByAuthorName(name);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @DeleteMapping("/book/delete/{bookName}")
    public ResponseEntity<String> delete(@Valid @PathVariable @NameConstraint String bookName, @AuthenticationPrincipal UserDetails userDetails){
        if(bookService.deleteBook(bookName)){
            return ResponseEntity.ok("Book deleted!");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
