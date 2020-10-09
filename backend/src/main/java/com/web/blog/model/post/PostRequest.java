package com.web.blog.model.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class PostRequest {

    int articleId;
    int categoryId;
    int userId;
    @ApiModelProperty(required = true)
    @NotNull
    String title;
    String writer;

    String address;

    int minPrice;
    int myPrice;
    @ApiModelProperty(required = true)
    @NotNull
    String description;
    String urlLink;  
      String openLink;
    String image;
    String token;
    int likeNum;
    int commentNum;
    String tag;


    String[] tags;
    int temp;
 
    String endDate;
    String endTime;
    LocalDateTime endT;
}
