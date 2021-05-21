package com.arjen0203.codex.domain.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Error which gets thrown whenever a User is trying to create an account with a duplicate email.
 */
public class EmailAlreadyInUseException extends ResponseStatusException {
  public EmailAlreadyInUseException() {
    super(HttpStatus.CONFLICT, "Email is already in use.");
  }
}
