package com.arjen0203.codex.domain.core.general.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception thrown when a resource does not exist. */
public class NotFoundException extends ResponseStatusException {
  public NotFoundException() {
    super(HttpStatus.NOT_FOUND, "Could not resolve entity");
  }

  public NotFoundException(String name) {
    super(HttpStatus.NOT_FOUND, name + " could not be found");
  }
}
