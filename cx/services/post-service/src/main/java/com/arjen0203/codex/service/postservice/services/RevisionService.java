package com.arjen0203.codex.service.postservice.services;

import java.time.Instant;
import java.util.UUID;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.RevisionDto;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.domain.post.entity.Revision;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import com.arjen0203.codex.service.postservice.repositories.RevisionRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RevisionService {
  private final RevisionRepository revisionRepository;
  private final PostRepository postRepository;
  private final PostService postService;
  private final ModelMapper modelMapper;

  /**
   * Creates a new Project with user as owner.
   *
   * @param user uuid of user
   * @param revisionDto data used to create revision
   * @return created revision
   */
  public RevisionDto storeRevision(long postId, UUID user, RevisionDto.RequestData revisionDto) {
    val originalPost = postService.getPostById(postId);
    val revision = modelMapper.map(revisionDto, Revision.class);

    revision.setOriginalPost(originalPost);
    originalPost.getRevisions().add(revision);

    revision.getPost().setCreatedAt(Instant.now());
    revision.getPost().setAuthor(user);
    revision.getPost().setRevision(revision);

    try {
      postRepository.save(revision.getPost());
      revisionRepository.save(revision);
      postRepository.save(originalPost);
    } catch (DataIntegrityViolationException ex) {
      throw new ConflictException("Could not create revision");
    }
    val returnData = modelMapper.map(revision, RevisionDto.class);
    return returnData;
  }

  public long getRevisionCountOfPost(long id) {
    try {
      return revisionRepository.getRevisionCountByPostId(id);
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException("Post");
    }
  }
}
