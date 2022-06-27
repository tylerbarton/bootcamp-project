package com.perficient.pbcpuserservice.web.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Catches exceptions in the controller and returns a proper response
 *
 * @author tyler.barton
 * @version 1.0, 6/27/2022
 * @project PBCP-UserService
 */
@ControllerAdvice // Catch all exceptions in the controller
public class MvcExceptionHandler {
    /**
     * Catches invalid user id exceptions and returns a proper response
     * @param e the exception
     * @return a proper response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches constraint violations and returns a proper response
     * @param e the exception
     * @return a proper response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches all other exceptions and returns a proper response
     * @param e the exception
     * @return a proper response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
