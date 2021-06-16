package com.arjen0203.codex.service.postservice.controllers;

import java.util.UUID;

import javax.validation.Valid;

import com.arjen0203.codex.domain.post.dto.PostDto;
import com.arjen0203.codex.service.postservice.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;

  /**
   * Get all the posts (or at least a page).
   *
   * @return a page of posts
   */
  @GetMapping
  public ResponseEntity<Page<PostDto.PostReturn>> allPosts(@RequestHeader UUID userId,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(postService.getAllPosts(userId, page, size));
  }

  /**
   * Get all the posts (or at least a page) of a user.
   *
   * @return a page of posts
   */
  @GetMapping("/user/{postUserId}")
  public ResponseEntity<Page<PostDto.PostReturn>> getAllUserPosts(@RequestHeader UUID userId,
          @PathVariable UUID postUserId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(postService.getAllUserPosts(userId, postUserId, page, size));
  }

  /**
   * The method for getting a specific post.
   *
   * @param id the id of the desired post
   * @return a response entity with the post (if found)
   */
  @GetMapping("/{id}")
  public ResponseEntity<PostDto.PostReturn> getPostById(@RequestHeader UUID userId, @PathVariable long id) {
    return ResponseEntity.ok(postService.getPostDtoById(id, userId));
  }

  /**
   * The method for getting a specific post.
   *
   * @param id the id of the desired post
   * @return a response entity with the post (if found)
   */
  @GetMapping("/{id}/stats")
  public ResponseEntity<PostDto.PostStatReturn> getPostStatsById(@RequestHeader UUID userId, @PathVariable long id) {
    return ResponseEntity.ok(postService.getPostStatsDto(id, userId));
  }

  /**
   * Posting a post to be saved.
   *
   * @param postDto the post to be stored
   * @param userId the uuid of the user making the post
   * @return a response entity with the created post
   */
  @PostMapping
  public ResponseEntity<PostDto> createPost(
      @RequestHeader UUID userId, @RequestBody PostDto.RequestData postDto) {
    return ResponseEntity.ok(postService.storePost(userId, postDto));
  }

  /**
   * Putting a post to be updated.
   *
   * @param id the id for the post you want to update
   * @return a response entity with the updated post, hopefully the same as the one send
   */
  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(
      @Valid @RequestPart("post") PostDto postDto, @PathVariable long id) {
    return ResponseEntity.ok(postService.updatePost(postDto, id));
  }

  /**
   * A method for removing a post.
   *
   * @param id the id of the post that should be removed.
   * @return a response entity with a string of the result.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> removePost(@PathVariable long id) {
    postService.removePost(id);
    return ResponseEntity.ok("ok");
  }
}
