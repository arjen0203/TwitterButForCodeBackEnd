package com.arjen0203.codex.service.postservice.configs;

import com.arjen0203.codex.core.rabbit.utils.Messaging;
import com.arjen0203.codex.domain.core.general.errorhandlers.RestErrorHandler;
import com.arjen0203.codex.domain.post.entity.Comment;
import com.arjen0203.codex.domain.post.entity.ContentBlock;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.domain.post.entity.PostLike;
import com.arjen0203.codex.domain.post.entity.Revision;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Main configuration for the project service. */
@Configuration
@Import({RestErrorHandler.class, Messaging.class})
@EntityScan(
    basePackageClasses = {
      Comment.class,
      ContentBlock.class,
      PostLike.class,
      Post.class,
      Revision.class
    })
public class ProjectAppConfiguration {
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
}
