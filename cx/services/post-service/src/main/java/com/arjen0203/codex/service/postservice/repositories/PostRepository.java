package com.arjen0203.codex.service.postservice.repositories;

import com.arjen0203.codex.domain.post.entity.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

}
