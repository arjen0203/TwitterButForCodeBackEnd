package com.arjen0203.codex.service.postservice.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue removePostsUser() {
        return new Queue("remove-data-user");
    }
}
