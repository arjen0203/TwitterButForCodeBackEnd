package com.arjen0203.codex.service.postservice.controllers;

import java.util.UUID;

import javax.validation.Valid;

import com.arjen0203.codex.domain.post.dto.RevisionDto;
import com.arjen0203.codex.service.postservice.services.RevisionService;
import lombok.RequiredArgsConstructor;
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
}
