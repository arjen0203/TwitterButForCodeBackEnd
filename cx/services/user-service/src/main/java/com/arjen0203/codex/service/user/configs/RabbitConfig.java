package com.arjen0203.codex.service.user.configs;

import com.arjen0203.codex.core.rabbit.utils.Messaging;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue RegisterUser() {
        return new Queue("register-user");
    }

    @Bean
    public Queue getUserByEmail() {
        return new Queue("get-user-by-email");
    }

    @Bean
    public Queue removePostsUser() {
        return new Queue("remove-data-user");
    }
}
