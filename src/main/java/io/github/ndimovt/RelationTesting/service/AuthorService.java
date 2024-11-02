package io.github.ndimovt.RelationTesting.service;

import io.github.ndimovt.RelationTesting.exceptin.AuthorNamePresentException;
import io.github.ndimovt.RelationTesting.model.Author;
import io.github.ndimovt.RelationTesting.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public void insertAuthor(Author author){
        if(authorRepository.existsByName(author.getName())){
            throw new AuthorNamePresentException("Author already exists!");
        }
        authorRepository.save(author);
    }
}
