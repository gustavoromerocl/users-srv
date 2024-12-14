package com.duocuc.users_srv.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.duocuc.users_srv.dto.SignUpRequest;
import com.duocuc.users_srv.dto.UserProfileDto;
import com.duocuc.users_srv.dto.UserResponseDto;
import com.duocuc.users_srv.model.Role;
import com.duocuc.users_srv.model.User;
import com.duocuc.users_srv.service.UserService;
import com.duocuc.users_srv.util.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

class UsersControllerTest {

  @InjectMocks
  private UsersController usersController;

  @Mock
  private UserService userService;

  @Mock
  private JwtUtils jwtUtils;

  @Mock
  private HttpServletRequest request;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetUsers() {
    // Datos de prueba
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    Role role = new Role("USER", "ROLE_USER");
    user.setRoles(Set.of(role));

    // Mockear el servicio
    when(userService.getAllUsers()).thenReturn(List.of(user));

    // Ejecutar el método
    ResponseEntity<?> response = usersController.getUsers();

    // Verificaciones
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());

    List<UserResponseDto> users = (List<UserResponseDto>) response.getBody();
    assertNotNull(users);
    assertEquals(1, users.size());
    assertEquals("testuser", users.get(0).getName());
    assertEquals("USER", users.get(0).getRole());

    verify(userService, times(1)).getAllUsers();
  }

  @Test
  void testGetAuthenticatedUserProfileSuccess() {
    // Datos de prueba
    String token = "valid-token";
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    Role role = new Role("USER", "ROLE_USER");
    user.setRoles(Collections.singleton(role));

    when(jwtUtils.getJwtFromRequest(request)).thenReturn(token);
    when(userService.getAuthenticatedUser(token)).thenReturn(Optional.of(user));

    // Ejecutar el método
    ResponseEntity<?> response = usersController.getAuthenticatedUserProfile(request);

    // Verificaciones
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertTrue(response.getBody() instanceof UserProfileDto);

    UserProfileDto userProfile = (UserProfileDto) response.getBody();
    assertEquals(user.getId(), userProfile.getId());
    assertEquals(user.getUsername(), userProfile.getUsername());
    assertEquals("USER", userProfile.getRoles().get(0).getName());

    verify(jwtUtils, times(1)).getJwtFromRequest(request);
    verify(userService, times(1)).getAuthenticatedUser(token);
  }

  @Test
  void testUpdateUserSuccess() {
    // Datos de prueba
    Long userId = 1L;
    SignUpRequest updateRequest = new SignUpRequest();
    updateRequest.setUsername("updatedUser");
    updateRequest.setEmail("updated@example.com");

    doNothing().when(userService).updateUser(userId, updateRequest);

    // Ejecutar el método
    ResponseEntity<?> response = usersController.updateUser(userId, updateRequest);

    // Verificaciones
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());

    verify(userService, times(1)).updateUser(userId, updateRequest);
  }

  @Test
  void testDeleteUserSuccess() {
    // Datos de prueba
    Long userId = 1L;

    doNothing().when(userService).deleteUser(userId);

    // Ejecutar el método
    ResponseEntity<?> response = usersController.deleteUser(userId);

    // Verificaciones
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());

    verify(userService, times(1)).deleteUser(userId);
  }
}
