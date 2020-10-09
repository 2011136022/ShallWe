package com.web.blog.model.post;
import java.util.List;


import io.swagger.annotations.ApiModelProperty;


public class PostListResponse {
    @ApiModelProperty(value = "postList", position = 1)
    public List<PostResponse> postList;
    @ApiModelProperty(value = "newPostList", position = 2)
    public List<PostResponse> recentList;
    @ApiModelProperty(value = "deadLineList", position = 3)
    public List<PostResponse> deadLineList;
    @ApiModelProperty(value = "bestReviewList", position = 4)
    public List<PostResponse> bestReviewList;
}