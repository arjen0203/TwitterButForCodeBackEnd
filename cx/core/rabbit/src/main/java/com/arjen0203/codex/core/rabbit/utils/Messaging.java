package com.arjen0203.codex.core.rabbit.utils;

import com.arjen0203.codex.core.rabbit.objects.Message;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/** Helper utility that provides useful functions to send and receive with Rabbit. */
@Component
@RequiredArgsConstructor
public class Messaging {
  private final Gson gson;
  private final RabbitTemplate rabbitTemplate;

  public <T, U> U sendAndReceive(String queue, T data, Class<U> returnType) {
    String messageString = gson.toJson(new Message(data));
    System.out.println("[x] send message " + messageString);

    String response = (String) rabbitTemplate.convertSendAndReceive(queue, messageString);

    Message responseMessage = gson.fromJson(response, Message.class);
    System.out.println("[x] send message " + responseMessage);

    return responseMessage.getData(returnType);
  }

  public <T> void send(String queue, T data) {
    String messageString = gson.toJson(new Message(data));
    System.out.println("[x] send message " + messageString);

    rabbitTemplate.convertAndSend(queue, messageString);
  }
}
