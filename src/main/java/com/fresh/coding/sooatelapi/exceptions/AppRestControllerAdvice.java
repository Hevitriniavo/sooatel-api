package com.fresh.coding.sooatelapi.exceptions;

import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler(HttpNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError<String> handleNotFoundException(HttpNotFoundException ex) {
        return new AppError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(HttpBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError<String> handleBadRequestException(HttpBadRequestException ex) {
        return new AppError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AppError<String> handleUnauthorizedException(AuthenticationException ex) {
        return new AppError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.UNAUTHORIZED.value()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppError<String> handleForbiddenException(AccessDeniedException ex) {
        return new AppError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.FORBIDDEN.value()
        );
    }

    @ExceptionHandler(HttpInternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError<String> handleInternalServerException(HttpInternalServerException ex) {
        return new AppError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new AppError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        var errors = new HashMap<String, String>();
        for (var violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return new AppError<>(
                errors,
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new AppError<>(
                errors,
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError<String> handleMessagingException(MessagingException ex) {
        return new AppError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError<String> handleAllExceptions(Exception ex) {
        return new AppError<>(
                ex.getMessage(),
                LocalDate.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}
