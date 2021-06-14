package com.arjen0203.codex.domain.trending.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.arjen0203.codex.domain.trending.enums.TrafficType;
import lombok.Data;

@Entity
@Data
public class Traffic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private TrafficType type;

    private long postId;
    private LocalDateTime dateTime;
}
