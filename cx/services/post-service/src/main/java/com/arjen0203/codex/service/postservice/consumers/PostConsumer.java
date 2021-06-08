package com.arjen0203.codex.service.postservice.consumers;

import com.arjen0203.codex.core.rabbit.objects.Request;
import com.arjen0203.codex.domain.user.dto.UserDto;
import com.arjen0203.codex.service.postservice.services.PostService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostConsumer {
    private final Gson gson = new Gson();
    private final PostService postService;

    @RabbitListener(queues = "remove-posts-user")
    public void removeUserPosts(String request) {
        var message = gson.fromJson(request, Request.class);
        var user = message.getData(UserDto.Remove.class);
        System.out.println("[x] received message " + message);
        postService.removeUserPosts(user.getId());
    }

}