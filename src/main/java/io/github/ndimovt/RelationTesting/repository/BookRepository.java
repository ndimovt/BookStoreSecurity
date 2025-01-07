package io.github.ndimovt.RelationTesting.repository;

import io.github.ndimovt.RelationTesting.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The BookRepository interface
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Returns Book object by a given name
     * @param name String object
     * @return Book object
     */
    Optional<Book> findByTitle(String name);

    /**
     * Joins author and book table based on ids
     * @param name String object
     * @return List of object/s
     */
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

    /**
     * Deletes author record from database by a given name
     * @param title String object
     */
    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM author
            WHERE books_id = (SELECT id FROM book WHERE title = ?)
            """, nativeQuery = true)
    void deleteAuthorByBookTitle(String title);

    /**
     * Deletes book from database by a given name
     * @param name String object
     * @return Int primitive
     */
    @Modifying
    @Transactional
    @Query(value = """
             DELETE FROM book
             WHERE title = ?
            """,
    nativeQuery = true)
    int deleteByBookName(String name);

    /**
     * Updates book title
     * @param newName String object
     * @param oldName String object
     * @return Int primitive
     */
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE book
            SET title = ?
            WHERE title = ?
            """, nativeQuery = true)
    int updateBookTitle(String newName, String oldName);
}
