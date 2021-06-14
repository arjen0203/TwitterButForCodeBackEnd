package com.arjen0203.codex.service.postservice.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.arjen0203.codex.core.rabbit.utils.Messaging;
import com.arjen0203.codex.domain.post.dto.CustomTrendingPostPage;
import com.arjen0203.codex.domain.post.interfaces.IPost;
import com.arjen0203.codex.domain.trending.dto.TrendingPostPageDto;
import com.arjen0203.codex.domain.trending.dto.TrendingPostsRequest;
import com.arjen0203.codex.service.postservice.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrendingService {
  private final PostRepository postRepository;
  private final PostService postService;
  private final Messaging messaging;
  private final ModelMapper modelMapper;


  public CustomTrendingPostPage getTrendingPostsDay(UUID user, int pageNr, int size) {
     var trendingPage = messaging.sendAndReceive("trending-post-day", new TrendingPostsRequest(pageNr, size),
             TrendingPostPageDto.class);

      return convertRequestToListGetPostsAndConvertToDTO(user, trendingPage);
  }

  public CustomTrendingPostPage getTrendingPostsWeek(UUID user, int pageNr, int size) {
    var trendingPage = messaging.sendAndReceive("trending-post-week", new TrendingPostsRequest(pageNr, size),
            TrendingPostPageDto.class);

      return convertRequestToListGetPostsAndConvertToDTO(user, trendingPage);
  }

  public CustomTrendingPostPage getTrendingPostsMonth(UUID user, int pageNr, int size) {
    var trendingPage = messaging.sendAndReceive("trending-post-month", new TrendingPostsRequest(pageNr, size),
            TrendingPostPageDto.class);

      return convertRequestToListGetPostsAndConvertToDTO(user, trendingPage);
  }

  public CustomTrendingPostPage getTrendingPostsYear(UUID user, int pageNr, int size) {
    var trendingPage = messaging.sendAndReceive("trending-post-year", new TrendingPostsRequest(pageNr, size),
            TrendingPostPageDto.class);

      return convertRequestToListGetPostsAndConvertToDTO(user, trendingPage);
  }

    public CustomTrendingPostPage convertRequestToListGetPostsAndConvertToDTO(UUID user,
            TrendingPostPageDto trendingPage) {

       List<Long> postIds =
                trendingPage.getTrendingPosts().stream().map(p -> p.getPostId()).collect(Collectors.toList());
       List<IPost> posts = postRepository.findAllIPostByIds(postIds);

       var postPage = new CustomTrendingPostPage();
       postPage.setContent(posts.stream().map(p -> postService.createPostReturn(user, p)).collect(Collectors.toList()));
       postPage.setPageNumber(trendingPage.getPageNumber());
       postPage.setMaxPages(trendingPage.getMaxPages());
       postPage.setPageSize(trendingPage.getPageSize());

        return postPage;
    }
}
