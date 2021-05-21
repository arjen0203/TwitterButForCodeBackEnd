package com.arjen0203.codex.domain.core.general.errorhandlers;

import lombok.AllArgsConstructor;
import lombok.Data;

/** The Error that gets returned inside a ResponseEntity. */
@Data
@AllArgsConstructor
public class ReturnError {
  private final int code;
  private final String status;
  private final String message;
}
