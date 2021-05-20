package com.arjen0203.codex.domain.post.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.arjen0203.codex.domain.post.dto.PostDto;
import com.arjen0203.codex.domain.post.dto.RevisionDto;
import lombok.Data;

@Data
@Entity
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Post post;

    public void update(RevisionDto revisionDto) {
        this.post.setTitle(revisionDto.getPost().getTitle());
        //todo add contentblocks updating
    }
}
