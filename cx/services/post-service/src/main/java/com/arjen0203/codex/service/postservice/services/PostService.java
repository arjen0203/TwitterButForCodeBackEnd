package com.arjen0203.codex.service.postservice.services;

import java.time.Instant;
import java.util.UUID;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.PostDto;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    /**
     * Retrieves a specific Project by id.
     *
     * @param id id of project
     * @return Project
     */
    public PostDto getPostById(long id) {
        val oPost = postRepository.findById(id);
        if (oPost.isEmpty()) {
            // Do not remove the throwing of this exception
            throw new NotFoundException();
        }
        return modelMapper.map(oPost.get(), PostDto.class);
    }

    /**
     * Creates a new Project with user as owner.
     *
     * @param user uuid of user
     * @param postDto data used to create Project
     * @return created Project
     */
    public PostDto createPost(UUID user, PostDto.RequestData postDto) {
        val post = modelMapper.map(postDto, Post.class);

        post.setAuthor(user);
        post.setCreatedAt(Instant.now());

        try {
            postRepository.save(post);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Could not create post");
        }

        return modelMapper.map(post, PostDto.class);
    }
}
