package com.arjen0203.codex.service.postservice.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.arjen0203.codex.core.rabbit.utils.Messaging;
import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.post.dto.PostDto;
import com.arjen0203.codex.domain.post.dto.RevisionDto;
import com.arjen0203.codex.domain.post.entity.Post;
import com.arjen0203.codex.domain.post.entity.Revision;
import com.arjen0203.codex.domain.post.interfaces.IPost;
import com.arjen0203.codex.domain.trending.dto.TrendingPostDto;
import com.arjen0203.codex.domain.trending.dto.TrendingPostPageDto;
import com.arjen0203.codex.domain.trending.dto.TrendingPostsRequest;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import com.arjen0203.codex.service.postservice.repositories.RevisionRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrendingService {
  private final PostRepository postRepository;
  private final PostService postService;
  private final Messaging messaging;
  private final ModelMapper modelMapper;


  public Page<PostDto.PostReturn> getTrendingPostsDay(UUID user, int pageNr, int size) {
     var trendingPage = messaging.sendAndReceive("trending-post-day", new TrendingPostsRequest(pageNr, size),
             TrendingPostPageDto.class).getTrendingPostPage();

      return convertRequestToListGetPostsAndConvertToDTO(user, trendingPage);
  }

  public Page<PostDto.PostReturn> getTrendingPostsWeek(UUID user, int pageNr, int size) {
    var trendingPage = messaging.sendAndReceive("trending-post-week", new TrendingPostsRequest(pageNr, size),
            TrendingPostPageDto.class).getTrendingPostPage();

      return convertRequestToListGetPostsAndConvertToDTO(user, trendingPage);
  }

  public Page<PostDto.PostReturn> getTrendingPostsMonth(UUID user, int pageNr, int size) {
    var trendingPage = messaging.sendAndReceive("trending-post-month", new TrendingPostsRequest(pageNr, size),
            TrendingPostPageDto.class).getTrendingPostPage();

      return convertRequestToListGetPostsAndConvertToDTO(user, trendingPage);
  }

  public Page<PostDto.PostReturn> getTrendingPostsYear(UUID user, int pageNr, int size) {
    var trendingPage = messaging.sendAndReceive("trending-post-year", new TrendingPostsRequest(pageNr, size),
            TrendingPostPageDto.class).getTrendingPostPage();

      return convertRequestToListGetPostsAndConvertToDTO(user, trendingPage);
  }

    public Page<PostDto.PostReturn> convertRequestToListGetPostsAndConvertToDTO(UUID user,
            Page<TrendingPostDto> trendingPage) {

       List<Long> postIds =
                trendingPage.stream().map(p -> p.getPostId()).collect(Collectors.toList());
        Page<IPost> posts = postRepository.findAllIPostByIds(postIds);

        return postService.createPageOfPostDto(user, posts);
    }
}
