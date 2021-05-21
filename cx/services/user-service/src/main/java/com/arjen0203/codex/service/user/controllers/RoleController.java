package com.highcrit.flowerpower.service.user.controllers;

import com.highcrit.flowerpower.domain.user.dto.RoleDto;
import com.highcrit.flowerpower.service.user.service.RoleService;
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
