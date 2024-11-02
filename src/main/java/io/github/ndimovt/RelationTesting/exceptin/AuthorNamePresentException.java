package io.github.ndimovt.RelationTesting.exceptin;

public class AuthorNamePresentException extends RuntimeException{
    public AuthorNamePresentException(String message) {
        super(message);
    }
}
