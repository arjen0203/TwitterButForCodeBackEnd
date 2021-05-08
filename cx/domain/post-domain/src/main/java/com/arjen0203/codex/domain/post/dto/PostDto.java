package com.arjen0203.codex.domain.post.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDto {
    private long id;
    private UUID author;
    private String title;
    private Instant createdAt;
    private List<ContentBlockDto> contentBlockDtos;
    private List<PostLikeDto> postLikeDtos;
    private List<CommentDto> commentDtos;
    private RevisionDto revisionDto;

    @Data
    public static class RequestData {
        @NotBlank(message = "title can't be blank")
        @Size(min = 3, max = 128, message = "Title must be between 3 and 128 characters")
        private String title;

        //@NotEmpty
        @Size(max = 5, message = "Content blocks can't be more then 5")
        private List<ContentBlockDto> contentBlockDtos;

        private RevisionDto revisionDto;
    }
}
