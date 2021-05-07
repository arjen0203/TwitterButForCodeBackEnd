package com.arjen0203.codex.service.postservice.repositories;

import com.arjen0203.codex.domain.post.entity.ContentBlock;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContentBlockRepository extends PagingAndSortingRepository<ContentBlock, Long> {

}
