package com.arjen0203.codex.service.postservice.repositories;

import java.util.Optional;
import java.util.UUID;

import com.arjen0203.codex.domain.post.entity.Comment;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.domain.post.entity.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends CrudRepository<PostLike, Long> {
  Optional<PostLike> findByUserAndPost(UUID user, Post post);

  @Query("select p from PostLike p where p.post.id = :postId")
  Page<PostLike> findAllByPostId(@Param("postId") Long postId, final Pageable pageable);
}
