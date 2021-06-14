package com.arjen0203.codex.domain.trending.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendingPostPageDto {
    private List<TrendingPostDto> trendingPosts;
    int maxPages;
    int pageNumber;
    int pageSize;

    public TrendingPostPageDto(Page<TrendingPostDto> trendingPostDtoPage) {
        this.trendingPosts = trendingPostDtoPage.getContent();
        this.maxPages = trendingPostDtoPage.getTotalPages();
        this.pageNumber = trendingPostDtoPage.getNumber();
        this.pageSize = trendingPosts.size();
    }
}
