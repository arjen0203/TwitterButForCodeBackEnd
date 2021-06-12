package com.arjen0203.codex.domain.trending.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class RabbitTrafficDto {
    private long postId;
    private Instant dateTime;
}
