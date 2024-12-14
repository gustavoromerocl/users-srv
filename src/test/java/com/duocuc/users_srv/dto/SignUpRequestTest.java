package com.duocuc.users_srv.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class SignUpRequestTest {

  @Test
  void testSetAndGetUsername() {
    // Crear instancia de SignUpRequest
    SignUpRequest signUpRequest = new SignUpRequest();

    // Establecer username
    String username = "testuser";
    signUpRequest.setUsername(username);

    // Validar que getUsername devuelve el valor correcto
    assertEquals(username, signUpRequest.getUsername());
  }

  @Test
  void testSetAndGetPassword() {
    // Crear instancia de SignUpRequest
    SignUpRequest signUpRequest = new SignUpRequest();

    // Establecer password
    String password = "password123";
    signUpRequest.setPassword(password);

    // Validar que getPassword devuelve el valor correcto
    assertEquals(password, signUpRequest.getPassword());
  }

  @Test
  void testSetAndGetEmail() {
    // Crear instancia de SignUpRequest
    SignUpRequest signUpRequest = new SignUpRequest();

    // Establecer email
    String email = "test@example.com";
    signUpRequest.setEmail(email);

    // Validar que getEmail devuelve el valor correcto
    assertEquals(email, signUpRequest.getEmail());
  }

  @Test
  void testDefaultValues() {
    // Crear instancia sin inicializar valores
    SignUpRequest signUpRequest = new SignUpRequest();

    // Validar que los valores predeterminados son null
    assertNull(signUpRequest.getUsername());
    assertNull(signUpRequest.getPassword());
    assertNull(signUpRequest.getEmail());
  }
}
