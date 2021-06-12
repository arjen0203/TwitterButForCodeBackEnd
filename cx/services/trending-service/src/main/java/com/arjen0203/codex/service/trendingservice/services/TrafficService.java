package com.arjen0203.codex.service.trendingservice.services;

import com.arjen0203.codex.domain.trending.dto.TrendingPostDto;
import com.arjen0203.codex.service.trendingservice.repositories.TrafficRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrafficService {
    private final TrafficRepository trafficRepository;
    private final ModelMapper modelMapper;

    //updates every 5 minutes
    public Page<TrendingPostDto> getPageTrendingPostsDay() {

        return null;
    }

    //updates every hour
    public Page<TrendingPostDto> getPageTrendingPostsWeek() {
        return null;
    }

    //updates every day
    public Page<TrendingPostDto> getPageTrendingPostsMonth() {
        return null;
    }

    //updates every week
    public Page<TrendingPostDto> getPageTrendingPostsYear() {
        return null;
    }

    public Page<TrendingPostDto> getAllTrafficCounter(int pageNr, int size) {
        Page<TrendingPostDto> returnData = trafficRepository.getTrafficCounted(PageRequest.of(pageNr, size));
        return returnData;
    }
}
