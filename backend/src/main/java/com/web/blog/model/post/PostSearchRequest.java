package com.web.blog.model.post;

import lombok.Data;

import javax.validation.Valid;

@Data
@Valid
public class PostSearchRequest {
    String subject;
    String word;
    String address;
}   