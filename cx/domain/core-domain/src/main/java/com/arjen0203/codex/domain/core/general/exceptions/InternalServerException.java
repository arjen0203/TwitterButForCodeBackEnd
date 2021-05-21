package com.arjen0203.codex.domain.core.general.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Default Internal Server Error (500). * */
public class InternalServerException extends ResponseStatusException {
  public InternalServerException() {
    super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }
}
