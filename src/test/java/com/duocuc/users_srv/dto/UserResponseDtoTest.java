package com.duocuc.users_srv.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class UserResponseDtoTest {

  @Test
  void testConstructorAndGetters() {
    // Datos simulados
    Long id = 1L;
    String name = "Test User";
    String email = "test@example.com";
    String role = "ADMIN";

    // Crear instancia usando el constructor
    UserResponseDto userResponse = new UserResponseDto(id, name, email, role);

    // Validar que los getters devuelvan los valores correctos
    assertEquals(id, userResponse.getId());
    assertEquals(name, userResponse.getName());
    assertEquals(email, userResponse.getEmail());
    assertEquals(role, userResponse.getRole());
  }

  @Test
  void testSetId() {
    // Crear instancia
    UserResponseDto userResponse = new UserResponseDto(null, "Test User", "test@example.com", "ADMIN");

    // Asignar nuevo ID
    Long newId = 2L;
    userResponse.setId(newId);

    // Validar que getId devuelve el nuevo valor
    assertEquals(newId, userResponse.getId());
  }

  @Test
  void testSetName() {
    // Crear instancia
    UserResponseDto userResponse = new UserResponseDto(1L, null, "test@example.com", "ADMIN");

    // Asignar nuevo nombre
    String newName = "New User";
    userResponse.setName(newName);

    // Validar que getName devuelve el nuevo valor
    assertEquals(newName, userResponse.getName());
  }

  @Test
  void testSetEmail() {
    // Crear instancia
    UserResponseDto userResponse = new UserResponseDto(1L, "Test User", null, "ADMIN");

    // Asignar nuevo email
    String newEmail = "new@example.com";
    userResponse.setEmail(newEmail);

    // Validar que getEmail devuelve el nuevo valor
    assertEquals(newEmail, userResponse.getEmail());
  }

  @Test
  void testSetRole() {
    // Crear instancia
    UserResponseDto userResponse = new UserResponseDto(1L, "Test User", "test@example.com", null);

    // Asignar nuevo rol
    String newRole = "USER";
    userResponse.setRole(newRole);

    // Validar que getRole devuelve el nuevo valor
    assertEquals(newRole, userResponse.getRole());
  }

  @Test
  void testDefaultValues() {
    // Crear instancia con valores nulos
    UserResponseDto userResponse = new UserResponseDto(null, null, null, null);

    // Validar que los valores iniciales son null
    assertNull(userResponse.getId());
    assertNull(userResponse.getName());
    assertNull(userResponse.getEmail());
    assertNull(userResponse.getRole());
  }
}
