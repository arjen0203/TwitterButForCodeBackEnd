package com.arjen0203.codex.domain.post.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RevisionDto {
  private long id;
  private PostDto.ShortReturn originalPost;
  private PostDto post;

  @Data
  public static class RequestData {
    @NotBlank(message = "post can't be blank")
    private PostDto.RequestData post;
  }
}
