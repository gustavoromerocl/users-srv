package com.duocuc.users_srv.util.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de errores 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "Access Denied",
            ex.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    // Manejo de errores 401
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "Unauthorized",
            ex.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    // Manejo de excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            "Revise los logs del servidor"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
