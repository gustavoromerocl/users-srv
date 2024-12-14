package com.duocuc.users_srv.security;

import com.duocuc.users_srv.model.Role;
import com.duocuc.users_srv.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserPrincipalTest {

  private User user;

  @BeforeEach
  void setUp() {
    user = new User("testuser", "encodedpassword", "test@example.com");
    user.setId(1L);
    user.setRoles(Set.of(
        new Role("ADMIN", "ROLE_ADMIN"),
        new Role("USER", "ROLE_USER")));
  }

  @Test
  void testCreateUserPrincipal() {
    // Crear UserPrincipal desde el usuario
    UserPrincipal userPrincipal = UserPrincipal.create(user);

    // Validar valores básicos
    assertEquals(user.getId(), userPrincipal.getId());
    assertEquals(user.getEmail(), userPrincipal.getUsername());
    assertEquals(user.getPassword(), userPrincipal.getPassword());

    // Validar autoridades
    Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
    assertEquals(2, authorities.size());
    List<String> authorityNames = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .toList();
    assertTrue(authorityNames.contains("ADMIN"));
    assertTrue(authorityNames.contains("USER"));
  }

  @Test
  void testIsAccountNonExpired() {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    assertTrue(userPrincipal.isAccountNonExpired());
  }

  @Test
  void testIsAccountNonLocked() {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    assertTrue(userPrincipal.isAccountNonLocked());
  }

  @Test
  void testIsCredentialsNonExpired() {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    assertTrue(userPrincipal.isCredentialsNonExpired());
  }

  @Test
  void testIsEnabled() {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    assertTrue(userPrincipal.isEnabled());
  }
}
