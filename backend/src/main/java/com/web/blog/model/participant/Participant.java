package com.web.blog.model.participant;

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
@Table(name = "participant") // 이어줄 테이블명 지정

public class Participant {
    @Id
    private int no;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "article_id")
    private int articleId;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private int price;
    @Column(name = "writer")
    private String writer;
    @Column(name = "status")
    private int status;
    @Column(name = "description")
    private String description;

}