package com.arjen0203.codex.domain.post.entity;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public void update(CommentDto commentDto) {
        this.content = commentDto.getContent();
    }
}
