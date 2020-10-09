package com.web.blog.dao;

import java.util.List;

import com.web.blog.model.participant.Participant;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantDao extends JpaRepository<Participant, String> {

   
    Participant getParticipantByNo(int no);
    List<Participant> getParticipantByUserId(int userId);
    List<Participant> getParticipantByUserIdAndStatus(int userid,int status);
   
    Participant getParticipantByUserIdAndArticleId(int userId, int articleId);
    Participant findParticipantByArticleIdAndUserId(int articleId,int userId);
    List<Participant> findParticipantByArticleId(int articleId);
    List<Participant> getParticipantByArticleId(int articleId);
}
