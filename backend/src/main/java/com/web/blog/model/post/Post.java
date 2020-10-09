package com.web.blog.model.post;

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
@Table(name = "article") // 이어줄 테이블명 지정
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "article_id")
    private int articleId;
    @Column(name = "category_id")
    private int categoryId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "title")
    private String title;
    @Column(name = "writer")
    private String writer;
    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;
    @Column(name = "min_price")
    private int minPrice;
    @Column(name = "sum_price")
    private int sumPrice;
    @Column(name = "like_num")
    private int likeNum;
    @Column(name = "comment_num")
    private int commentNum;
    @Column(name = "tag")
    private String tag;
    @Column(name = "url_link")
    private String urlLink;
    @Column(name = "open_link")
    private String openLink;
    @Column(name = "image")
    private String image;
    @Column(name = "status")
    private int status;
    @Column(name = "temp")
    private int temp;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    // 생성시간은 자동생성
    @Column(insertable = false, updatable = false, name = "create_time" )
    private LocalDateTime createTime;

    public Post(int cateId, int userId, String title, String writer, String addr, String des, int minPrice,
            int sumPrice, String image, int temp, LocalDateTime endTime) {
                this.categoryId = cateId;
                this.userId = userId;
                this.title = title;
                this.writer = writer;
                this.address = addr;
                this.description = des;
                this.minPrice = minPrice;
                this.sumPrice = sumPrice;
                this.image = image;
                this.temp = temp;
                this.endTime = endTime;
    }

}