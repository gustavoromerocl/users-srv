package com.duocuc.users_srv.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.duocuc.users_srv.util.jwt.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class JwtAuthenticationFilterTest {

  @Mock
  private JwtUtils jwtUtils;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain chain;

  @Mock
  private Claims claims;

  @InjectMocks
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    SecurityContextHolder.clearContext(); // Clear context before each test
  }

  @Test
  void testDoFilterInternal_WithValidToken() throws Exception {
    // Simular un token válido y el comportamiento de jwtUtils
    String token = "valid-jwt-token";
    String username = "testuser";
    String roles = "ROLE_USER,ROLE_ADMIN";
    when(jwtUtils.getJwtFromRequest(request)).thenReturn(token);
    when(jwtUtils.validateToken(token)).thenReturn(true);
    when(jwtUtils.getClaimsFromToken(token)).thenReturn(claims);
    when(claims.getSubject()).thenReturn(username);
    when(claims.get("roles")).thenReturn(roles);

    // Ejecutar el filtro
    jwtAuthenticationFilter.doFilterInternal(request, response, chain);

    // Verificar que el contexto de seguridad se ha actualizado correctamente
    SecurityContext context = SecurityContextHolder.getContext();
    assertNotNull(context.getAuthentication());
    assertEquals(username, context.getAuthentication().getName());
    assertTrue(context.getAuthentication().getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    assertTrue(context.getAuthentication().getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));

    // Verificar que se ha llamado al método doFilter
    verify(chain, times(1)).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_WithInvalidToken() throws Exception {
    // Simular un token no válido
    String token = "invalid-jwt-token";
    when(jwtUtils.getJwtFromRequest(request)).thenReturn(token);
    when(jwtUtils.validateToken(token)).thenReturn(false);

    // Ejecutar el filtro
    jwtAuthenticationFilter.doFilterInternal(request, response, chain);

    // Verificar que el contexto de seguridad no se ha actualizado
    SecurityContext context = SecurityContextHolder.getContext();
    assertNull(context.getAuthentication());

    // Verificar que se ha llamado al método doFilter
    verify(chain, times(1)).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_WithNoToken() throws Exception {
    // Simular que no se pasa token en la solicitud
    when(jwtUtils.getJwtFromRequest(request)).thenReturn(null);

    // Ejecutar el filtro
    jwtAuthenticationFilter.doFilterInternal(request, response, chain);

    // Verificar que el contexto de seguridad no se ha actualizado
    SecurityContext context = SecurityContextHolder.getContext();
    assertNull(context.getAuthentication());

    // Verificar que se ha llamado al método doFilter
    verify(chain, times(1)).doFilter(request, response);
  }
}
