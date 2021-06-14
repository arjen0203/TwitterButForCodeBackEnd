package com.arjen0203.codex.domain.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/** DTO containing the login details a user sends to the api. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
  @NotBlank(message = "Email should not be empty")
  @Email(message = "Email should be a valid email address")
  private String email;

  @NotBlank(message = "Password should not be empty")
  @Length(min = 8, max = 64, message = "Passwords should be between 8 and 64 characters")
  private String password;

  private boolean rememberMe;
}
