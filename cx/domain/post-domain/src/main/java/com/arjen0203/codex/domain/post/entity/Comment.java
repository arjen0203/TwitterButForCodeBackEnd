package com.arjen0203.codex.domain.post.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.arjen0203.codex.domain.post.dto.CommentDto;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Type(type = "org.hibernate.type.UUIDCharType")
  @Column(length = 36)
  private UUID user;

  @Column(nullable = false)
  private Instant createdAt;

  @Column(length = 512)
  private String content;

  @ManyToOne private Post post;

  public void update(CommentDto.RequestData commentDto) {
    this.content = commentDto.getContent();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment = (Comment) o;
    return id == comment.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
