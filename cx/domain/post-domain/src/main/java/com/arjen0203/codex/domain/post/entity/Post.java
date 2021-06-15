package com.arjen0203.codex.domain.post.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.arjen0203.codex.domain.post.dto.PostDto;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Type(type = "org.hibernate.type.UUIDCharType")
  @Column(length = 36)
  private UUID author;

  @Column(length = 128)
  private String title;

  @Column(nullable = false)
  private Instant createdAt;

  @OneToMany(cascade = {CascadeType.ALL})
  private Set<ContentBlock> contentBlocks;

  @OneToMany(
      cascade = {CascadeType.REMOVE},
      mappedBy = "post")
  private Set<PostLike> postLikes;

  @OneToMany(
      cascade = {CascadeType.REMOVE},
      mappedBy = "post")
  private Set<Comment> comments;

  @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "originalPost")
  private Set<Revision> revisions;

  @OneToOne(cascade = CascadeType.ALL)
  private Revision revision;

//  @OneToOne
//  @JoinColumn(name = "revision_id", referencedColumnName = "id")
//  private Revision revision;

  public void update(PostDto postDto) {
    this.title = postDto.getTitle();
    // todo add contentblocks updating
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Post post = (Post) o;
    return id == post.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
