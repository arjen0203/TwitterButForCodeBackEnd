package com.arjen0203.codex.service.auth.repositories;

import com.arjen0203.codex.domain.auth.entity.InviteToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

/** Repository for interacting with the InviteToken database. */
@Repository
public interface InviteTokenRepository extends JpaRepository<InviteToken, UUID> {
  void deleteByTimeStampBefore(Instant time);
}
