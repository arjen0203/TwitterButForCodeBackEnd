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
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;

    /**
     * Retrieves a specific Project by id.
     *
     * @param id id of project
     * @return Project
     */
    public PostLikeDto getPostLikeById(long id) {
        val oPostLike = postLikeRepository.findById(id);
        if (oPostLike.isEmpty()) {
            throw new NotFoundException();
        }
        return modelMapper.map(oPostLike.get(), PostLikeDto.class);
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
        if (post == null) throw new NotFoundException("Post");
        val postLike = new PostLike();
        postLike.setPost(modelMapper.map(post, Post.class));
        postLike.setUser(user);

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
     * @param id the id of the like that should be removed.
     */
    public void removePostLike(long id) {
        postLikeRepository.deleteById(id);
    }
}
