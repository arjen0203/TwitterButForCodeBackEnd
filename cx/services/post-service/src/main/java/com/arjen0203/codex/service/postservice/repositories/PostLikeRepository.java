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

  @Query("select p from PostLike p where p.post.id = :postId and p.user = :user")
  Optional<PostLike> findByUserAndPostId(@Param("user") UUID user, @Param("postId") long postId);

  @Query("select p from PostLike p where p.post.id = :postId")
  Page<PostLike> findAllByPostId(@Param("postId") Long postId, final Pageable pageable);

  @Query("SELECT COUNT(p) FROM PostLike p WHERE p.post.id = :postId")
  long getPostLikeCountByPostId(@Param("postId") long postId);
}
