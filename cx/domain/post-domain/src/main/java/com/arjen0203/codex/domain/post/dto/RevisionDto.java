package com.arjen0203.codex.domain.post.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RevisionDto {
  private long id;
  private PostDto.RevisionReturn originalPost;
  private PostDto.RevisionReturn post;

  @Data
  public static class RequestData {
    @NotBlank(message = "post can't be blank")
    private PostDto.RequestData post;
  }

  @Data
  public static class PostSetReturnData {
    private long id;
    private PostDto post;
  }

  @Data
  public static class PostReturnData {
    private long id;
    private PostDto.RevisionReferenceReturn originalPost;
  }
}
