package com.arjen0203.codex.domain.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception that gets thrown when the provided Invite token is invalid. */
public class InviteTokenInvalidException extends ResponseStatusException {
  public InviteTokenInvalidException() {
    super(HttpStatus.BAD_REQUEST, "Invite is invalid");
  }
}
