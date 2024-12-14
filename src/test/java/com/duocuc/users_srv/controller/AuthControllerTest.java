package com.duocuc.users_srv.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.duocuc.users_srv.service.UserService;
import com.duocuc.users_srv.util.jwt.JwtUtils;

@WebMvcTest(controllers = AuthController.class)
@WithMockUser // Simula un usuario autenticado
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthenticationManager authenticationManager;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtUtils jwtUtils;

  @InjectMocks
  private AuthController authController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

    // Simular usuario autenticado
    Authentication authentication = mock(Authentication.class);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  public void testAuthenticateUserSuccess() throws Exception {
    // Datos simulados
    String jwtToken = "fake-jwt-token";
    Authentication authentication = mock(Authentication.class);

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
    when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwtToken);

    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\": \"test@example.com\", \"password\": \"password\"}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(jwtToken));
  }

  @Test
  public void testRegisterUserSuccess() throws Exception {
    when(userService.existsByUsername("newuser")).thenReturn(false);
    doAnswer(invocation -> null).when(userService).registerUser("newuser", "password", "newuser@example.com");

    mockMvc.perform(post("/api/auth/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\": \"newuser\", \"password\": \"password\", \"email\": \"newuser@example.com\"}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("success"))
        .andExpect(jsonPath("$.message").value("User registered successfully!"));
  }

  @Test
  public void testRegisterUserFailureUsernameTaken() throws Exception {
    when(userService.existsByUsername("existinguser")).thenReturn(true);

    mockMvc.perform(post("/api/auth/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            "{\"username\": \"existinguser\", \"password\": \"password\", \"email\": \"existinguser@example.com\"}"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Username is already taken!"));
  }
}
