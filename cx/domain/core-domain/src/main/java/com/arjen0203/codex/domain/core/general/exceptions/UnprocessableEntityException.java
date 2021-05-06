package com.arjen0203.codex.domain.core.general.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception that gets thrown when the User's input doesn't match the constraints. */
public class UnprocessableEntityException extends ResponseStatusException {
  public UnprocessableEntityException(String message) {
    super(HttpStatus.UNPROCESSABLE_ENTITY, message);
  }
}
