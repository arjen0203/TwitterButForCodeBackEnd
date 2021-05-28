package com.arjen0203.codex.domain.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Error that gets thrown whenever the user provides an invalid JWT. */
public class InvalidJwtException extends ResponseStatusException {
  public InvalidJwtException() {
    super(HttpStatus.UNAUTHORIZED, "JWT token is invalid");
  }
}
