package com.web.blog.model.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminLoginResponse {
    @ApiModelProperty(value = "adminToken", position = 1)
    String adminToken;
    @ApiModelProperty(value = "token", position = 2)
    String token;
}
