package io.github.ndimovt.RelationTesting.service;

import io.github.ndimovt.RelationTesting.exceptin.AuthorNamePresentException;
import io.github.ndimovt.RelationTesting.model.Author;
import io.github.ndimovt.RelationTesting.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void insertAuthor(Author author){
        if(authorRepository.existsByName(author.getName())){
            throw new AuthorNamePresentException("Author already exists!");
        }
        authorRepository.save(author);
    }
}
