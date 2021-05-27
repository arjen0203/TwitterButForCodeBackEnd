package com.arjen0203.codex.service.auth.configs;

import com.arjen0203.codex.service.auth.messaging.TestSender;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue register() {
        return new Queue("Register");
    }
}