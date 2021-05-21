package com.arjen0203.codex.domain.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Error that gets thrown whenever the user enters an incorrect email or password. */
public class InvalidEmailOrPasswordException extends ResponseStatusException {
  public InvalidEmailOrPasswordException() {
    super(HttpStatus.UNAUTHORIZED, "Email or Password is invalid");
  }
}
