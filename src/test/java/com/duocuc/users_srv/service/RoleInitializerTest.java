package com.duocuc.users_srv.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.duocuc.users_srv.model.Role;
import com.duocuc.users_srv.repository.RoleRepository;

class RoleInitializerTest {

  @Mock
  private RoleRepository roleRepository;

  @InjectMocks
  private RoleInitializer roleInitializer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testInitRolesWhenRolesDoNotExist() {
    // Simular que los roles no existen
    when(roleRepository.findByCode("ROLE_ADMIN")).thenReturn(Optional.empty());
    when(roleRepository.findByCode("ROLE_USER")).thenReturn(Optional.empty());

    // Ejecutar el método
    roleInitializer.initRoles();

    // Capturar los roles guardados
    ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
    verify(roleRepository, times(2)).save(roleCaptor.capture());

    // Validar que se guardaron los roles correctos
    assertEquals(2, roleCaptor.getAllValues().size());
    assertEquals("ADMIN", roleCaptor.getAllValues().get(0).getName());
    assertEquals("ROLE_ADMIN", roleCaptor.getAllValues().get(0).getCode());
    assertEquals("USER", roleCaptor.getAllValues().get(1).getName());
    assertEquals("ROLE_USER", roleCaptor.getAllValues().get(1).getCode());
  }

  @Test
  void testInitRolesWhenRolesExist() {
    // Simular que los roles ya existen
    when(roleRepository.findByCode("ROLE_ADMIN")).thenReturn(Optional.of(new Role("ADMIN", "ROLE_ADMIN")));
    when(roleRepository.findByCode("ROLE_USER")).thenReturn(Optional.of(new Role("USER", "ROLE_USER")));

    // Ejecutar el método
    roleInitializer.initRoles();

    // Verificar que no se guarde ningún rol nuevo
    verify(roleRepository, never()).save(any(Role.class));
  }
}
