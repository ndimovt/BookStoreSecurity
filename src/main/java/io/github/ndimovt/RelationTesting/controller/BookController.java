package io.github.ndimovt.RelationTesting.controller;

import io.github.ndimovt.RelationTesting.model.Book;
import io.github.ndimovt.RelationTesting.model.dtos.BookDto;
import io.github.ndimovt.RelationTesting.service.BookService;
import io.github.ndimovt.RelationTesting.validator.NameConstraint;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The class BookController
 */
@RestController
@Validated
public class BookController {
    private final BookService bookService;

    /**
     * Instantiates BookController
     * @param bookService BookService object
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Inserts Book record in database
     * @param book Book object
     * @return ResponseEntity object
     */
    @PostMapping("/book/add")
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book){
        bookService.insertBook(book);
        return ResponseEntity.ok("Record successfully added!");
    }

    /**
     * Searches for a record based on a name parameter. If there are multiple records for a name all are returned in a List
     * @param name String object
     * @return ResponseEntity object
     */
    @GetMapping("/book/byAuthorName/{name}")
    public ResponseEntity<List<BookDto>> getBooks(@Valid @PathVariable @NameConstraint String name){
        List<BookDto> books = bookService.getBooksByAuthorName(name);
        if(books != null && !books.isEmpty()){
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes book using provided name parameter
     * @param bookName String object
     * @return ResponseEntity object
     */
    @DeleteMapping("/book/delete/{bookName}")
    public ResponseEntity<String> delete(@Valid @PathVariable @NameConstraint String bookName){
        if(bookService.deleteBook(bookName)){
            return ResponseEntity.ok("Book deleted successfully!");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
