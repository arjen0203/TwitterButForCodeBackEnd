package com.arjen0203.codex.service.user.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "register")
public class AuthReceiver {
    @RabbitHandler
    public void receiveRegister(String in) {
        System.out.println(" [x] Received '" + in + "'");
    }
}