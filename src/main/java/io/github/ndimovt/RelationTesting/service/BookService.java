package io.github.ndimovt.RelationTesting.service;

import io.github.ndimovt.RelationTesting.exceptin.BookAlreadyPresentException;
import io.github.ndimovt.RelationTesting.model.Author;
import io.github.ndimovt.RelationTesting.model.Book;
import io.github.ndimovt.RelationTesting.model.dtos.BookDto;
import io.github.ndimovt.RelationTesting.repository.AuthorRepository;
import io.github.ndimovt.RelationTesting.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The class BookService
 */
@Service
public class BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    /**
     * Instantiating BookService
     * @param authorRepository AuthorRepository object
     * @param bookRepository BookRepository object
     */

    public BookService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Insert Book object into database. Checks for existing record. May throw exception is such book already exists.
     * @param book Book object
     */
    public void insertBook(Book book){
        existingTitle(book);
        Book bookInfo = new Book();
        bookInfo.setTitle(book.getTitle());
        bookInfo.setGenre(book.getGenre());
        Set<Author> authors = book.getAuthors();
        bookInfo.setAuthors(authors);
        authors.forEach(authorRepository::save);
        bookRepository.save(bookInfo);
    }
    private void existingTitle(Book book){
        Optional<Book> title = bookRepository.findByTitle(book.getTitle());
        if(title.isPresent()){
            throw new BookAlreadyPresentException("Book title is already present!");
        }
    }

    /**
     * Returns List of BookDto containing books based on author name criteria.
     * @param name String object
     * @return List object
     */
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

    /**
     * Deletes book by book name. Returns true if there is one or more deleted records.
     * @param bookName String object
     * @return boolean primitive
     */
    public boolean deleteBook(String bookName){
        bookRepository.deleteAuthorByBookTitle(bookName);
        return bookRepository.deleteByBookName(bookName) > 0;
    }

    /**
     * Updates existing book title with a new one
     * @param newName String object
     * @param oldName String object
     * @return Int primitive
     */
    public int changeTitle(String newName, String oldName){
        return bookRepository.updateBookTitle(newName, oldName);
    }
}
