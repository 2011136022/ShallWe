package com.web.blog.model.comment;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;

public class CommentResponse {
    @ApiModelProperty(value = "content", position = 1)
    public String content;

    @ApiModelProperty(value = "commentlist", position = 2)
     public List<Comment> commentList;
   
}