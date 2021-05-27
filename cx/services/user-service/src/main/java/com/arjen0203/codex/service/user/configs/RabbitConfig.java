package com.arjen0203.codex.service.user.configs;

import com.arjen0203.codex.service.user.messaging.AuthReceiver;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue register() {
        return new Queue("register");
    }

    @Bean
    public AuthReceiver receiver() {
        return new AuthReceiver();
    }
}