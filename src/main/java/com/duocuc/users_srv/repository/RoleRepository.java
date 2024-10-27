package com.duocuc.users_srv.repository;

import com.duocuc.users_srv.model.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByCode(String code);
}