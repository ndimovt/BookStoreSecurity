package io.github.ndimovt.RelationTesting.repository;

import io.github.ndimovt.RelationTesting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface UserRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Returns User object with a given name
     * @param username String object
     * @return User object
     */
    Optional<User> findByUsername(String username);
}
