package com.highcrit.flowerpower.service.user.repositories;

import java.util.Optional;
import java.util.UUID;

import com.highcrit.flowerpower.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** The repository for interacting with the User database. */
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
  Optional<User> findByEmail(String email);
}
