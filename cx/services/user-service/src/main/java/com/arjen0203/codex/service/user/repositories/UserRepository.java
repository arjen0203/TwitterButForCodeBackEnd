package com.arjen0203.codex.service.user.repositories;

import java.util.Optional;
import java.util.UUID;

import com.arjen0203.codex.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** The repository for interacting with the User database. */
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
}
