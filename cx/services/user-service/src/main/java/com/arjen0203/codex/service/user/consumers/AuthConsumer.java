package com.arjen0203.codex.service.user.consumers;

import com.arjen0203.codex.core.rabbit.objects.Response;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.user.dto.CreateUser;
import com.arjen0203.codex.service.user.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.arjen0203.codex.core.rabbit.objects.Request;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthConsumer {
    private final Gson gson = new Gson();
    private final UserService userService;

    @RabbitListener(queues = "register-user")
    public String createUser(String request) {
        var message = gson.fromJson(request, Request.class);
        var user = message.getData(CreateUser.class);

        try {
            var newUser = userService.createUser(user);
            return gson.toJson(Response.success(newUser));
        } catch (ResponseStatusException err) {
            return gson.toJson(Response.error(err));
        }
    }

    @RabbitListener(queues = "get-user-by-email")
    public String getByEmail(String request) {
        var message = gson.fromJson(request, Request.class);
        var email = message.getData(String.class);

        var oUser = userService.getUserByEmail(email);
        if (oUser.isPresent()) return gson.toJson(Response.success(oUser.get()));
        return gson.toJson(Response.error(new NotFoundException("user")));
    }
}