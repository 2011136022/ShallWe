package com.web.blog.model.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Data
@Valid
public class CommentRequest {
  
    int commentId;
    int ArticleId;
    int userId;
    @ApiModelProperty(required = true)
    @NotNull
    String content;
    String token;
        
}