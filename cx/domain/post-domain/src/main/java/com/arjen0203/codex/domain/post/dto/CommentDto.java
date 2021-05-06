package com.arjen0203.codex.domain.post.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class CommentDto {
    private UUID user;
    private Instant createdAt;
    private String content;
}
