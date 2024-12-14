package com.duocuc.users_srv.util.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void testHandleAccessDeniedException() {
    // Simular una excepción de acceso denegado
    AccessDeniedException exception = new AccessDeniedException("Access is denied");

    // Invocar el método del handler
    ResponseEntity<ErrorDetails> response = exceptionHandler.handleAccessDeniedException(exception);

    // Validar la respuesta
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Access Denied", response.getBody().getMessage());
    assertEquals("Access is denied", response.getBody().getDetails());
  }

  @Test
  void testHandleAuthenticationException() {
    // Simular una excepción de autenticación
    AuthenticationException exception = new AuthenticationException("Invalid credentials") {
    };

    // Invocar el método del handler
    ResponseEntity<ErrorDetails> response = exceptionHandler.handleAuthenticationException(exception);

    // Validar la respuesta
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Unauthorized", response.getBody().getMessage());
    assertEquals("Invalid credentials", response.getBody().getDetails());
  }

  @Test
  void testHandleGlobalException() {
    // Simular una excepción general
    Exception exception = new Exception("Unexpected error occurred");

    // Invocar el método del handler
    ResponseEntity<ErrorDetails> response = exceptionHandler.handleGlobalException(exception);

    // Validar la respuesta
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Unexpected error occurred", response.getBody().getMessage());
    assertEquals("Revise los logs del servidor", response.getBody().getDetails());
  }
}
