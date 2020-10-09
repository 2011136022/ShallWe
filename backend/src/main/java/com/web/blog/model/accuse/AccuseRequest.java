package com.web.blog.model.accuse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Data
@Valid
public class AccuseRequest {
  
    int accuseId;
    @ApiModelProperty(required = true)
    @NotNull
    int accuseIndex;
    int accuseValue;
    int accuseKind;
    String reporter;
    String defendant;
    String accuseReason;
    String accuseUrl;
    int accuseConfirm;
    String token;
    String nickname;
}