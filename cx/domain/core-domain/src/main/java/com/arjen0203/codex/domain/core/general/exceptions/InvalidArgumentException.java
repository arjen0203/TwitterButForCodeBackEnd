package com.arjen0203.codex.domain.core.general.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception that gets thrown when a passed argument is not within specifications. * */
public class InvalidArgumentException extends ResponseStatusException {
  public InvalidArgumentException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
