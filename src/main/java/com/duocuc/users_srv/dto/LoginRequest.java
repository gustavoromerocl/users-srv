package com.duocuc.users_srv.dto;

public class LoginRequest {
  private String password;
  private String email;

  // Getters y Setters
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
