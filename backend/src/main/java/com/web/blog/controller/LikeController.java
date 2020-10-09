package com.web.blog.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.web.blog.dao.LikeDao;
import com.web.blog.dao.PostDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostResponse;
import com.web.blog.model.like.Like;
import com.web.blog.model.like.LikeResponse;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = PostResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = PostResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = PostResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = PostResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class LikeController {
    @Autowired
    LikeDao likeDao;
    @Autowired
    UserDao userDao;
    @Autowired
    PostDao postDao;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/like/{articleId}") // SWAGGER UI에 보이는 REQUEST명
    @ApiOperation(value = "좋아요 / 좋아요 취소")
    public Object Like(@RequestBody TokenRequest request,@Valid @PathVariable int articleId) {
        String message = "";
        String token = request.getToken();
        User jwtuser = jwtService.getUser(token);
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        if(token == null){
            message = "로그인이 되어있지 않습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (userOpt.isPresent()) {
            System.out.println("Like in!! ");
            // Post post = postDao.getPostByPid(pid);// 게시물 가져옴
            Optional<Like> likeOpt = likeDao.findLikeByUserIdAndArticleId(userOpt.get().getUserId(), articleId);
            if (likeOpt.isPresent()) {// 좋아요 상태일때
                System.out.println("IFFFFF");
                int UserId = userOpt.get().getUserId();
                Like like = new Like();
                like.setNo(likeOpt.get().getNo());
                like.setUserId(UserId);
                like.setArticleId(articleId);
                likeDao.delete(like);
                message = "좋아요 취소 !!";
                Post post = postDao.getPostByArticleId(articleId);
                
                post.setLikeNum(post.getLikeNum()-1);
                
                postDao.save(post);
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {
                int UserId = userOpt.get().getUserId();
                Like like = new Like();
                like.setArticleId(articleId);
                like.setUserId(UserId);
                likeDao.save(like);

                Post post = postDao.getPostByArticleId(articleId);
                
                post.setLikeNum(post.getLikeNum()+1);
                
                postDao.save(post);
                message = "좋아요!!"; 
                return new ResponseEntity<>(message, HttpStatus.OK);

            }
        } else {
            message = "로그인 상태를 확인하세요 / 비로그인 상태입니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/likedcheck/{articleId}") // SWAGGER UI에 보이는 REQUEST명
    @ApiOperation(value = "좋아요여부 확인")
    public Object LikeCheck(@RequestBody TokenRequest request , @Valid @PathVariable int articleId) {
        String message = "";
        String token = request.getToken();
        User jwtuser = jwtService.getUser(token);
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        if(token == null){
            message = "로그인이 되어있지 않습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (userOpt.isPresent()) {
            
            Optional<Like> likeOpt = likeDao.findLikeByUserIdAndArticleId(userOpt.get().getUserId(), articleId);
            LikeResponse result = new LikeResponse();
        
            if (likeOpt.isPresent()) {// 좋아요 상태일때
                result.isLiked = true;
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.isLiked = false;
                return new ResponseEntity<>(result, HttpStatus.OK);

            }
        } else {
            message = "로그인 상태를 확인하세요 / 비로그인 상태입니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        }
    }

}