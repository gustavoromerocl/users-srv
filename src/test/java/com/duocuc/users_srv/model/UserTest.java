package com.duocuc.users_srv.model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

  private User user;

  @BeforeEach
  void setUp() {
    user = new User("testuser", "securepassword", "test@example.com");
  }

  @Test
  void testConstructorWithParameters() {
    assertEquals("testuser", user.getUsername());
    assertEquals("securepassword", user.getPassword());
    assertEquals("test@example.com", user.getEmail());
    assertNotNull(user.getRoles());
    assertTrue(user.getRoles().isEmpty());
  }

  @Test
  void testSettersAndGetters() {
    user.setId(1L);
    user.setUsername("newuser");
    user.setPassword("newpassword");
    user.setEmail("new@example.com");

    assertEquals(1L, user.getId());
    assertEquals("newuser", user.getUsername());
    assertEquals("newpassword", user.getPassword());
    assertEquals("new@example.com", user.getEmail());
  }

  @Test
  void testAddRole() {
    Role adminRole = new Role("Admin", "ROLE_ADMIN");
    user.addRole(adminRole);

    assertEquals(1, user.getRoles().size());
    assertTrue(user.getRoles().contains(adminRole));
  }

  @Test
  void testRemoveRole() {
    Role adminRole = new Role("Admin", "ROLE_ADMIN");
    user.addRole(adminRole);

    assertEquals(1, user.getRoles().size());

    user.removeRole(adminRole);

    assertTrue(user.getRoles().isEmpty());
  }

  @Test
  void testSetRoles() {
    Role adminRole = new Role("Admin", "ROLE_ADMIN");
    Role userRole = new Role("User", "ROLE_USER");

    Set<Role> roles = new HashSet<>();
    roles.add(adminRole);
    roles.add(userRole);

    user.setRoles(roles);

    assertEquals(2, user.getRoles().size());
    assertTrue(user.getRoles().contains(adminRole));
    assertTrue(user.getRoles().contains(userRole));
  }

  @Test
  void testToString() {
    Role adminRole = new Role("Admin", "ROLE_ADMIN");
    user.addRole(adminRole);

    String expected = "User{id=null, username='testuser', email='test@example.com', roles=[" + adminRole.toString()
        + "]}";
    assertTrue(user.toString().contains("username='testuser'"));
    assertTrue(user.toString().contains("email='test@example.com'"));
    assertTrue(user.toString().contains("roles"));
  }
}
