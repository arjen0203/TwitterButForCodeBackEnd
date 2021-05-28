package com.arjen0203.codex.domain.user.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

/** Entity for storing Users in the database. */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  @Column(length = 36)
  private UUID id = UUID.randomUUID();

  @Column(length = 32, unique = true)
  private String username;

  @ManyToOne private Role role;

  @Column(unique = true)
  private String email;

  private String password;
}
