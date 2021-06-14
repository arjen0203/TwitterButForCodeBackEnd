package com.arjen0203.codex.service.trendingservice.repositories;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.arjen0203.codex.domain.trending.dto.TrendingPostDto;
import com.arjen0203.codex.domain.trending.entity.Traffic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface TrafficRepository extends PagingAndSortingRepository<Traffic, Long> {
    @Query("select new com.arjen0203.codex.domain.trending.dto.TrendingPostDto(t.postId, count(t)) from Traffic t "
            + "where t.dateTime between :startDate and :endDate "
            + "group by t.postId order by count(t) desc")
    Page<TrendingPostDto> getTrafficCounted(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
