package com.highcrit.flowerpower.service.user.configs;

import com.highcrit.flowerpower.core.kafka.configs.KafkaConfig;
import com.highcrit.flowerpower.core.service.configs.BaseConfig;
import com.highcrit.flowerpower.core.service.configs.PersistenceConfig;
import com.highcrit.flowerpower.domain.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Configuration for the User-Service. */
@Configuration
@Import({BaseConfig.class, PersistenceConfig.class, KafkaConfig.class})
@EntityScan(basePackageClasses = {User.class})
public class UserServiceConfig {
  /**
   * Creates a default instance of ModelMapper for the entirety of this service.
   *
   * @return ModelMapper instance
   * @see ModelMapper
   */
  @Bean
  ModelMapper modelMapper() {
    var modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setSkipNullEnabled(true);
    return modelMapper;
  }
}
