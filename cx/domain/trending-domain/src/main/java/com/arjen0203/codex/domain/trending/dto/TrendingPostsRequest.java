package com.arjen0203.codex.domain.trending.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class TrendingPostsRequest {
    int pageNr;
    int pageSize;
}
