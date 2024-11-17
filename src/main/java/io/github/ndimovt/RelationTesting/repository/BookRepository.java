package io.github.ndimovt.RelationTesting.repository;

import io.github.ndimovt.RelationTesting.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String name);
    @Query(value = """
            SELECT b.title, b.genre, STRING_AGG(a.name, ', ')
            FROM book b
            JOIN author a
                ON a.books_id = b.id
            WHERE a.name = ?
            GROUP BY b.title, b.genre
            """,
    nativeQuery = true)
    List<Object[]> getBooksByAuthorName(String name);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM author
            WHERE books_id = (SELECT id FROM book WHERE title = ?)
            """, nativeQuery = true)
    void deleteAuthorByBookTitle(String title);

    @Modifying
    @Transactional
    @Query(value = """
             DELETE FROM book
             WHERE title = ?
            """,
    nativeQuery = true)
    int deleteByBookName(String name);
}
