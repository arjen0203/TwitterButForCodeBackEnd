package com.arjen0203.codex.service.trendingservice.consumers;

import com.arjen0203.codex.core.rabbit.objects.Request;
import com.arjen0203.codex.core.rabbit.objects.Response;
import com.arjen0203.codex.domain.core.general.exceptions.InternalServerException;
import com.arjen0203.codex.domain.trending.dto.RabbitTrafficDto;
import com.arjen0203.codex.domain.trending.dto.TrendingPostsRequest;
import com.arjen0203.codex.service.trendingservice.services.TrafficService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostConsumer {
    private final Gson gson = new Gson();
    private final TrafficService trafficService;

    @RabbitListener(queues = "post-like-traffic")
    public void handlePostLikeTraffic(String request) {
        var rabbitTraffic = getTrafficRequestData(request);
        trafficService.addPostLikeTraffic(rabbitTraffic);
    }

    @RabbitListener(queues = "post-comment-traffic")
    public void handlePostCommentTraffic(String request) {
        var rabbitTraffic = getTrafficRequestData(request);
        trafficService.addPostCommentTraffic(rabbitTraffic);
    }

    @RabbitListener(queues = "post-revision-traffic")
    public void handlePostRevisionTraffic(String request) {
        var rabbitTraffic = getTrafficRequestData(request);
        trafficService.addPostRevisionTraffic(rabbitTraffic);
    }

    @RabbitListener(queues = "trending-post-day")
    public String getPageTrendingPostsDay(String request) {
        var trendingRequest = getTrendingRequestData(request);

        try {
            var trendingPage = trafficService.getPageTrendingPostsDay(trendingRequest.getPageNr(),
                    trendingRequest.getPageSize());
            return gson.toJson(Response.success(trendingPage));
        } catch (Exception ex) {
            return gson.toJson(Response.error(new InternalServerException()));
        }
    }

    @RabbitListener(queues = "trending-post-week")
    public String getPageTrendingPostsWeek(String request) {
        var trendingRequest = getTrendingRequestData(request);

        try {
            var trendingPage = trafficService.getPageTrendingPostsWeek(trendingRequest.getPageNr(),
                    trendingRequest.getPageSize());
            return gson.toJson(Response.success(trendingPage));
        } catch (Exception ex) {
            return gson.toJson(Response.error(new InternalServerException()));
        }
    }

    @RabbitListener(queues = "trending-post-month")
    public String getPageTrendingPostsMonth(String request) {
        var trendingRequest = getTrendingRequestData(request);

        try {
            var trendingPage = trafficService.getPageTrendingPostsMonth(trendingRequest.getPageNr(),
                    trendingRequest.getPageSize());
            return gson.toJson(Response.success(trendingPage));
        } catch (Exception ex) {
            return gson.toJson(Response.error(new InternalServerException()));
        }
    }


    @RabbitListener(queues = "trending-post-year")
    public String getPageTrendingPostsYear(String request) {
        var trendingRequest = getTrendingRequestData(request);

        try {
            var trendingPage = trafficService.getPageTrendingPostsYear(trendingRequest.getPageNr(),
                    trendingRequest.getPageSize());
            return gson.toJson(Response.success(trendingPage));
        } catch (Exception ex) {
            return gson.toJson(Response.error(new InternalServerException()));
        }
    }

    public RabbitTrafficDto getTrafficRequestData(String request) {
        var message = gson.fromJson(request, Request.class);
        var rabbitTraffic = message.getData(RabbitTrafficDto.class);
        System.out.println("[x] received message " + message);
        return rabbitTraffic;
    }

    public TrendingPostsRequest getTrendingRequestData(String request) {
        var message = gson.fromJson(request, Request.class);
        var pageRequest = message.getData(TrendingPostsRequest.class);
        System.out.println("[x] received message " + message);
        return pageRequest;
    }
}