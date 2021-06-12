package com.arjen0203.codex.domain.trending.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RabbitTrafficDto {
    private long postId;
    private LocalDateTime dateTime;
}
