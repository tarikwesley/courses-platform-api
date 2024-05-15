package com.project.coursesplatformapi.config.exception;

import com.project.coursesplatformapi.exception.CourseException;
import com.project.coursesplatformapi.exception.RegistrationException;
import com.project.coursesplatformapi.exception.ReviewException;
import com.project.coursesplatformapi.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> genericExceptionHandler(Exception ex) {
        HttpStatus status = INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), status), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        HttpStatus status = BAD_REQUEST;
        return new ResponseEntity<>(new ErrorDTO(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), status), status);
    }

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<Object> userExceptionHandler(UserException ex) {
        HttpStatus status = NOT_FOUND;
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), status), status);
    }

    @ExceptionHandler(CourseException.class)
    protected ResponseEntity<Object> courseExceptionHandler(CourseException ex) {
        HttpStatus status = NOT_FOUND;
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), status), status);
    }

    @ExceptionHandler(RegistrationException.class)
    protected ResponseEntity<Object> registrationExceptionHandler(RegistrationException ex) {
        HttpStatus status = NOT_FOUND;
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), status), status);
    }

    @ExceptionHandler(ReviewException.class)
    protected ResponseEntity<Object> reviewExceptionHandler(ReviewException ex) {
        HttpStatus status = BAD_REQUEST;
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), status), status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedExceptionHandler(AccessDeniedException ex) {
        HttpStatus status = FORBIDDEN;
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), status), status);
    }
}