package com.arjen0203.codex.domain.post.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class PostLikeDto {
  private long id;
  private UUID user;
}
