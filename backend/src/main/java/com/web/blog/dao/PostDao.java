package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Post, String> {
    // Post getUserByEmail(String email);
    // Post getUserByNickname(String nickname);

    
    Post getPostByArticleId(int articleId);

    Post findPostByArticleId(int articleId);
    
    List<Post> getPostByUserId(int userId);
    List<Post> getPostByUserIdAndTemp(int userId,int temp);
    List<Post> getPostByUserIdAndCategoryId(int userId,int categoryId);
    List<Post> getPostByTempAndCategoryIdNotOrderByCategoryIdAscCreateTimeDesc(int userId,int categoryId);
    List<Post> findPostByTempAndStatusNotOrderByCreateTimeDesc(int temp,int status);
    List<Post> findPostByTempAndCategoryIdAndStatusNotOrderByCreateTimeDesc(int temp,int categoryId,int status);
    List<Post> getPostByTempAndCategoryIdNotAndStatusNotOrderByCategoryIdAscCreateTimeDesc(int temp,int categoryId,int status);

    Post getPostByArticleIdAndStatus(int articleId,int status);
    Post getPostByArticleIdAndStatusIn(int articleId,List<Integer> numlist);
    List<Post> findPostByTempAndWriterOrderByCreateTimeDesc(int temp, String writer);


    Optional<Post> findPostByStatusNotAndArticleIdAndTempOrderByCreateTimeDesc(int status, int articleId,int temp);

    Optional<Post> findPostByArticleIdAndTempAndCategoryIdOrderByCreateTimeDesc(int articleId,int temp,int categoryId);

    List<Post> findPostByTitleLikeOrderByCreateTimeDesc(String word);

    List<Post> findPostByStatusNotAndTempAndTitleLikeOrderByCreateTimeDesc(int status,int temp , String word);
    Optional<Post>  findPostByAndArticleIdAndTempOrderByCreateTimeDesc(int articleId, int temp);

    List<Post> findPostByStatusNotAndTempAndCategoryIdAndTitleLikeOrderByCreateTimeDesc(int status,int temp ,int categoryId, String word);
    List<Post> findPostByStatusNotAndTempAndWriterLikeOrderByCreateTimeDesc(int status, int writer , String word);

    List<Post> findPostByStatusNotAndTempAndCategoryIdAndWriterLikeOrderByCreateTimeDesc(int status , int temp ,int categoryId, String word);

    // 리스트 출력 
    List<Post> findPostByStatusNotAndTempOrderByCreateTimeDesc(int status , int temp);
    
    List<Post> findPostByTempAndCategoryIdOrderByCreateTimeDesc(int temp,int categoryId);
    
    
    List<Post> findPostByUserId(int userId);
    
    // 스트링 토크나이저로 잘라서 한 개 입력했을 때 부터 네 개 입력했을 때 (전체)
    
    List<Post> findPostByStatusNotAndAddressLikeOrderByCreateTimeDesc(int status, String st1);
    List<Post> findPostByStatusNotAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(int status, String st1, String st2);
    List<Post> findPostByStatusNotAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(int status,String st1, String st2, String st3);
    List<Post> findPostByStatusNotAndAddressLikeAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(int status, String st1, String st2, String st3, String st4);
    // 스트링 토크나이저로 잘라서 한 개 입력했을 때 부터 네 개 입력했을 때 (카테고리별)
    
    List<Post> findPostByStatusNotAndTempAndCategoryIdAndAddressLikeOrderByCreateTimeDesc(int status, int temp, int categoryId, String st1);
    List<Post> findPostByStatusNotAndTempAndCategoryIdAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(int status, int temp, int categoryId, String st1, String st2);
    List<Post> findPostByStatusNotAndTempAndCategoryIdAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(int status, int temp, int categoryId, String st1, String st2, String st3);
    List<Post> findPostByStatusNotAndTempAndCategoryIdAndAddressLikeAndAddressLikeAndAddressLikeAndAddressLikeOrderByCreateTimeDesc(int status, int temp, int categoryId, String st1, String st2, String st3, String st4);
    
    // 메인
    // List<Post> findPostByTempOrderByCreateTimeDesc(int temp);
    List<Post> findPostByStatusNotAndTitleLikeOrderByCreateTimeDesc(int status,String title);
    List<Post> findPostByStatusNotAndTempOrderByEndTimeAsc(int status, int temp);
    List<Post> findPostByStatusNotAndTempAndCategoryIdOrderByLikeNumDesc(int status , int temp, int categoryId);;
}