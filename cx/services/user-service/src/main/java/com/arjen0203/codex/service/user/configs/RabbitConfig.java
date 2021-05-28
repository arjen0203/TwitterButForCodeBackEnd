package com.arjen0203.codex.service.user.configs;

import com.arjen0203.codex.service.user.consumers.AuthConsumer;
import com.arjen0203.codex.service.user.service.UserService;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue registerUser() {
        return new Queue("register-user");
    }

    @Bean
    public Queue getUserByEmail() {
        return new Queue("get-user-by-email");
    }

    @Bean
    public AuthConsumer authConsumer(UserService userService) {
        return new AuthConsumer(userService);
    }
}