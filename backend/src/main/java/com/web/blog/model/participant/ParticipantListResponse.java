package com.web.blog.model.participant;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ParticipantListResponse {
    @ApiModelProperty(value = "participantList", position = 1)
     public List<Participant> participantList;
   
}