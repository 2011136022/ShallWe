package com.web.blog.model.accuse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "accuse") // 이어줄 테이블명 지정

public class Accuse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accuseId;
    private String reporter;
    private String defendant;
    private int accuseIndex;
    private int accuseValue;
    private int accuseKind;
    private String accuseReason;
    private String accuseUrl;
    private int accuseConfirm;

    public Accuse(String reporter, String defendant, int accuseIndex, int accuseValue, int accuseKind, String accuseReason, String accuseUrl, int accuseConfirm) {
        this.reporter = reporter;
        this.defendant = defendant;
        this.accuseIndex = accuseIndex;
        this.accuseValue = accuseValue;
        this.accuseKind = accuseKind;
        this.accuseReason = accuseReason;
        this.accuseUrl = accuseUrl;
        this.accuseConfirm = accuseConfirm;
    }
}