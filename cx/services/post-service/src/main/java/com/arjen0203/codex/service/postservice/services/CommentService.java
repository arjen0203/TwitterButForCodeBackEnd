package com.arjen0203.codex.service.postservice.services;

import java.time.Instant;
import java.util.UUID;

import com.arjen0203.codex.domain.post.dto.CommentDto;
import com.arjen0203.codex.domain.post.entity.Comment;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.service.postservice.repositories.CommentRepository;
import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

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
  public Page<CommentDto> getAllComments(int pageNr, int size) {
    var commentPage = commentRepository.findAll(PageRequest.of(pageNr, size));
    return commentPage.map(f -> modelMapper.map(f, CommentDto.class));
  }

  /**
   * The method to get a single comment.
   *
   * @param id the id of the desired comment.
   * @return the comment.
   */
  public CommentDto getComment(long id) {
    var comment = commentRepository.findById(id);
    if (comment.isPresent()) {
      return modelMapper.map(comment.get(), CommentDto.class);
    }
    throw new NotFoundException("Comment");
  }

  /**
   * A method for storing a new comment.
   *
   * @param commentDto the comment to store
   * @return the comment dto of the stored comment
   */
  public CommentDto storeComment(UUID user, CommentDto.RequestData commentDto, long postId) {
    val post = postService.getPostById(postId);
    if (post == null) throw new NotFoundException("Post");
    val comment = modelMapper.map(commentDto, Comment.class);
    comment.setPost(modelMapper.map(post, Post.class));
    comment.setUser(user);
    comment.setCreatedAt(Instant.now());

    try {
      commentRepository.save(comment);
    } catch (DataIntegrityViolationException ex) {
      throw new ConflictException("comment could not be saved");
    }

    return modelMapper.map(comment, CommentDto.class);
  }

  /**
   * The method for updating a comment.
   *
   * <p>The method makes a comment entity based on the given dto. This entity is then stored.
   *
   * @param commentDto the dto of the updated comment.
   * @return the updated comment.
   */
  public CommentDto updateComment(CommentDto commentDto, long id) {
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
    commentRepository.deleteById(id);
  }
}
