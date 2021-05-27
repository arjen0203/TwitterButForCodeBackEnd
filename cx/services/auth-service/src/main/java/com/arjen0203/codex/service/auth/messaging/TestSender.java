package com.arjen0203.codex.service.auth.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public void send() {
        String message = "Hello World!";
        System.out.println("queue" + queue.getName());
        System.out.println("template" + template);
        template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
