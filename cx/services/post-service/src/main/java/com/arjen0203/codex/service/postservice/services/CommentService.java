package com.arjen0203.codex.service.postservice.services;

import java.time.Instant;
import java.util.UUID;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.CommentDto;
import com.arjen0203.codex.domain.post.entity.Comment;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.service.postservice.repositories.CommentRepository;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/** The service for dealing with all the comment actions. */
@Service
@AllArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final ModelMapper modelMapper;
  private final PostService postService;

  /**
   * The method for getting all the comments.
   *
   * @return a list of all the comments.
   */
  public Page<CommentDto> getAllComments(long postId, int pageNr, int size) {
    var commentPage = commentRepository.findAllByPostId(postId, PageRequest.of(pageNr, size, Sort.by("createdAt").descending()));
    return commentPage.map(f -> modelMapper.map(f, CommentDto.class));
  }

  /**
   * A method for storing a new comment.
   *
   * @param commentDto the comment to store
   * @return the comment dto of the stored comment
   */
  public CommentDto storeComment(UUID user, CommentDto.RequestData commentDto, long postId) {
    val post = modelMapper.map(postService.getPostById(postId), Post.class);

    val comment = modelMapper.map(commentDto, Comment.class);
    comment.setUser(user);
    comment.setCreatedAt(Instant.now());
    comment.setPost( post);

    commentRepository.save(comment);
    try {
      return modelMapper.map(comment, CommentDto.class);
    } catch (DataIntegrityViolationException ex) {
      throw new ConflictException("comment could not be saved");
    }
  }

  /**
   * The method for updating a comment.
   *
   * <p>The method makes a comment entity based on the given dto. This entity is then stored.
   *
   * @param commentDto the dto of the updated comment.
   * @return the updated comment.
   */
  public CommentDto updateComment(CommentDto.RequestData commentDto, long id) {
    var oComment = commentRepository.findById(id);
    if (oComment.isEmpty()) {
      throw new NotFoundException("Comment");
    }

    var comment = oComment.get();
    comment.update(commentDto);

    return modelMapper.map(commentRepository.save(comment), CommentDto.class);
  }

  /**
   * The method for removing a comment.
   *
   * @param id the id of the comment that should be removed.
   */
  public void removeComment(long id) {
    try {
      commentRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException("Comment");
    }
  }

  public long getCommentCountOfPost(long id) {
    try {
      return commentRepository.getCommentCountByPostId(id);
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException("Post");
    }
  }
}
