package com.arjen0203.codex.service.exampleservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Example controller. */
@RestController
public class IndexController {
  @GetMapping
  public String index() {
    return "Example";
  }
}
