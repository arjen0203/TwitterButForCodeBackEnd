package com.arjen0203.codex.service.trendingservice.services;

import java.time.LocalDateTime;

import com.arjen0203.codex.domain.trending.dto.TrafficDto;
import com.arjen0203.codex.domain.trending.dto.TrendingPostDto;
import com.arjen0203.codex.domain.trending.entity.Traffic;
import com.arjen0203.codex.domain.trending.enums.TrafficType;
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

    public TrafficDto createTraffic() {
        Traffic traffic = new Traffic();
        traffic.setDateTime(LocalDateTime.now());
        traffic.setPostId(5);
        traffic.setType(TrafficType.POSTLIKE);

        return modelMapper.map(trafficRepository.save(traffic), TrafficDto.class);
    }

    public Page<TrendingPostDto> getAllTrafficCounter(int pageNr, int size) {
        LocalDateTime now = LocalDateTime.now();
        Page<TrendingPostDto> returnData = trafficRepository.getTrafficCounted(now.minusSeconds(10), now ,
                PageRequest.of(pageNr, size));
        return returnData;
    }
}
