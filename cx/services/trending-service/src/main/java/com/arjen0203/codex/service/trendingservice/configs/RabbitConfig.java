package com.arjen0203.codex.service.trendingservice.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue postsLikeTraffic() {
        return new Queue("post-like-traffic");
    }

    @Bean
    public Queue postsCommentTraffic() {
        return new Queue("post-comment-traffic");
    }

    @Bean
    public Queue postsRevisionTraffic() {
        return new Queue("post-revision-traffic");
    }
}
