package com.arjen0203.codex.domain.post.interfaces;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.arjen0203.codex.domain.post.entity.ContentBlock;
import com.arjen0203.codex.domain.post.entity.Revision;

public interface IRevisionPost {
    long getId();
    UUID getAuthor();
    Instant getCreatedAt();
    String getTitle();
}
