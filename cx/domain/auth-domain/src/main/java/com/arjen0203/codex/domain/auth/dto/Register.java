package com.arjen0203.codex.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/** DTO containing the register details a user sends to the api. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register implements Serializable {
  @NotBlank(message = "Username should not be empty")
  @Length(min = 4, max = 32, message = "Usernames should be between 4 and 32 characters")
  private String username;

  @NotBlank(message = "Email should not be empty")
  @Email(message = "Email should be a valid email address")
  private String email;

  @NotBlank(message = "Password should not be empty")
  @Length(min = 8, max = 64, message = "Passwords should be between 8 and 64 characters")
  private String password;

  @NotNull(message = "Token should not be null")
  private UUID inviteToken;
}
