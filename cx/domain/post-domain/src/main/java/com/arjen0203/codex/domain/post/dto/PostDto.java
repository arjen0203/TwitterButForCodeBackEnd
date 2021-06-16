package com.arjen0203.codex.domain.post.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDto {
  private long id;
  private UUID author;
  private String title;
  private Instant createdAt;
  private Set<ContentBlockDto> contentBlocks = new HashSet<>();
  private Set<PostLikeDto> postLikes = new HashSet<>();
  private Set<CommentDto> comments = new HashSet<>();
  private Set<RevisionDto> revisions = new HashSet<>();
  private RevisionDto revision;

  @Data
  public static class RequestData {
    @NotBlank(message = "title can't be blank")
    @Size(min = 3, max = 128, message = "Title must be between 3 and 128 characters")
    private String title;

    // @NotEmpty
    @Size(max = 5, message = "Content blocks can't be more then 5")
    private Set<ContentBlockDto> contentBlocks;

    private RevisionDto revision;
  }

  @Data
  public static class PostReturn {
    private long id;
    private UUID author;
    private String title;
    private Instant createdAt;
    private Set<ContentBlockDto> contentBlocks;
    private RevisionDto.PostReturnData revision;
  }

  @Data
  public static class PostStatReturn {
    private long id;
    private long postLikesCount;
    private long commentsCount;
    private long revisionsCount;
    private boolean isLiked;
  }

  @Data
  public static class RevisionReturn {
    private long id;
    private UUID author;
    private String title;
    private Instant createdAt;
    private Set<ContentBlockDto> contentBlocks;
  }

  @Data
  public static class RevisionReferenceReturn {
    private long id;
    private UUID author;
    private String title;
    private Instant createdAt;
  }
}
