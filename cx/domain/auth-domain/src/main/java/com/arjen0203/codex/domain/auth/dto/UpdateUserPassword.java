package com.arjen0203.codex.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO Containing the information needed to update a user's password. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPassword {
  private String email;
  private String password;
}
