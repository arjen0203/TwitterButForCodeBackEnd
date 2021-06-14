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

    @Bean
    public Queue trendingPostDay() {
        return new Queue("trending-post-day");
    }

    @Bean
    public Queue trendingPostWeek() {
        return new Queue("trending-post-week");
    }

    @Bean
    public Queue trendingPostMonth() {
        return new Queue("trending-post-month");
    }

    @Bean
    public Queue trendingPostYear() {
        return new Queue("trending-post-year");
    }
}
