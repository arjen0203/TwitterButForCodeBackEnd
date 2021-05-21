package com.arjen0203.codex.domain.post.entity;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
public class PostLike {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Type(type = "org.hibernate.type.UUIDCharType")
  @Column(length = 36)
  private UUID user;

  @ManyToOne private Post post;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostLike postLike = (PostLike) o;
    return id == postLike.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
