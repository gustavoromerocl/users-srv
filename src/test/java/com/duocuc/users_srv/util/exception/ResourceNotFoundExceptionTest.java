package com.duocuc.users_srv.util.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class ResourceNotFoundExceptionTest {

  @Test
  void testExceptionMessage() {
    String errorMessage = "Resource not found";

    // Crear instancia de la excepción
    ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

    // Validar que el mensaje se asignó correctamente
    assertNotNull(exception);
    assertEquals(errorMessage, exception.getMessage());
  }
}
