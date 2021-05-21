package com.highcrit.flowerpower.service.auth.configs;

import java.util.Arrays;

import com.highcrit.flowerpower.core.kafka.configs.KafkaConfig;
import com.highcrit.flowerpower.core.service.configs.BaseConfig;
import com.highcrit.flowerpower.core.service.configs.PersistenceConfig;
import com.highcrit.flowerpower.domain.auth.entity.InviteToken;
import com.highcrit.flowerpower.domain.auth.entity.ResetPassword;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Configuration for the AuthService. */
@Configuration
@Import({BaseConfig.class, PersistenceConfig.class, KafkaConfig.class})
@EntityScan(basePackageClasses = {ResetPassword.class, InviteToken.class})
public class AuthServiceConfig {
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

  /** Configures the Expiration time for a cookie. */
  @Bean("expiration")
  public int getExpiration(@Value("${auth.jwt.expiration}") String exp) {
    return Arrays.stream(exp.replace("*", "").split(" "))
        .mapToInt(s -> s.isBlank() ? 1 : Integer.parseInt(s))
        .reduce(1, (total, i) -> total * i);
  }
}
