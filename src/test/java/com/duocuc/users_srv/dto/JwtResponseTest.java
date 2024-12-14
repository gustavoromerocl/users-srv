package com.duocuc.users_srv.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class JwtResponseTest {

  @Test
  void testConstructorAndGetToken() {
    // Crear instancia con el constructor
    String expectedToken = "test-jwt-token";
    JwtResponse jwtResponse = new JwtResponse(expectedToken);

    // Validar que el token fue asignado correctamente
    assertEquals(expectedToken, jwtResponse.getToken());
  }

  @Test
  void testSetToken() {
    // Crear instancia con un token inicial
    JwtResponse jwtResponse = new JwtResponse("initial-token");

    // Asignar un nuevo token
    String newToken = "new-jwt-token";
    jwtResponse.setToken(newToken);

    // Validar que el token fue actualizado correctamente
    assertEquals(newToken, jwtResponse.getToken());
  }

  @Test
  void testTokenFieldIsNullByDefault() {
    // Crear instancia con el constructor vacío
    JwtResponse jwtResponse = new JwtResponse(null);

    // Validar que el token es null
    assertNull(jwtResponse.getToken());
  }

  @Test
  void testToString() {
    // Crear instancia y validar la salida del método toString
    String token = "test-jwt-token";
    JwtResponse jwtResponse = new JwtResponse(token);

    String expectedToString = "JwtResponse{token='test-jwt-token'}";
    assertEquals(expectedToString, jwtResponse.toString());
  }
}
