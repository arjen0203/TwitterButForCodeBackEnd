package com.highcrit.flowerpower.service.user.controllers;

import java.util.UUID;

import com.highcrit.flowerpower.domain.user.dto.UserDto;
import com.highcrit.flowerpower.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller handling the REST requests for the User-Service. */
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  /**
   * Me mapping that retrieves the User by their own userId.
   *
   * @param userId the userId of the User making the request
   * @return UserDto for the user
   */
  @GetMapping("/me")
  public ResponseEntity<UserDto> me(@RequestHeader UUID userId) {
    var user = userService.getDtoById(userId);
    return ResponseEntity.ok(user);
  }
}
