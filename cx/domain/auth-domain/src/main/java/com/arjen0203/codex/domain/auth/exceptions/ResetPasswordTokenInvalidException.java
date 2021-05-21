package com.arjen0203.codex.domain.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception that gets thrown when the provided Reset Password token is invalid. */
public class ResetPasswordTokenInvalidException extends ResponseStatusException {
  public ResetPasswordTokenInvalidException() {
    super(HttpStatus.BAD_REQUEST, "Reset Password token invalid");
  }
}
