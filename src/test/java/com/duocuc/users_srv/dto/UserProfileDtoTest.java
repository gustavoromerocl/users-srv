package com.duocuc.users_srv.dto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class UserProfileDtoTest {

  @Test
  void testConstructorAndGetters() {
    // Crear datos simulados
    Long id = 1L;
    String username = "testuser";
    String email = "test@example.com";
    RoleDto role = new RoleDto(1L, "ADMIN");
    List<RoleDto> roles = List.of(role);

    // Crear instancia usando el constructor
    UserProfileDto userProfile = new UserProfileDto(id, username, email, roles);

    // Validar los valores con los getters
    assertEquals(id, userProfile.getId());
    assertEquals(username, userProfile.getUsername());
    assertEquals(email, userProfile.getEmail());
    assertEquals(roles, userProfile.getRoles());
  }

  @Test
  void testSetId() {
    // Crear instancia
    UserProfileDto userProfile = new UserProfileDto(null, "testuser", "test@example.com", List.of());

    // Asignar nuevo ID
    Long newId = 2L;
    userProfile.setId(newId);

    // Validar que getId devuelve el nuevo ID
    assertEquals(newId, userProfile.getId());
  }

  @Test
  void testSetUsername() {
    // Crear instancia
    UserProfileDto userProfile = new UserProfileDto(1L, null, "test@example.com", List.of());

    // Asignar nuevo username
    String newUsername = "newuser";
    userProfile.setUsername(newUsername);

    // Validar que getUsername devuelve el nuevo valor
    assertEquals(newUsername, userProfile.getUsername());
  }

  @Test
  void testSetEmail() {
    // Crear instancia
    UserProfileDto userProfile = new UserProfileDto(1L, "testuser", null, List.of());

    // Asignar nuevo email
    String newEmail = "new@example.com";
    userProfile.setEmail(newEmail);

    // Validar que getEmail devuelve el nuevo valor
    assertEquals(newEmail, userProfile.getEmail());
  }

  @Test
  void testSetRoles() {
    // Crear instancia
    UserProfileDto userProfile = new UserProfileDto(1L, "testuser", "test@example.com", null);

    // Asignar nuevos roles
    RoleDto newRole = new RoleDto(2L, "USER");
    List<RoleDto> newRoles = List.of(newRole);
    userProfile.setRoles(newRoles);

    // Validar que getRoles devuelve la nueva lista de roles
    assertEquals(newRoles, userProfile.getRoles());
  }

  @Test
  void testDefaultValues() {
    // Crear instancia con valores iniciales nulos
    UserProfileDto userProfile = new UserProfileDto(null, null, null, null);

    // Validar que los valores iniciales sean null
    assertNull(userProfile.getId());
    assertNull(userProfile.getUsername());
    assertNull(userProfile.getEmail());
    assertNull(userProfile.getRoles());
  }
}
