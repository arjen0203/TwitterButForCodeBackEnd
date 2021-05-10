package com.arjen0203.codex.domain.post.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.arjen0203.codex.domain.post.entity.Post;
import lombok.Data;

@Data
public class PostLikeDto {
    private long id;
    private UUID user;
}
