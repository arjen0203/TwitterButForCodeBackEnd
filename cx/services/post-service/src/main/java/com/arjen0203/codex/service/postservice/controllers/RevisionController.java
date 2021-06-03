package com.arjen0203.codex.service.postservice.controllers;

import java.util.UUID;

import javax.validation.Valid;

import com.arjen0203.codex.domain.post.dto.PostDto;
import com.arjen0203.codex.domain.post.dto.RevisionDto;
import com.arjen0203.codex.service.postservice.services.RevisionService;
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
public class RevisionController {
  private final RevisionService revisionService;
  
  /**
   * Posting a post to be saved.
   *
   * @param revisionDto the post to be stored
   * @param userId the uuid of the user making the post
   * @return a response entity with the created post
   */
  @PostMapping("/{postId}/revisions")
  public ResponseEntity<RevisionDto> createRevision(
      @PathVariable long postId, @RequestHeader UUID userId, @RequestBody RevisionDto.RequestData revisionDto) {
    return ResponseEntity.ok(revisionService.storeRevision(postId, userId, revisionDto));
  }

  /**
   * getting a page of revision posts.
   *
   * @param postId the post whos revisions need to be gotten
   * @return a page containing the revision posts
   */
  @GetMapping("/{postId}/revisions")
  public ResponseEntity<Page<PostDto.RevisionReferenceReturn>> getAllRevisionsOfPost(
          @PathVariable long postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(revisionService.getAllRevisionsOfPost(postId, page, size));
  }
}
