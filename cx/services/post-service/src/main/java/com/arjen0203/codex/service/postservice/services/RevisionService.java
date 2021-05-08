package com.arjen0203.codex.service.postservice.services;

import java.time.Instant;
import java.util.UUID;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.PostDto;
import com.arjen0203.codex.domain.post.dto.RevisionDto;
import com.arjen0203.codex.domain.post.entity.Revision;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import com.arjen0203.codex.service.postservice.repositories.RevisionRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RevisionService {
    private final PostRepository postRepository;
    private final RevisionRepository revisionRepository;
    private final ModelMapper modelMapper;


    /**
     * Retrieves a specific Project by id.
     *
     * @param id id of project
     * @return Project
     */
    public RevisionDto getRevisionById(long id) {
        val oRevision = revisionRepository.findById(id);
        if (oRevision.isEmpty()) {
            throw new NotFoundException();
        }
        return modelMapper.map(oRevision.get(), RevisionDto.class);
    }

    /**
     * Creates a new Project with user as owner.
     *
     * @param user uuid of user
     * @param revisionDto data used to create Project
     * @return created Project
     */
    public RevisionDto storeRevision(UUID user, RevisionDto.RequestData revisionDto) {
        val revision = modelMapper.map(revisionDto, Revision.class);

        revision.getPost().setAuthor(user);
        revision.getPost().setCreatedAt(Instant.now());

        try {
            revisionRepository.save(revision);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Could not create post");
        }

        return modelMapper.map(revision, RevisionDto.class);
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
