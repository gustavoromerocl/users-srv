package com.duocuc.users_srv.service;

import com.duocuc.users_srv.model.Role;
import com.duocuc.users_srv.model.User;
import com.duocuc.users_srv.repository.RoleRepository;
import com.duocuc.users_srv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // Método para registrar un usuario con un rol predeterminado
  public User registerUser(String username, String password, String email) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setEmail(email);

    Role userRole = roleRepository.findByCode("ROLE_USER")
        .orElseThrow(() -> new RuntimeException("Error: Role not found."));
    Set<Role> roles = new HashSet<>();
    roles.add(userRole);
    user.setRoles(roles);

    return userRepository.save(user);
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

}
