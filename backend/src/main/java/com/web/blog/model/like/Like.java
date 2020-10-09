package com.web.blog.model.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "like_table") // 이어줄 테이블명 지정
public class Like {
    @Id
    @Column(name = "no")
    int no;
    @Column(name = "user_id")
    int userId;
    @Column(name = "article_id")
    int articleId;
}