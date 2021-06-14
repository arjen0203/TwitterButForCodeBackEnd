package com.arjen0203.codex.domain.trending.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendingPostsRequest {
    int pageNr;
    int pageSize;
}
