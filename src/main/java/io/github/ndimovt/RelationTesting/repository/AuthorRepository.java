package io.github.ndimovt.RelationTesting.repository;

import io.github.ndimovt.RelationTesting.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface AuthorRepository
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    /**
     * Finds Author record by a given name
     * @param name String object
     * @return Author object
     */
    Optional<Author> findByName(String name);

    /**+
     * Check for existing author by a name
     * @param name String object
     * @return Boolean primitive
     */
    boolean existsByName(String name);
}
