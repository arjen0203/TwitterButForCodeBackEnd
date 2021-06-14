package com.arjen0203.codex.service.trendingservice.configs;

import java.time.LocalDateTime;

import com.arjen0203.codex.domain.core.general.configs.CachingConfig;
import com.arjen0203.codex.domain.core.general.errorhandlers.RestErrorHandler;

import com.arjen0203.codex.domain.trending.entity.Traffic;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;

/** Main configuration for the project service. */
@Configuration
@EntityScan(
        basePackageClasses = {
                Traffic.class,
        })
@Import({RestErrorHandler.class, CachingConfig.class})
public class TrendingAppConfiguration {
  /**
   * Creates a default instance of ModelMapper for the entirety of this service.
   *
   * @return ModelMapper instance
   * @see ModelMapper
   */
  @Bean
  ModelMapper modelMapper() {
    val modelMapper = new ModelMapper();
    modelMapper
        .getConfiguration()
        .setSkipNullEnabled(true)
        .setMatchingStrategy(MatchingStrategies.STRICT);
    return modelMapper;
  }

  //evicts cache every 5 minutes
  @CacheEvict(value = "trending-day", allEntries = true)
  @Scheduled(fixedDelay = 5 * 60 * 1000 ,  initialDelay = 5 * 60 * 1000)
  public void dayCacheEvict() {
    System.out.println("Flush trending day cache " + LocalDateTime.now());
  }

  //evicts cache every hour
  @CacheEvict(value = "trending-week", allEntries = true)
  @Scheduled(fixedDelay = 60 * 60 * 1000 ,  initialDelay = 60 * 60 * 1000)
  public void weekCacheEvict() {
    System.out.println("Flush trending day cache " + LocalDateTime.now());
  }

  //evicts cache every day
  @CacheEvict(value = "trending-month", allEntries = true)
  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000 ,  initialDelay = 24 * 60 * 60 * 1000)
  public void monthCacheEvict() {
    System.out.println("Flush trending day cache " + LocalDateTime.now());
  }

  //evicts cache every week
  @CacheEvict(value = "trending-day", allEntries = true)
  @Scheduled(fixedDelay = 7 * 24 * 60 * 60 * 1000 ,  initialDelay = 7 * 24 * 60 * 60 * 1000)
  public void yearCacheEvict() {
    System.out.println("Flush trending day cache " + LocalDateTime.now());
  }
}
