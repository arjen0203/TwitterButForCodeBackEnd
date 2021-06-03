package com.arjen0203.codex.service.postservice.services;

import java.util.UUID;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.PostLikeDto;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.domain.post.entity.PostLike;
import com.arjen0203.codex.service.postservice.repositories.PostLikeRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostLikeService {
  private final PostLikeRepository postLikeRepository;
  private final PostService postService;
  private final ModelMapper modelMapper;

  /**
   * The method for getting all the comments.
   *
   * @return a list of all the comments.
   */
  public Page<PostLikeDto> getAllPostLikes(long postId, int pageNr, int size) {
    var postLikesPage = postLikeRepository.findAllByPostId(postId, PageRequest.of(pageNr, size));
    return postLikesPage.map(f -> modelMapper.map(f, PostLikeDto.class));
  }

  /**
   * Creates a new Project with user as owner.
   *
   * @param user uuid of user
   * @param postId id of liked post
   * @return created Project
   */
  public PostLikeDto storePostLike(UUID user, long postId) {
    val post = modelMapper.map(postService.getPostById(postId), Post.class);
    if (postLikeRepository.findByUserAndPostId(user, post.getId()).isPresent()) {
      throw new ConflictException("The post is already liked by this user");
    }

    val postLike = new PostLike();
    postLike.setUser(user);
    postLike.setPost(post);

    try {
      postLikeRepository.save(postLike);
    } catch (DataIntegrityViolationException ex) {
      throw new ConflictException("Could not create post");
    }

    return modelMapper.map(postLike, PostLikeDto.class);
  }

  /**
   * The method for removing a like.
   *
   * @param postId the id of the post where the like should be removed.
   */
  public void removePostLike(UUID userId, long postId) {
    var postLike = postLikeRepository.findByUserAndPostId(userId, postId);
    if (postLike.isEmpty()) throw new NotFoundException("Like");
    postLikeRepository.deleteById(postLike.get().getId());
  }

  public long getPostLikeCountOfPost(long id) {
    try {
      return postLikeRepository.getPostLikeCountByPostId(id);
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException("Post");
    }
  }

}
