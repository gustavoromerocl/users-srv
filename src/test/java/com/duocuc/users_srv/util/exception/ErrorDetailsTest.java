package com.duocuc.users_srv.util.exception;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ErrorDetailsTest {

  @Test
  void testConstructorAndGetters() {
    LocalDateTime timestamp = LocalDateTime.now();
    String message = "Error occurred";
    String details = "Details about the error";

    // Crear instancia de ErrorDetails
    ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details);

    // Validar valores asignados
    assertEquals(timestamp, errorDetails.getTimestamp());
    assertEquals(message, errorDetails.getMessage());
    assertEquals(details, errorDetails.getDetails());
  }

  @Test
  void testToStringContainsFields() {
    LocalDateTime timestamp = LocalDateTime.now();
    String message = "Error message";
    String details = "Details about the error";

    // Crear instancia de ErrorDetails
    ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details);

    // Validar que toString incluye información relevante
    String result = errorDetails.toString();
    assertTrue(result.contains("timestamp=" + timestamp.toString()));
    assertTrue(result.contains("message='Error message'"));
    assertTrue(result.contains("details='Details about the error'"));
  }
}
