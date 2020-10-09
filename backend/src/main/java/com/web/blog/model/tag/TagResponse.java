package com.web.blog.model.tag;

import io.swagger.annotations.ApiModelProperty;

public class TagResponse {
    @ApiModelProperty(value = "tagId", position = 1)
    public int tagId;
    @ApiModelProperty(value = "name", position = 2)
    public String name;
    @ApiModelProperty(value = "name", position = 3)
    public int articleId;
}