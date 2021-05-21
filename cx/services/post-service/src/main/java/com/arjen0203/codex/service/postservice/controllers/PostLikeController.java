package com.arjen0203.codex.service.postservice.controllers;

import java.util.UUID;

import com.arjen0203.codex.domain.post.dto.CommentDto;
import com.arjen0203.codex.domain.post.dto.PostLikeDto;
import com.arjen0203.codex.service.postservice.services.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the controller for the post likes
 *
 * <p>It contains basic crud functionality for the post likes.
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostLikeController {
  private final PostLikeService postLikeService;

  /**
   * Posting a like to be saved.
   *
   * @param userId the uuid of the user making the like
   * @return a response entity with the created like
   */
  @PostMapping("/{postId}/likes")
  public ResponseEntity<PostLikeDto> createPostLike(
      @RequestHeader UUID userId, @PathVariable long postId) {
    return ResponseEntity.ok(postLikeService.storePostLike(userId, postId));
  }

  /**
   * Get all the likes of a post (or at least a page).
   *
   * @return a page of likes of a post
   */
  @GetMapping("/{postId}/likes")
  public ResponseEntity<Page<PostLikeDto>> allCommentsOfPost(
          @PathVariable long postId,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(postLikeService.getAllPostLikes(postId, page, size));
  }

  /**
   * A method for removing a like.
   *
   * @param id the id of the like that should be removed.
   * @return a response entity with a string of the result.
   */
  @DeleteMapping("/likes/{id}")
  public ResponseEntity<String> removePostLIke(@PathVariable long id) {
    postLikeService.removePostLike(id);
    return ResponseEntity.ok("ok");
  }
}
