package com.arjen0203.codex.service.postservice.controllers;

import java.util.UUID;

import javax.validation.Valid;

import com.arjen0203.codex.domain.post.dto.CommentDto;
import com.arjen0203.codex.service.postservice.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the controller for comments
 *
 * <p>It contains basic crud functionality for comments.
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;

  /**
   * Get all the comments (or at least a page).
   *
   * @return a page of comments
   */
  @GetMapping
  public ResponseEntity<Page<CommentDto>> allFlowers(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(commentService.getAllComments(page, size));
  }

  /**
   * The method for getting a specific comment.
   *
   * @param id the id of the desired comment
   * @return a response entity with the comment (if found)
   */
  @GetMapping("/{id}")
  public ResponseEntity<CommentDto> getFlowerById(@PathVariable long id) {
    return ResponseEntity.ok(commentService.getComment(id));
  }

  /**
   * Posting a flower to be saved.
   *
   * @param commentDto the flower to be stored
   * @param postId the id of the post you want the comment on
   * @param user the uuid of the user making the comment
   * @return a response entity with the updated flower, hopefully the same as the one send
   */
  @PostMapping
  public ResponseEntity<CommentDto> createComment(
          @PathVariable long postId,
          @Valid @RequestBody CommentDto.RequestData commentDto,
          @Valid @RequestBody UUID user) {
    return ResponseEntity.ok(commentService.storeComment(user, commentDto, postId));
  }

  /**
   * Putting a comment to be updated.
   *
   * @param id the id for the comment you want to update
   * @return a response entity with the updated comment, hopefully the same as the one send
   */
  @PutMapping("/{id}")
  public ResponseEntity<CommentDto> updateComment(
      @Valid @RequestPart("comment") CommentDto commentDto,
      @PathVariable long id) {
    return ResponseEntity.ok(commentService.updateComment(commentDto, id));
  }

  /**
   * A method for removing a comment.
   *
   * @param id the id of the comment that should be removed.
   * @return a response entity with a string of the result.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeComment(@PathVariable long id) {
    commentService.removeComment(id);
    return ResponseEntity.ok("ok");
  }
}
