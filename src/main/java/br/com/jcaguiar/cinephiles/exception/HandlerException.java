package br.com.jcaguiar.cinephiles.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
public class HandlerException {

    //TODO: FIX!
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> mainHandler(RuntimeException exception, WebRequest request) {
        String message = "DEFAULT ERROR MESSAGE";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        switch(exception.getCause().getClass().getSimpleName()) { //TODO: WORKING??!
            case "NoSuchElementException" -> {
                message = "Sorry. We couldn't find what you are looking for...";
                status = HttpStatus.NOT_FOUND;
            }
            case "ConstraintViolationException" -> {
                message = "Your request violates one or more Database rules. Example: duplicated unique keys.";
                status = HttpStatus.CONFLICT;
            }
            case "DuplicatedRecordException" -> {
                message = "The record you want to save in the Database already exist.";
                status = HttpStatus.CONFLICT;
            }
        };
        return new ResponseEntity<>(message, status);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> dataException(DataAccessException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
