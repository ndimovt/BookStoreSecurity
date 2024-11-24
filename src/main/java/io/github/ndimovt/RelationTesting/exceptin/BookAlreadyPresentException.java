package io.github.ndimovt.RelationTesting.exceptin;

/**
 * The class BookAlreadyPresentException
 */
public class BookAlreadyPresentException extends RuntimeException{
    /**
     * Instantiating BookAlreadyPresentException
     * @param message String object
     */
    public BookAlreadyPresentException(String message) {
        super(message);
    }
}
