package com.project.coursesplatformapi.config.exception;

import com.project.coursesplatformapi.exception.CourseException;
import com.project.coursesplatformapi.exception.RegistrationException;
import com.project.coursesplatformapi.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> genericExceptionHandler(Exception ex) {
        HttpStatus status = INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), status), status);
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
}
