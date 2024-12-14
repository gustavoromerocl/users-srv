package com.duocuc.users_srv.model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest {

  private Role role;

  @BeforeEach
  void setUp() {
    role = new Role("Admin", "ROLE_ADMIN");
  }

  @Test
  void testConstructorWithParameters() {
    assertEquals("Admin", role.getName());
    assertEquals("ROLE_ADMIN", role.getCode());
    assertNotNull(role.getUsers());
    assertTrue(role.getUsers().isEmpty());
  }

  @Test
  void testSettersAndGetters() {
    role.setId(1L);
    role.setName("User");
    role.setCode("ROLE_USER");

    assertEquals(1L, role.getId());
    assertEquals("User", role.getName());
    assertEquals("ROLE_USER", role.getCode());
  }

  @Test
  void testUsersAssociation() {
    User user1 = new User("testuser1", "password1", "test1@example.com");
    User user2 = new User("testuser2", "password2", "test2@example.com");

    Set<User> users = new HashSet<>();
    users.add(user1);
    users.add(user2);

    role.setUsers(users);

    assertEquals(2, role.getUsers().size());
    assertTrue(role.getUsers().contains(user1));
    assertTrue(role.getUsers().contains(user2));
  }

  @Test
  void testToString() {
    String expected = "Role{id=null, name='Admin', code='ROLE_ADMIN'}";
    assertEquals(expected, role.toString());
  }
}
