package com.arjen0203.codex.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/** DTO Containing the information needed to actually reset your password. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
  @NotBlank(message = "Password should not be empty")
  @Length(min = 8, max = 64, message = "Passwords should be between 8 and 64 characters")
  public String password;

  @NotNull(message = "Token should not be null")
  public UUID token;
}
