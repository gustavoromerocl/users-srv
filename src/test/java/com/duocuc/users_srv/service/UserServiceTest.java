package com.duocuc.users_srv.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.duocuc.users_srv.dto.SignUpRequest;
import com.duocuc.users_srv.model.Role;
import com.duocuc.users_srv.model.User;
import com.duocuc.users_srv.repository.RoleRepository;
import com.duocuc.users_srv.repository.UserRepository;
import com.duocuc.users_srv.util.jwt.JwtUtils;

class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtUtils jwtUtils;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllUsers() {
    // Simular usuarios en la base de datos
    User user1 = new User("user1", "password1", "user1@example.com");
    User user2 = new User("user2", "password2", "user2@example.com");
    when(userRepository.findAll()).thenReturn(List.of(user1, user2));

    // Llamar al método
    List<User> users = userService.getAllUsers();

    // Validar resultados
    assertEquals(2, users.size());
    assertEquals("user1", users.get(0).getUsername());
    assertEquals("user2", users.get(1).getUsername());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testRegisterUser() {
    // Simular roles en la base de datos
    Role userRole = new Role("USER", "ROLE_USER");
    when(roleRepository.findByCode("ROLE_USER")).thenReturn(Optional.of(userRole));
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Llamar al método
    User newUser = userService.registerUser("newuser", "password123", "newuser@example.com");

    // Validar resultados
    assertEquals("newuser", newUser.getUsername());
    assertEquals("encodedPassword", newUser.getPassword());
    assertEquals("newuser@example.com", newUser.getEmail());
    assertTrue(newUser.getRoles().contains(userRole));
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void testUpdateUser() {
    // Simular usuario existente
    User existingUser = new User("olduser", "oldpassword", "olduser@example.com");
    when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
    when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");

    // Crear solicitud de actualización
    SignUpRequest updateRequest = new SignUpRequest();
    updateRequest.setUsername("updateduser");
    updateRequest.setPassword("newpassword");
    updateRequest.setEmail("updateduser@example.com");

    // Llamar al método
    userService.updateUser(1L, updateRequest);

    // Validar resultados
    assertEquals("updateduser", existingUser.getUsername());
    assertEquals("newEncodedPassword", existingUser.getPassword());
    assertEquals("updateduser@example.com", existingUser.getEmail());
    verify(userRepository, times(1)).save(existingUser);
  }

  @Test
  void testDeleteUser() {
    // Simular usuario existente
    User existingUser = new User("user", "password", "user@example.com");
    existingUser.setRoles(new HashSet<>(Set.of(new Role("USER", "ROLE_USER")))); // Conjunto mutable
    when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

    // Llamar al método
    userService.deleteUser(1L);

    // Validar que las asociaciones se eliminaron y el usuario fue borrado
    assertTrue(existingUser.getRoles().isEmpty());
    verify(userRepository, times(1)).save(existingUser);
    verify(userRepository, times(1)).delete(existingUser);
  }

  @Test
  void testGetAuthenticatedUser() {
    // Simular token y usuario
    String token = "valid-token";
    String email = "user@example.com";
    User user = new User("user", "password", email);
    when(jwtUtils.getAuthenticatedUsername(token)).thenReturn(email);
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    // Llamar al método
    Optional<User> authenticatedUser = userService.getAuthenticatedUser(token);

    // Validar resultados
    assertTrue(authenticatedUser.isPresent());
    assertEquals(email, authenticatedUser.get().getEmail());
    verify(jwtUtils, times(1)).getAuthenticatedUsername(token);
    verify(userRepository, times(1)).findByEmail(email);
  }
}
