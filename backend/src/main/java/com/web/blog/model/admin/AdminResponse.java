package com.web.blog.model.admin;


import io.swagger.annotations.ApiModelProperty;

public class AdminResponse {
    // admin

    @ApiModelProperty(value = "no", position = 1)
    public int no;
    @ApiModelProperty(value = "adminId", position = 2)
    public String adminId;
    @ApiModelProperty(value = "password", position = 3)
    public String password;  

}