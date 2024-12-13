package com.duocuc.users_srv.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.duocuc.users_srv.model.User;

public class UserPrincipal implements UserDetails {
  private final Long id;
  private final String email; // Usar email en lugar de username
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(User user) {
    this.id = user.getId();
    this.email = user.getEmail(); // Cambiar a email
    this.password = user.getPassword();
    this.authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }

  public static UserPrincipal create(User user) {
    return new UserPrincipal(user);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email; // Cambiar a email
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
