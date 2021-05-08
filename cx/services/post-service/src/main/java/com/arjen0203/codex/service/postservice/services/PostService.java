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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    /**
     * The method for getting all the posts.
     *
     * @return a list of all the posts.
     */
    public Page<PostDto> getAllPosts(int pageNr, int size) {
        var postPage = postRepository.findAll(PageRequest.of(pageNr, size));
        return postPage.map(f -> modelMapper.map(f, PostDto.class));
    }

    /**
     * Retrieves a specific Project by id.
     *
     * @param id id of project
     * @return Project
     */
    public PostDto getPostById(long id) {
        val oPost = postRepository.findById(id);
        if (oPost.isEmpty()) {
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
    public PostDto storePost(UUID user, PostDto.RequestData postDto) {
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
        if (oPost.isEmpty()) {
            throw new NotFoundException("Post");
        }

        var post = oPost.get();
        post.update(postDto);

        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    /**
     * The method for removing a post.
     *
     * @param id the id of the post that should be removed.
     */
    public void removePost(long id) {
        postRepository.deleteById(id);
    }
}
