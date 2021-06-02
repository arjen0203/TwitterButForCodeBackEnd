package com.arjen0203.codex.service.postservice.repositories;

import com.arjen0203.codex.domain.post.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

  @Query("select c from Comment c where c.post.id = :postId")
  Page<Comment> findAllByPostId(@Param("postId") Long postId, final Pageable pageable);

  @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
  long getCommentCountByPostId(@Param("postId") long postId);
}
