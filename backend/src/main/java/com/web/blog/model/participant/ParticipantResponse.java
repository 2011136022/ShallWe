package com.web.blog.model.participant;

import io.swagger.annotations.ApiModelProperty;

public class ParticipantResponse {
    @ApiModelProperty(value = "userId", position = 1)
    private int userId;
    
    @ApiModelProperty(value = "articleId", position = 2)
    private int articleId;
    
    @ApiModelProperty(value = "title", position = 3)
    private String title;
    
    @ApiModelProperty(value = "price", position = 4)
    private int price;

    @ApiModelProperty(value = "description", position = 5)
    private String description;
    @ApiModelProperty(value = "writer", position = 6)
    private String writer;
    @ApiModelProperty(value = "status", position = 7)
    private int status;

}