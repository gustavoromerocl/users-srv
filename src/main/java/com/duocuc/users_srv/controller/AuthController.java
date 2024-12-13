package com.duocuc.users_srv.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duocuc.users_srv.dto.JwtResponse;
import com.duocuc.users_srv.dto.LoginRequest;
import com.duocuc.users_srv.dto.SignUpRequest;
import com.duocuc.users_srv.service.UserService;
import com.duocuc.users_srv.util.jwt.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtils jwtUtils;

  // Ruta para iniciar sesión
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), // Cambiar a email
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  // Ruta para registrar un usuario
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
    // Verificar si el nombre de usuario ya está en uso
    if (userService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(
          Map.of(
              "status", "error",
              "message", "Username is already taken!"));
    }

    // Registrar el nuevo usuario usando el método registerUser de UserService
    userService.registerUser(
        signUpRequest.getUsername(),
        signUpRequest.getPassword(),
        signUpRequest.getEmail());

    return ResponseEntity.ok(
        Map.of(
            "status", "success",
            "message", "User registered successfully!"));
  }
}