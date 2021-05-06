package com.arjen0203.codex.domain.core.general.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception thrown when an entity holds invalid values. */
public class InvalidEntityException extends ResponseStatusException {
  public InvalidEntityException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
