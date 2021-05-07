package com.arjen0203.codex.service.postservice.repositories;

import com.arjen0203.codex.domain.post.entity.Revision;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RevisionRepository extends PagingAndSortingRepository<Revision, Long> {

}
