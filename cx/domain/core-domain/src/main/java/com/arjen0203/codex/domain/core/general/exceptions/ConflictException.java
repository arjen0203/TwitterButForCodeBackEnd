package com.arjen0203.codex.domain.core.general.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a resource conflicts with an existing resource e.g. unique constraint
 * violation.
 */
public class ConflictException extends ResponseStatusException {
  public ConflictException(String reason) {
    super(HttpStatus.CONFLICT, reason);
  }
}
