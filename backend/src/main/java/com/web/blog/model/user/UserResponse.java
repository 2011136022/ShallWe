package com.web.blog.model.user;

import java.time.LocalDate;
import java.util.List;

import com.web.blog.model.post.Post;

import io.swagger.annotations.ApiModelProperty;

public class UserResponse {
    // user

    @ApiModelProperty(value = "userId", position = 1)
    public int userId;
    @ApiModelProperty(value = "email", position = 3)
    public String email;
    @ApiModelProperty(value = "nickname", position = 5)
    public String nickname;
    @ApiModelProperty(value = "address", position = 6)
    public String address;
    @ApiModelProperty(value = "birthday", position = 7)
    public LocalDate birthday;
    @ApiModelProperty(value = "userPoint", position = 8)
    public int userPoint;
    @ApiModelProperty(value = "articleCount", position = 9)
    public int articleCount;
    @ApiModelProperty(value = "reviewCount", position = 10)
    public int reviewCount;

    @ApiModelProperty(value = "likeCount", position = 11)
    public int likeCount;

    @ApiModelProperty(value = "tempCount", position = 12)
    public int tempCount;


    @ApiModelProperty(value = "articleList", position = 13)
    public List<Post> articleList;

    @ApiModelProperty(value = "reviewList", position = 14)
    public List<Post> reviewList;

    @ApiModelProperty(value = "likeList", position = 15)
    public List<Post> likeList;

    @ApiModelProperty(value = "tempList", position = 16)
    public List<Post> tempList;


    @ApiModelProperty(value = "grade", position = 17)
    public int grade;
    @ApiModelProperty(value = "status", position = 18)
    public int status;
  
    @ApiModelProperty(value = "joinCount", position = 19)
    public int joinCount;


    @ApiModelProperty(value = "joinList", position = 20)
    public List<Post> joinList;

    @ApiModelProperty(value = "completeCount", position = 21)
    public int completeCount;
    
    @ApiModelProperty(value = "completeList", position = 22)
    public List<Post> completeList; 
    @ApiModelProperty(value = "freeCount", position = 23)
    public int freeCount;

    @ApiModelProperty(value = "freeList", position = 24)
    public List<Post> freeList;

    @ApiModelProperty(value = "introduce", position = 25)
    public String introduce;
}