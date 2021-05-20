package com.arjen0203.codex.service.postservice.services;

import java.time.Instant;
import java.util.UUID;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.RevisionDto;
import com.arjen0203.codex.domain.post.entity.Revision;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import com.arjen0203.codex.service.postservice.repositories.RevisionRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RevisionService {
    private final RevisionRepository revisionRepository;
    private final ModelMapper modelMapper;


    /**
     * Retrieves a specific revision by id.
     *
     * @param id id of revision
     * @return Revision
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
     * @param revisionDto data used to create revision
     * @return created revision
     */
    public RevisionDto storeRevision(UUID user, RevisionDto.RequestData revisionDto) {
        val revision = modelMapper.map(revisionDto, Revision.class);

        revision.getPost().setAuthor(user);
        revision.getPost().setCreatedAt(Instant.now());

        try {
            revisionRepository.save(revision);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Could not create revision");
        }

        return modelMapper.map(revision, RevisionDto.class);
    }

    /**
     * The method for updating a revision.
     *
     * <p>The method makes a revision entity based on the given dto. This entity is then stored.
     *
     * @param revisionDto the dto of the updated revision.
     * @return the updated revision.
     */
    public RevisionDto updateRevision(RevisionDto revisionDto, long id) {
        var oRevision = revisionRepository.findById(id);
        if (oRevision.isEmpty()) {
            throw new NotFoundException("Revision");
        }

        var revision = oRevision.get();
        revision.update(revisionDto);

        return modelMapper.map(revisionRepository.save(revision), RevisionDto.class);
    }

    /**
     * The method for removing a revision.
     *
     * @param id the id of the revision that should be removed.
     */
    public void removeRevision(long id) {
        revisionRepository.deleteById(id);
    }
}
