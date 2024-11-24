package io.github.ndimovt.RelationTesting.handler;

import io.github.ndimovt.RelationTesting.exceptin.AuthorNamePresentException;
import io.github.ndimovt.RelationTesting.exceptin.BookAlreadyPresentException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The class GlobalExceptionHandler
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Returns ResponseEntity with message and response code if BookAlreadyPresentException is thrown
     * @param exception BookAlreadyPresentException object
     * @return ResponseEntity object
     */
    @ExceptionHandler(BookAlreadyPresentException.class)
    public ResponseEntity<String> titleAlreadyPresent(BookAlreadyPresentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Returns ResponseEntity with message and response code if AuthorNamePresentException is thrown
     * @param exception AuthorNamePresentException object
     * @return ResponseEntity object
     */
    @ExceptionHandler(AuthorNamePresentException.class)
    public ResponseEntity<String> authorAlreadyPresent(AuthorNamePresentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Add all error's messages to a list, and returns messages corresponding to a given error
     * @param exception MethodArgumentNotValidException object
     * @return ResponseEntity object
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException exception){
        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Add all exception's messages to a set, and returns messages corresponding to a given exception
     * @param ex ConstraintViolationException object
     * @return ResponseEntity object
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolations(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> exceptionSet = ex.getConstraintViolations();
        String errorMessages = exceptionSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}
