package com.arjen0203.codex.domain.post.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.arjen0203.codex.domain.post.dto.CommentDto;
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

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<ContentBlock> contentBlocks;

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<PostLike> postLikes;

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Comment> comments;

    //if there exist no revision data then it is a original post.
    @OneToMany(
            mappedBy = "originalPost",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Revision> revisions;

    public void update(PostDto postDto) {
        this.title = postDto.getTitle();
        //todo add contentblocks updating
    }
}
