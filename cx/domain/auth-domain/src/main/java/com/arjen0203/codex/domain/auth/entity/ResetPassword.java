package com.arjen0203.codex.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

/** Entity containing the information about users that are resetting their password. */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  @Column(length = 36)
  private UUID token = UUID.randomUUID();

  @Column(unique = true, length = 191)
  private String email;

  @Column(nullable = false)
  private Instant timeStamp;
}
