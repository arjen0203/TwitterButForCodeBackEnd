package com.arjen0203.codex.domain.post.dto;

import javax.validation.constraints.NotBlank;

import com.arjen0203.codex.domain.post.entity.Post;
import lombok.Data;

@Data
public class RevisionDto {
    private Post originalPost;
    private Post post;

    @Data
    public static class RequestData {
        @NotBlank(message = "original post can't be blank")
        private Post originalPost;
        @NotBlank(message = "post can't be blank")
        private Post post;
    }
}
