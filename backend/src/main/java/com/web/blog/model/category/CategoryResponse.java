package com.web.blog.model.category;

import io.swagger.annotations.ApiModelProperty;

public class CategoryResponse {
    @ApiModelProperty(value = "categoryId", position = 1)
    private int categoryId;
    
    @ApiModelProperty(value = "name", position = 2)
    private String name;
}