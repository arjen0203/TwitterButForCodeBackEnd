package com.arjen0203.codex.domain.user.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO containing the information about a User. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private UUID id;
  private String username;
  private RoleDto role;
  private String email;


}
