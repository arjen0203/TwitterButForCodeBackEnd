package com.arjen0203.codex.service.postservice.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.arjen0203.codex.domain.post.interfaces.IPost;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.domain.post.interfaces.IPostIncludingRevisions;
import com.arjen0203.codex.domain.post.interfaces.IRevisionPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    @Query("select p from Post p where p.author =:userId")
    Page<IPost> findAllPostByUserId(@Param("userId") UUID userId, final Pageable pageable);

    @Modifying
    @Query("delete from Post p where p.author = :userId")
    void deleteAllPostsByUser(@Param("userId") UUID userId);

    @Query("select p from Post p where p.id =:postId")
    Optional<IPost> findIPostById(@Param("postId") long id);

    @Query("select p from Post p where p.id =:postId")
    Optional<IPostIncludingRevisions> findIPostByIdWithRevisions(@Param("postId") long id);

    @Query("select p from Post p where p.revision.originalPost.id =:postId")
    Page<IRevisionPost> findAllRevisionPostByOriginalPostId(@Param("postId") long postId, final Pageable pageable);

    @Query("select p from Post p where p.id =:postIds")
    Page<IPost> findAllIPostByIds(@Param("postIds") List<Long> inventoryIdList, Pageable pageable);
}
