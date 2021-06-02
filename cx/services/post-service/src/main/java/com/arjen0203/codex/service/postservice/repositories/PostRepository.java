package com.arjen0203.codex.service.postservice.repositories;

import java.util.UUID;

import javax.transaction.Transactional;

import com.arjen0203.codex.domain.post.interfaces.IPost;
import com.arjen0203.codex.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    @Query("select p from Post p where p.author =:userId")
    Page<IPost> findAllPostByUserId(@Param("userId") UUID userId, final Pageable pageable);
}
