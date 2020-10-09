package com.web.blog.model.user;
import lombok.Data;


import javax.validation.Valid;

@Data
@Valid
public class TokenRequest {
 
    String token;
    String status;

}
