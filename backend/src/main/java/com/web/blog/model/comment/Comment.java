package com.web.blog.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "comment") // 이어줄 테이블명 지정

public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    @Column(name = "article_id")
    private int articleId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "status")
    private int status;
    @Column(name = "writer")
    private String writer;
    @Column(name = "content")
    private String content;
    
    @Column(insertable = false, updatable = false ,name = "create_time")
    private LocalDateTime createTime;
    
}