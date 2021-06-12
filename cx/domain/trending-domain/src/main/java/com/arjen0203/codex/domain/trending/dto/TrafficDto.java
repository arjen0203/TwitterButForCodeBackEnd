package com.arjen0203.codex.domain.trending.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class TrafficDto {
    private int type;
    private long postId;
    private Instant dateTime;
}
