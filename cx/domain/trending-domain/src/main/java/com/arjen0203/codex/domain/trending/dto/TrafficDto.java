package com.arjen0203.codex.domain.trending.dto;

import java.time.LocalDateTime;

import com.arjen0203.codex.domain.trending.enums.TrafficType;
import lombok.Data;

@Data
public class TrafficDto {
    private TrafficType type;
    private long postId;
    private LocalDateTime dateTime;
}
