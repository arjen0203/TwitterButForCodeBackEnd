package com.arjen0203.codex.service.user.service;

import java.util.List;

import com.arjen0203.codex.domain.user.dto.RoleDto;
import com.arjen0203.codex.service.user.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

/** Service for business logic with roles. * */
@Service
@AllArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;
  private final TypeToken<Iterable<RoleDto>> roleDtoTypeToken = new TypeToken<>() {};

  /**
   * Gets all the available roles.
   *
   * @return a Set of all the roles
   */
  public List<RoleDto> getAll() {
    return modelMapper.map(roleRepository.findAll(), roleDtoTypeToken.getType());
  }
}
