package com.arjen0203.codex.service.postservice.controllers;

import java.util.UUID;

import com.arjen0203.codex.domain.post.dto.CustomTrendingPostPage;
import com.arjen0203.codex.service.postservice.services.TrendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/trending")
@RequiredArgsConstructor
public class TrendingController {
  private final TrendingService trendingService;

  /**
   * Get all the posts (or at least a page).
   *
   * @return a page of posts
   */
  @GetMapping("/day")
  public ResponseEntity<CustomTrendingPostPage> trendingPostsDay(@RequestHeader UUID userId,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(trendingService.getTrendingPostsDay(userId, page, size));
  }

  /**
   * Get all the posts (or at least a page).
   *
   * @return a page of posts
   */
  @GetMapping("/week")
  public ResponseEntity<CustomTrendingPostPage> trendingPostsWeek(@RequestHeader UUID userId,
          @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(trendingService.getTrendingPostsWeek(userId, page, size));
  }

  /**
   * Get all the posts (or at least a page).
   *
   * @return a page of posts
   */
  @GetMapping("/month")
  public ResponseEntity<CustomTrendingPostPage> trendingPostsMonth(@RequestHeader UUID userId,
          @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(trendingService.getTrendingPostsMonth(userId, page, size));
  }

  /**
   * Get all the posts (or at least a page).
   *
   * @return a page of posts
   */
  @GetMapping("/year")
  public ResponseEntity<CustomTrendingPostPage> trendingPostsYear(@RequestHeader UUID userId,
          @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(trendingService.getTrendingPostsYear(userId, page, size));
  }
}
