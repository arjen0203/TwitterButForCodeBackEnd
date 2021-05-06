package com.arjen0203.codex.domain.core.general.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception that gets thrown when a User tries to do something they are not authorized to do. */
public class UnauthorizedException extends ResponseStatusException {
  public UnauthorizedException(String reason) {
    super(HttpStatus.UNAUTHORIZED, reason);
  }
}
