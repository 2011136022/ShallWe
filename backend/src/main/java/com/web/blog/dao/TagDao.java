package com.web.blog.dao;

import java.util.List;
import com.web.blog.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<Tag, String>{

    List<Tag> findTagByName(String name);
    List<Tag> getTagByArticleId(int articleId);

    List<Tag> findTagByArticleId(int articleId);
}