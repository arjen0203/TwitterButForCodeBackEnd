package com.arjen0203.codex.domain.trending.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendingPostPageDto {
    private Page<TrendingPostDto> trendingPostPage;
}
