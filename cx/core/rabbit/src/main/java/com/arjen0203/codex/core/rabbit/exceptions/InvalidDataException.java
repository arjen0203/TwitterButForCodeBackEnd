package com.arjen0203.codex.core.rabbit.exceptions;

/** Error that gets thrown whenever an invalid Payload is provided to a Kafka response object. */
public class InvalidDataException extends RuntimeException {
  /** Creates the error. */
  public InvalidDataException(Object payload) {
    super(
        "You tried to provide a data of type: "
            + payload.getClass().getName()
            + " which we can't JSON "
            + "stringify");
  }
}
