package com.web.blog.model.user;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@Valid
public class KsignupRequest {
    @ApiModelProperty(required = true)
    @NotNull
    String email;
    @ApiModelProperty(required = true)
    @NotNull
    String nickname;
    String address;
    String introduce;
    LocalDate birthday;
    String token;
    String profileImage;
}