package com.duocuc.users_srv.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class RoleDtoTest {

  @Test
  void testConstructorAndGetters() {
    // Crear instancia usando el constructor
    Long id = 1L;
    String name = "ADMIN";
    RoleDto roleDto = new RoleDto(id, name);

    // Validar que los getters devuelvan los valores correctos
    assertEquals(id, roleDto.getId());
    assertEquals(name, roleDto.getName());
  }

  @Test
  void testSetId() {
    // Crear instancia y asignar ID
    RoleDto roleDto = new RoleDto(null, "USER");
    Long newId = 2L;
    roleDto.setId(newId);

    // Validar que getId devuelva el nuevo valor
    assertEquals(newId, roleDto.getId());
  }

  @Test
  void testSetName() {
    // Crear instancia y asignar nombre
    RoleDto roleDto = new RoleDto(1L, null);
    String newName = "USER";
    roleDto.setName(newName);

    // Validar que getName devuelva el nuevo valor
    assertEquals(newName, roleDto.getName());
  }

  @Test
  void testDefaultValues() {
    // Crear instancia con valores iniciales nulos
    RoleDto roleDto = new RoleDto(null, null);

    // Validar que los valores iniciales sean null
    assertNull(roleDto.getId());
    assertNull(roleDto.getName());
  }
}
