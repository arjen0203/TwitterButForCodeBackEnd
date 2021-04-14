package com.arjen0203.codex.domain.core.general.exceptions;

/** Default Internal Server Error (500). * */
public class InternalServerError extends RestError {
  public InternalServerError() {
    super("Internal Server Error", 500);
  }
}
