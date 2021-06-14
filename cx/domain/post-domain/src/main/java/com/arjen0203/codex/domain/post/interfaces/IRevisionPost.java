package com.arjen0203.codex.domain.post.interfaces;

import java.time.Instant;
import java.util.UUID;

public interface IRevisionPost {
    long getId();
    UUID getAuthor();
    Instant getCreatedAt();
    String getTitle();
}
