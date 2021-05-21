package com.arjen0203.codex.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO containing the information needed to Create a User. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser {
  private String username;
  private String email;
  private String password;
  private RoleDto role;
}
