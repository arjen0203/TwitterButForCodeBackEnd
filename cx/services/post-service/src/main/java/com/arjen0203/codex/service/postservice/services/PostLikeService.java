package com.arjen0203.codex.service.postservice.services;

import java.util.UUID;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.PostLikeDto;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.domain.post.entity.PostLike;
import com.arjen0203.codex.service.postservice.repositories.PostLikeRepository;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    /**
     * Retrieves a specific Project by id.
     *
     * @param id id of project
     * @return Project
     */
    public PostLikeDto getPostLikeDtoById(long id) {
        return modelMapper.map(getPostLikeById(id), PostLikeDto.class);
    }

    /**
     * Retrieves a specific Project by id.
     *
     * @param id id of project
     * @return Project
     */
    public PostLike getPostLikeById(long id) {
        val oPostLike = postLikeRepository.findById(id);
        return oPostLike.orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new Project with user as owner.
     *
     * @param user uuid of user
     * @param postId id of liked post
     * @return created Project
     */
    public PostLikeDto storePostLike(UUID user, long postId) {
        //todo add check if the user already has a post liked
        val post = postService.getPostById(postId);

        val postLike = new PostLike();
        postLike.setUser(user);

        post.getPostLikes().add(postLike);

        try {
            postRepository.save(post);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Could not create post");
        }

        return modelMapper.map(postLike, PostLikeDto.class);
    }

    /**
     * The method for removing a like.
     *
     * @param id the id of the like that should be removed.
     */
    public void removePostLike(long id) {
        postLikeRepository.deleteById(id);
    }
}
