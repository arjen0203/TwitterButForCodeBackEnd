package com.arjen0203.codex.service.postservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
  @GetMapping
  public String index() {
    return "Example";
  }
}
