package com.arjen0203.codex.service.postservice.repositories;

import com.arjen0203.codex.domain.post.entity.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

}
