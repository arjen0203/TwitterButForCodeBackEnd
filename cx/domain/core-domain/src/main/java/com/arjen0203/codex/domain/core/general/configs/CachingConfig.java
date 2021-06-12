package com.arjen0203.codex.domain.core.general.configs;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/** Base configuration for all services making use of caching. */
@Configuration
@EnableCaching
@PropertySource("classpath:caching.properties")
public class CachingConfig {}
