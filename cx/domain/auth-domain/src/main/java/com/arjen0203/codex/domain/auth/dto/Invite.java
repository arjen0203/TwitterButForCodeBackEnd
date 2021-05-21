package com.arjen0203.codex.domain.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The DTO for creating invites for people. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invite {
  @NotBlank(message = "Email should not be empty")
  @Email(message = "Email should be a valid email address")
  private String email;

  private long roleId;
}
