package com.arjen0203.codex.service.user.repositories;

import com.arjen0203.codex.domain.user.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Repository for roles. * */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {}
