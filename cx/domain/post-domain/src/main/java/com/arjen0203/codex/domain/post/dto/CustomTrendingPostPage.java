package com.arjen0203.codex.domain.post.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomTrendingPostPage {
    List<PostDto.PostReturn> content;
    int maxPages;
    int pageNumber;
    int pageSize;
}
