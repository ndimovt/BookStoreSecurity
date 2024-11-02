package io.github.ndimovt.RelationTesting.repository;

import io.github.ndimovt.RelationTesting.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
    boolean existsByName(String name);
}
