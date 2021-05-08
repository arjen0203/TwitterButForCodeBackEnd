package com.arjen0203.codex.service.postservice.controllers;

import java.util.UUID;

import com.arjen0203.codex.domain.post.dto.PostLikeDto;
import com.arjen0203.codex.service.postservice.services.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the controller for the post likes
 *
 * <p>It contains basic crud functionality for the post likes.
 */
@RestController
@RequestMapping("/{postId}/likes")
@RequiredArgsConstructor
public class PostLikeController {
  private final PostLikeService postLikeService;

  /**
   * The method for getting a specific like.
   *
   * @param id the id of the desired like
   * @return a response entity with the like (if found)
   */
  @GetMapping("/{id}")
  public ResponseEntity<PostLikeDto> getPostLikeById(@PathVariable long id) {
    return ResponseEntity.ok(postLikeService.getPostLikeById(id));
  }

  /**
   * Posting a like to be saved.
   *
   * @param userId the uuid of the user making the like
   * @return a response entity with the created like
   */
  @PostMapping
  public ResponseEntity<PostLikeDto> createPostLike(
          @RequestHeader UUID userId,
          @PathVariable long postId) {
    return ResponseEntity.ok(postLikeService.storePostLike(userId, postId));
  }

  /**
   * A method for removing a like.
   *
   * @param id the id of the like that should be removed.
   * @return a response entity with a string of the result.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> removePostLIke(@PathVariable long id) {
    postLikeService.removePostLike(id);
    return ResponseEntity.ok("ok");
  }
}
