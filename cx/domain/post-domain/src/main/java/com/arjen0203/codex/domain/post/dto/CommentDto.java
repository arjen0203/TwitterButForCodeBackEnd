package com.arjen0203.codex.domain.post.dto;

import java.time.Instant;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.arjen0203.codex.domain.post.entity.Post;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommentDto {
    private long id;

    //@NotNull(message = "User can not be null")
    private UUID user;

    @NotNull(message = "creation date can't be null")
    @PastOrPresent(message = "creation date can't be in the future")
    private Instant createdAt;

    private String content;

    @NotBlank(message = "Post can't be blank")
    private Post post;

    @Data
    public static class RequestData {
        @NotBlank(message = "Content can't be blank")
        @Size(min = 5, max = 512, message = "Content must be between 5 and 512 characters")
        private String content;
    }
}
