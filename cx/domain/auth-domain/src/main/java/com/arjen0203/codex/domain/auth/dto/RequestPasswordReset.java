package com.arjen0203.codex.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/** DTO Containing the information needed for a Password Reset request. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPasswordReset {
  @NotBlank(message = "Email should not be empty")
  @Email(message = "Email should be a valid email address")
  private String email;
}
