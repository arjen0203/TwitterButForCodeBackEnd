package com.arjen0203.codex.domain.core.general.exceptions;

/** Base Error which extends RuntimeException to include a status code. * */
public class RestError extends RuntimeException {
  private final int statusCode;

  public RestError(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
