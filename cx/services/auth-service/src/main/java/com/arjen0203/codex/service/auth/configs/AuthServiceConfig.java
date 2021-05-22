package com.arjen0203.codex.service.auth.configs;

import java.util.Arrays;

import com.arjen0203.codex.domain.core.general.errorhandlers.RestErrorHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Configuration for the AuthService. */
@Configuration
@Import({RestErrorHandler.class})
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
