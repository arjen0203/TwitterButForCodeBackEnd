package com.arjen0203.codex.service.trendingservice.consumers;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostConsumer {
    private final Gson gson = new Gson();

    @RabbitListener(queues = "remove-data-user")
    public void removeUserPosts(String request) {

    }

}