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

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookAlreadyPresentException.class)
    public ResponseEntity<String> titleAlreadyPresent(BookAlreadyPresentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AuthorNamePresentException.class)
    public ResponseEntity<String> authorAlreadyPresent(AuthorNamePresentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException exception){
        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolations(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> exceptionSet = ex.getConstraintViolations();
        String errorMessages = exceptionSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}
