package com.arjen0203.codex.service.auth.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.arjen0203.codex.domain.auth.dto.Login;
import com.arjen0203.codex.domain.auth.dto.Register;
import com.arjen0203.codex.service.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Auth-Service controller to handle the incoming REST requests. */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
  private static final String SUCCESS = "Success";
  private static final String SET_COOKIE = "Set-Cookie";

  private final AuthService authService;

  /**
   * Authorize mapping that verifies if the User has a valid JWT.
   *
   * @param jwt the JWT provided by the User in the Authorization header
   * @param response the HttpServletResponse from spring to add headers
   * @return ResponseEntity notifying the user that their JWT is valid
   */
  @GetMapping
  public String auth(@CookieValue("Authorization") String jwt, HttpServletResponse response) {
    authService.auth(jwt).forEach(response::addHeader);
    return "Valid";
  }

  /**
   * Login mapping that logs the user in.
   *
   * @param login DTO containing the User's login info
   * @return ResponseEntity notifying the user that their login was successful
   */
  @PostMapping("/login")
  public String login(@Valid @RequestBody Login login, HttpServletResponse response) {
    var cookie = authService.login(login);
    response.addCookie(cookie);
    response.setHeader(SET_COOKIE, response.getHeader(SET_COOKIE) + "; SameSite=strict");
    return SUCCESS;
  }

  /**
   * Logout mapping that logs the user out by overriding the Auth cookie.
   *
   * @param response HttpServletResponse
   * @return Success string
   */
  @PostMapping("/logout")
  public String logout(HttpServletResponse response) {
    var cookie = authService.logout();
    response.addCookie(cookie);
    response.setHeader(SET_COOKIE, response.getHeader(SET_COOKIE) + "; SameSite=strict");
    return SUCCESS;
  }

  /**
   * Register mapping that registers a new user.
   *
   * @param register DTO containing the User's register info
   * @return ResponseEntity notifying the user that their register was successful
   */
  @PostMapping("/register")
  public String register(@Valid @RequestBody Register register) {
    authService.register(register);
    return SUCCESS;
  }
}
