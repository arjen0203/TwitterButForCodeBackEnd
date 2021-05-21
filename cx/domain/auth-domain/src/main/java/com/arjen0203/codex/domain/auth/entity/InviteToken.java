package com.arjen0203.codex.domain.auth.entity;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

/** Entity for storing Invite tokens that can be created by Admins to invite users. */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteToken {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  @Column(length = 36)
  private UUID token = UUID.randomUUID();

  @Column(unique = true, length = 191, nullable = false)
  private String email;

  private long roleId;

  @Column(nullable = false)
  private Instant timeStamp;
}
