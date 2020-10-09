package com.web.blog.model.accuse;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
public class AccuseRes {
    private int accuseId;
    private String reporter;
    private String defendant;
    private int accuseIndex;
    private int accuseValue;
    private int accuseKind;
    private String accuseReason;
    private String accuseUrl;
    private int accuseConfirm;
}