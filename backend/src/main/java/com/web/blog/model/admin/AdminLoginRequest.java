package com.web.blog.model.admin;

import javax.validation.Valid;

import lombok.Data;

@Data
@Valid
public class AdminLoginRequest {

    String adminId;
    String password;

}
