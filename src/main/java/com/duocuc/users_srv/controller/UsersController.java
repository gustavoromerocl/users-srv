package com.duocuc.users_srv.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duocuc.users_srv.dto.RoleDto;
import com.duocuc.users_srv.dto.SignUpRequest;
import com.duocuc.users_srv.dto.UserProfileDto;
import com.duocuc.users_srv.dto.UserResponseDto;
import com.duocuc.users_srv.model.User;
import com.duocuc.users_srv.service.UserService;
import com.duocuc.users_srv.util.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UsersController {
  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtils jwtUtils;

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> getUsers() {
    try {
      List<UserResponseDto> users = userService.getAllUsers().stream()
          .map(user -> new UserResponseDto(
              user.getId(),
              user.getUsername(), // Mapea el username a name
              user.getEmail(),
              user.getRoles().isEmpty() ? "N/A" : user.getRoles().iterator().next().getName() // Obtén el primer rol
          ))
          .collect(Collectors.toList());
      return ResponseEntity.ok(users);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving users.");
    }
  }

  @GetMapping("/profile")
  public ResponseEntity<?> getAuthenticatedUserProfile(HttpServletRequest request) {
    try {
      String token = jwtUtils.getJwtFromRequest(request);
      System.err.println(token);
      Optional<User> userOpt = userService.getAuthenticatedUser(token);
      System.err.println(userOpt);
      if (userOpt.isPresent()) {
        User user = userOpt.get();

        // Mapear los roles a RoleDto
        List<RoleDto> roles = user.getRoles().stream()
            .map(role -> new RoleDto(role.getId(), role.getName()))
            .collect(Collectors.toList());

        // Crear el UserProfileDto
        UserProfileDto userProfile = new UserProfileDto(user.getId(), user.getUsername(), user.getEmail(), roles);

        return ResponseEntity.ok(userProfile);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user profile");
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody SignUpRequest updateRequest) {
    try {
      // Llamada al servicio para realizar la actualización
      userService.updateUser(id, updateRequest);

      // Respuesta JSON de éxito
      return ResponseEntity.ok(Map.of(
          "message", "User updated successfully!",
          "user", Map.of(
              "id", id,
              "username", updateRequest.getUsername(),
              "email", updateRequest.getEmail())));
    } catch (Exception e) {
      // Manejo de errores
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
          "message", "Error updating user.",
          "error", e.getMessage()));
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    try {
      // Llamada al servicio para eliminar el usuario
      userService.deleteUser(id);

      // Respuesta JSON de éxito
      return ResponseEntity.ok(Map.of(
          "message", "User deleted successfully!",
          "userId", id));
    } catch (Exception e) {
      // Manejo de errores
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
          "message", "Error deleting user.",
          "error", e.getMessage()));
    }
  }

}