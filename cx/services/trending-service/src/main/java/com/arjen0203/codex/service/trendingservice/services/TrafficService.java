package com.arjen0203.codex.service.trendingservice.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.arjen0203.codex.domain.trending.dto.RabbitTrafficDto;
import com.arjen0203.codex.domain.trending.dto.TrafficDto;
import com.arjen0203.codex.domain.trending.dto.TrendingPostDto;
import com.arjen0203.codex.domain.trending.dto.TrendingPostPageDto;
import com.arjen0203.codex.domain.trending.entity.Traffic;
import com.arjen0203.codex.domain.trending.enums.TrafficType;
import com.arjen0203.codex.service.trendingservice.repositories.TrafficRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrafficService {
    private final TrafficRepository trafficRepository;
    private final ModelMapper modelMapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final CacheManager cacheManager;

    @Cacheable(value = "trending-day", key = "{#pageNr, #size}")
    public TrendingPostPageDto getPageTrendingPostsDay(int pageNr, int size) {
        LocalDateTime now = LocalDateTime.now();
        Page<TrendingPostDto> returnData = trafficRepository.getTrafficCounted(now.minusDays(1), now ,
                PageRequest.of(pageNr, size));

        return new TrendingPostPageDto(returnData);
    }

    @Cacheable(value = "trending-week", key = "{#pageNr, #size}")
    public TrendingPostPageDto getPageTrendingPostsWeek(int pageNr, int size) {
        LocalDateTime now = LocalDateTime.now();
        Page<TrendingPostDto> returnData = trafficRepository.getTrafficCounted(now.minusWeeks(1), now ,
                PageRequest.of(pageNr, size));
        return new TrendingPostPageDto(returnData);
    }

    @Cacheable(value = "trending-month", key = "{#pageNr, #size}")
    public TrendingPostPageDto getPageTrendingPostsMonth(int pageNr, int size) {
        LocalDateTime now = LocalDateTime.now();
        Page<TrendingPostDto> returnData = trafficRepository.getTrafficCounted(now.minusMonths(1), now ,
                PageRequest.of(pageNr, size));
        return new TrendingPostPageDto(returnData);
    }

    @Cacheable(value = "trending-year", key = "{#pageNr, #size}")
    public TrendingPostPageDto getPageTrendingPostsYear(int pageNr, int size) {
        LocalDateTime now = LocalDateTime.now();
        Page<TrendingPostDto> returnData = trafficRepository.getTrafficCounted(now.minusYears(1), now ,
                PageRequest.of(pageNr, size));
        return new TrendingPostPageDto(returnData);
    }

    public TrafficDto createTraffic() {
        Traffic traffic = new Traffic();
        traffic.setDateTime(LocalDateTime.now());
        traffic.setPostId(5);
        traffic.setType(TrafficType.POSTLIKE);

        return modelMapper.map(trafficRepository.save(traffic), TrafficDto.class);
    }

    public void addPostLikeTraffic(RabbitTrafficDto trafficDto) {
        var traffic = modelMapper.map(trafficDto, Traffic.class);
        traffic.setDateTime(LocalDateTime.parse(trafficDto.getDateTimeString(), formatter));
        traffic.setType(TrafficType.POSTLIKE);

        addTrafficToCacheQueue(traffic);
    }

    public void addPostCommentTraffic(RabbitTrafficDto trafficDto) {
        var traffic = modelMapper.map(trafficDto, Traffic.class);
        traffic.setDateTime(LocalDateTime.parse(trafficDto.getDateTimeString(), formatter));
        traffic.setType(TrafficType.POSTCOMMENT);

        addTrafficToCacheQueue(traffic);
    }

    public void addPostRevisionTraffic(RabbitTrafficDto trafficDto) {
        var traffic = modelMapper.map(trafficDto, Traffic.class);
        traffic.setDateTime(LocalDateTime.parse(trafficDto.getDateTimeString(), formatter));
        traffic.setType(TrafficType.POSTREVISION);

        addTrafficToCacheQueue(traffic);
    }

    public void addTrafficToCacheQueue(Traffic traffic) {

        trafficRepository.save(traffic);
    }

    public void addQueueToTraffic() {

    }
}
