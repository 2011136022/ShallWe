package com.web.blog.model.accuse;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class AccuseListResponse {
    @ApiModelProperty(value = "accuselist", position = 6)
    public List<AccuseResponse> accuseList;
}