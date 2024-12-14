package com.duocuc.users_srv.security;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.duocuc.users_srv.model.Role;
import com.duocuc.users_srv.model.User;
import com.duocuc.users_srv.repository.UserRepository;

class CustomUserDetailsServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CustomUserDetailsService customUserDetailsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLoadUserByUsernameSuccess() {
    // Simular usuario en la base de datos
    User user = new User("testuser", "encodedpassword", "test@example.com");
    user.setRoles(Set.of(new Role("ADMIN", "ROLE_ADMIN")));
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

    // Llamar al método
    UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");

    // Validar resultados
    assertNotNull(userDetails);
    assertEquals("test@example.com", userDetails.getUsername());
    assertEquals("encodedpassword", userDetails.getPassword());
    assertTrue(userDetails.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    verify(userRepository, times(1)).findByEmail("test@example.com");
  }

  @Test
  void testLoadUserByUsernameNotFound() {
    // Simular que el usuario no existe
    when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

    // Llamar al método y esperar excepción
    assertThrows(UsernameNotFoundException.class,
        () -> customUserDetailsService.loadUserByUsername("nonexistent@example.com"));

    // Verificar que se llamó al repositorio
    verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
  }
}
