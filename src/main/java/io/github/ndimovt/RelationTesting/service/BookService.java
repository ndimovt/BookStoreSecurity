package io.github.ndimovt.RelationTesting.service;

import io.github.ndimovt.RelationTesting.exceptin.BookAlreadyPresentException;
import io.github.ndimovt.RelationTesting.model.Author;
import io.github.ndimovt.RelationTesting.model.Book;
import io.github.ndimovt.RelationTesting.model.BookDto;
import io.github.ndimovt.RelationTesting.repository.AuthorRepository;
import io.github.ndimovt.RelationTesting.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    public void insertBook(Book book){
        existingTitle(book);
        Book bookInfo = new Book();
        bookInfo.setTitle(book.getTitle());
        bookInfo.setGenre(book.getGenre());
        Set<Author> authorsList = book.getAuthors().stream().map(author -> {
            return authorRepository.findByName(author.getName())
                    .orElseGet(() -> {
                        Author authors = new Author();
                        authors.setName(author.getName());
                        return authorRepository.save(authors);
                    });
        }).collect(Collectors.toSet());
        bookInfo.setAuthors(authorsList);
        bookRepository.save(bookInfo);
    }
    private void existingTitle(Book book){
        Optional<Book> title = bookRepository.findByTitle(book.getTitle());
        if(title.isPresent()){
            throw new BookAlreadyPresentException("Book title is already present!");
        }
    }
    public List<BookDto> getBooksByAuthorName(String name){
        List<Object[]> objects = bookRepository.getBooksByAuthorName(name);
        List<BookDto> books = new ArrayList<>();
        for(Object[] row : objects){
            String title = String.valueOf(row[0]);
            String genre = String.valueOf(row[1]);
            String author = String.valueOf(row[2]);

            List<String> authors = Arrays.asList(author.split(", "));

            books.add(new BookDto(title, genre, authors));
        }
        return books;
    }
    public boolean deleteBook(String bookName){
        bookRepository.deleteAuthorByBookTitle(bookName);
        return bookRepository.deleteByBookName(bookName) > 0;
    }
}
