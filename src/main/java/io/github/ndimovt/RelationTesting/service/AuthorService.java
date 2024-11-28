package io.github.ndimovt.RelationTesting.service;

import io.github.ndimovt.RelationTesting.exceptin.AuthorNamePresentException;
import io.github.ndimovt.RelationTesting.model.Author;
import io.github.ndimovt.RelationTesting.repository.AuthorRepository;
import org.springframework.stereotype.Service;

/**
 * The class AuthorService
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Instantiating AuthorService
     * @param authorRepository AuthorRepository object
     */

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Inserts Author object into database. May throw exception if Author with such name already exists
     * @param author Author object
     */
    public void insertAuthor(Author author){
        if(authorRepository.existsByName(author.getName())){
            throw new AuthorNamePresentException("Author already exists!");
        }
        authorRepository.save(author);
    }
}
