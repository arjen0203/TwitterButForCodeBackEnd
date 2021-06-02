package com.arjen0203.codex.service.postservice.repositories;

import com.arjen0203.codex.domain.post.entity.Revision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RevisionRepository extends PagingAndSortingRepository<Revision, Long> {
    @Query("SELECT COUNT(r) FROM Revision r WHERE r.originalPost.id = :postId")
    long getRevisionCountByPostId(@Param("postId") long postId);
}
