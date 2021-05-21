package com.arjen0203.codex.domain.core.general.errorhandlers;

import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

/** Handles various REST related errors that get thrown by the services. */
@Configuration
@RestControllerAdvice
public class RestErrorHandler {

  /**
   * Converts thrown REST error to a ResponseEntity error message.
   *
   * @param err ResponseStatusException that got thrown
   * @return ResponseEntity with the status and message
   */
  @ExceptionHandler
  public ResponseEntity<ReturnError> handleExceptions(final ResponseStatusException err) {
    var status = err.getStatus();
    return ResponseEntity.status(err.getStatus())
        .body(new ReturnError(status.value(), status.getReasonPhrase(), err.getReason()));
  }

  /**
   * Converts thrown Type mismatch error to a ResponseEntity error message.
   *
   * @param err TypeMismatchError that got thrown
   * @return ResponseEntity with the status and message
   */
  @ExceptionHandler
  public ResponseEntity<ReturnError> handleTypeMismatchException(
      final MethodArgumentTypeMismatchException err) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ReturnError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                err.getName() + " was not of a valid type"));
  }

  /**
   * Converts Missing Request Error's to a ResponseEntity error message.
   *
   * @param err MissingRequestHeaderError that got thrown
   * @return ResponseEntity with the status and message
   */
  @ExceptionHandler
  public ResponseEntity<ReturnError> handleMissingRequestHeaderException(
      final MissingRequestHeaderException err) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            new ReturnError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                err.getHeaderName() + " is missing"));
  }

  /**
   * Converts Missing Cookie Errors to a ResponseEntity error message.
   *
   * @param err MissingRequestCookieError that got thrown
   * @return ResponseEntity with the status and message
   */
  @ExceptionHandler
  public ResponseEntity<ReturnError> handleMissingCookieException(
      final MissingRequestCookieException err) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            new ReturnError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                err.getCookieName() + " is missing"));
  }

  /**
   * Handles MethodArgumentNotValidException which are thrown by Spring's validator.
   *
   * @param err MethodArgumentNotValidException that got thrown
   * @return ResponseEntity with the status and message
   */
  @ExceptionHandler
  public ResponseEntity<ReturnError> handleConstraintViolationException(
      MethodArgumentNotValidException err) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ReturnError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                err.getBindingResult().getAllErrors().stream()
                    .map(
                        e ->
                            String.format(
                                "%s: %s", ((FieldError) e).getField(), e.getDefaultMessage()))
                    .collect(Collectors.joining("\n"))));
  }

  /**
   * Handles InvalidContentTypeExceptions which are thrown by Spring when the content type doesn't
   * match.
   *
   * @param err InvalidContentTypeException that got thrown
   * @return ResponseEntity with the status and message
   */
  @ExceptionHandler
  public ResponseEntity<ReturnError> handleInvalidContentTypeException(
      InvalidContentTypeException err) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ReturnError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                err.getMessage()));
  }

  /**
   * Handles HttpMessageNotReadableExceptions which are thrown by Spring when the provided type
   * doesn't match the expected type in the controller.
   *
   * @param err HttpMessageNotReadableException that got thrown
   * @return ResponseEntity with the status and message
   */
  @ExceptionHandler
  public ResponseEntity<ReturnError> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException err) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(
            new ReturnError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                err.getMessage()));
  }
}
