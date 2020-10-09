package com.web.blog.model.user;

import javax.validation.Valid;

import lombok.Data;

@Data
@Valid
public class LoginRequest {

    String email;
    String password;

}
