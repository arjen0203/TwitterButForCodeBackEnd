package com.highcrit.flowerpower.service.auth.repositories;

import com.highcrit.flowerpower.domain.auth.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

/** Repository for interacting with the PasswordReset database. */
@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, UUID> {
  void deleteByTimeStampBefore(Instant time);
}
