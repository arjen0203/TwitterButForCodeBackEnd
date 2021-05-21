package com.arjen0203.codex.service.user.controllers;

import com.arjen0203.codex.domain.user.dto.RoleDto;
import com.arjen0203.codex.service.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for roles. * */
@RestController
@RequestMapping("users/roles")
@RequiredArgsConstructor
public class RoleController {
  private final RoleService roleService;

  /**
   * Gets all the available roles.
   *
   * @return a Set containing all the roles
   */
  @GetMapping
  public Iterable<RoleDto> getAllRoles() {
    return roleService.getAll();
  }
}
