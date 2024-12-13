package com.duocuc.users_srv.dto;

import java.util.List;

public class UserProfileDto {
  private Long id;
  private String username;
  private String email;
  private List<RoleDto> roles;

  // Constructor
  public UserProfileDto(Long id, String username, String email, List<RoleDto> roles) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  // Getters y Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<RoleDto> getRoles() {
    return roles;
  }

  public void setRoles(List<RoleDto> roles) {
    this.roles = roles;
  }
}
