package com.highcrit.flowerpower.service.auth.repositories;

import com.highcrit.flowerpower.domain.auth.entity.InviteToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

/** Repository for interacting with the InviteToken database. */
@Repository
public interface InviteTokenRepository extends JpaRepository<InviteToken, UUID> {
  void deleteByTimeStampBefore(Instant time);
}
