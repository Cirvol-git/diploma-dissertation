package diploma.dissertation.d64Restorator.controller;

import diploma.dissertation.d64Restorator.exception.DifferentDiscIdException;
import diploma.dissertation.d64Restorator.exception.NotEnoughSpaceException;
import diploma.dissertation.d64Restorator.exception.UnsupportedFileTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice(basePackageClasses = RootController.class)
public class ExceptionHandlerController {

    @ExceptionHandler(value = {DifferentDiscIdException.class, NotEnoughSpaceException.class, UnsupportedFileTypeException.class})
    public ResponseEntity<String> handleDifferentDiscIdException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<String> handleIOException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
