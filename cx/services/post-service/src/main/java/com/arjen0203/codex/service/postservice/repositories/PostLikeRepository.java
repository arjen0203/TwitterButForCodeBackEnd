package com.arjen0203.codex.service.postservice.repositories;

import com.arjen0203.codex.domain.post.entity.PostLike;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostLikeRepository extends PagingAndSortingRepository<PostLike, Long> {

}
