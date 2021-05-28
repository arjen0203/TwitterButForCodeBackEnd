package com.arjen0203.codex.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dto for the role entity. * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
  private long id;
  private String name;

  public RoleDto(long id) {
    this.id = id;
  }
}
