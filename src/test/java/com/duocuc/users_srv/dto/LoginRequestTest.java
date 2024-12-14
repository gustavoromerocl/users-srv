package com.duocuc.users_srv.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class LoginRequestTest {

  @Test
  void testSetAndGetEmail() {
    // Crear instancia de LoginRequest
    LoginRequest loginRequest = new LoginRequest();

    // Establecer email
    String email = "test@example.com";
    loginRequest.setEmail(email);

    // Validar que getEmail devuelve el valor correcto
    assertEquals(email, loginRequest.getEmail());
  }

  @Test
  void testSetAndGetPassword() {
    // Crear instancia de LoginRequest
    LoginRequest loginRequest = new LoginRequest();

    // Establecer password
    String password = "password123";
    loginRequest.setPassword(password);

    // Validar que getPassword devuelve el valor correcto
    assertEquals(password, loginRequest.getPassword());
  }

  @Test
  void testDefaultValues() {
    // Crear instancia de LoginRequest sin inicializar valores
    LoginRequest loginRequest = new LoginRequest();

    // Validar que los valores predeterminados son null
    assertNull(loginRequest.getEmail());
    assertNull(loginRequest.getPassword());
  }
}
