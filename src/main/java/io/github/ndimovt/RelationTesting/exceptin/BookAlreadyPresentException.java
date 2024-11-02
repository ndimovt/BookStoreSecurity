package io.github.ndimovt.RelationTesting.exceptin;

public class BookAlreadyPresentException extends RuntimeException{
    public BookAlreadyPresentException(String message) {
        super(message);
    }
}
