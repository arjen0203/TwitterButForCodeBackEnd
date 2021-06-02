package com.arjen0203.codex.service.postservice.services;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.PostDto;
import com.arjen0203.codex.domain.post.entity.ContentBlock;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.service.postservice.repositories.CommentRepository;
import com.arjen0203.codex.service.postservice.repositories.PostLikeRepository;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import com.arjen0203.codex.service.postservice.repositories.RevisionRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final PostLikeRepository postLikeRepository;
  private final RevisionRepository revisionRepository;
  private final ModelMapper modelMapper;

  /**
   * The method for getting all the posts.
   *
   * @return a list of all the posts.
   */
  public Page<PostDto.PostReturn> getAllPosts(UUID userId, int pageNr, int size) {
    var postPage = postRepository.findAll(PageRequest.of(pageNr, size, Sort.by("createdAt").descending()));
    return createPageOfPostDto(userId, postPage);
  }

  /**
   * The method for getting all the posts.
   *
   * @return a list of all the posts.
   */
  public Page<PostDto.PostReturn> getAllUserPosts(UUID userId, UUID postUserId, int pageNr, int size) {
    var postPage = postRepository.findAllPostByUserId(postUserId, PageRequest.of(pageNr, size,
            Sort.by("createdAt").descending()));
    return createPageOfPostDto(userId, postPage);
  }

  public <T> Page<PostDto.PostReturn> createPageOfPostDto(UUID user, Page<T> postPage) {
    return postPage.map(f -> {
      val returnData = modelMapper.map(f, PostDto.PostReturn.class);
      //todo make this more efficient (possibly normal sql request)
      returnData.setCommentsCount(commentRepository.getCommentCountByPostId(returnData.getId()));
      returnData.setPostLikesCount(postLikeRepository.getPostLikeCountByPostId(returnData.getId()));
      returnData.setRevisionsCount(revisionRepository.getRevisionCountByPostId(returnData.getId()));
      System.out.println(user);
      System.out.println(returnData.getId());
      returnData.setLiked(postLikeRepository.findByUserAndPostId(user, returnData.getId()).isPresent());
      return returnData;
    });
  }

  /**
   * Retrieves a specific Project by id.
   *
   * @param id id of project
   * @return Project
   */
  public PostDto.PostReturn getPostDtoById(long id) {
    val post = getPostById(id);
    val returnData = modelMapper.map(post, PostDto.PostReturn.class);
    returnData.setCommentsCount(post.getComments().size());
    returnData.setPostLikesCount(post.getPostLikes().size());
    returnData.setRevisionsCount(post.getRevisions().size());

    return returnData;
  }

  public Post getPostById(long id) {
    val oPost = postRepository.findById(id);

    return oPost.orElseThrow(NotFoundException::new);
  }

  /**
   * Creates a new Project with user as owner.
   *
   * @param user uuid of user
   * @param postDto data used to create Project
   * @return created Project
   */
  public PostDto storePost(UUID user, PostDto.RequestData postDto) {
    val post = modelMapper.map(postDto, Post.class);

    post.setAuthor(user);
    post.setCreatedAt(Instant.now());

    Set<ContentBlock> contentBlocks =
        new HashSet(
            postDto.getContentBlocks().stream()
                .map(contentBlockDto -> modelMapper.map(contentBlockDto, ContentBlock.class))
                .collect(Collectors.toList()));

    post.setContentBlocks(contentBlocks);

    try {
      return modelMapper.map(postRepository.save(post), PostDto.class);
    } catch (DataIntegrityViolationException ex) {
      throw new ConflictException("Could not create post");
    }
  }

  /**
   * The method for updating a post.
   *
   * <p>The method makes a post entity based on the given dto. This entity is then stored.
   *
   * @param postDto the dto of the updated post.
   * @return the updated post.
   */
  public PostDto updatePost(PostDto postDto, long id) {
    var oPost = postRepository.findById(id);

    var post = oPost.orElseThrow(NotFoundException::new);
    post.update(postDto);

    return modelMapper.map(postRepository.save(post), PostDto.class);
  }

  /**
   * The method for removing a post.
   *
   * @param id the id of the post that should be removed.
   */
  public void removePost(long id) {
    try {
      postRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException("Post");
    }
  }
}
