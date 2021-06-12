//package com.arjen0203.codex.service.trendingservice.consumers;
//
//
//import com.google.gson.Gson;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PostConsumer {
//    private final Gson gson = new Gson();
//
//    @RabbitListener(queues = "post-like-traffic")
//    public void handlePostLikeTraffic(String request) {
//
//    }
//
//    @RabbitListener(queues = "post-comment-traffic")
//    public void handlePostCommentTraffic(String request) {
//
//    }
//
//    @RabbitListener(queues = "post-revision-traffic")
//    public void handlePostRevisionTraffic(String request) {
//
//    }
//}