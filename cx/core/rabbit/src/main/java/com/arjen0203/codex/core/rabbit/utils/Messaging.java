package com.arjen0203.codex.core.rabbit.utils;

import com.arjen0203.codex.core.rabbit.objects.Request;
import com.arjen0203.codex.core.rabbit.objects.Response;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/** Helper utility that provides useful functions to send and receive with Rabbit. */
@Component
@RequiredArgsConstructor
public class Messaging {
  private final Gson gson;
  private final RabbitTemplate rabbitTemplate;

  public <T, U> U sendAndReceive(String queue, T data, Class<U> returnType) {
    String messageString = gson.toJson(new Request(data));
    System.out.println("[x] send message " + messageString);

    String responseMessage = (String) rabbitTemplate.convertSendAndReceive(queue, messageString);

    Response response = gson.fromJson(responseMessage, Response.class);
    System.out.println("[x] received message " + responseMessage);

    if (response.isSuccess()) return response.getData(returnType);
    throw response.getException();
  }

  public <T> void send(String queue, T data) {
    String messageString = gson.toJson(new Request(data));
    System.out.println("[x] send message " + messageString);

    rabbitTemplate.convertAndSend(queue, messageString);
  }
}
