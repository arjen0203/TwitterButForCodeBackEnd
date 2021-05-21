package com.highcrit.flowerpower.service.user.repositories;

import com.highcrit.flowerpower.domain.user.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Repository for roles. * */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {}
