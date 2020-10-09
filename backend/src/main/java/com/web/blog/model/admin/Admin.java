package com.web.blog.model.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "admin") // 이어줄 테이블명 지정
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    @Column(name = "admin_id")
    private String adminId;
    @Column(name = "password")
    private String password;

    public Admin(String adminId, String password){
        this.adminId = adminId;
        this.password = password;
    }
}