package io.github.ndimovt.RelationTesting.repository;

import io.github.ndimovt.RelationTesting.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String name);
    @Query(value = """
            SELECT b.title, b.genre, STRING_AGG(a.name, ', ') AS authors
            FROM Book b
            JOIN combine c
                ON c.book_id = b.id
            JOIN Author a
                ON a.id = c.author_id
            WHERE a.name = ?
            GROUP BY b.title, b.genre
            """,
    nativeQuery = true)
    List<Object[]> getBooksByAuthorName(String name);
    @Modifying
    @Transactional
    @Query(value = """
           DELETE FROM combine c
           USING book 
           WHERE c.book_id = book.id
           AND book.title = ?
            """,
    nativeQuery = true)
    int deleteByBookName(String name);
}
