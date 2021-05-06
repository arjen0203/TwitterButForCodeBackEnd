package com.arjen0203.codex.domain.post.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class PostDto {
    private UUID author;
    private String title;
    private Instant createdAt;
    private List<ContentBlockDto> contentBlockDtos;
    private List<LikeDto> likeDtos;
    private List<CommentDto> commentDtos;
    private RevisionDto revisionDto;
}
