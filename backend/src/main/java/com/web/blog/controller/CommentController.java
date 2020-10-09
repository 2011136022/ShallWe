package com.web.blog.controller;

import java.io.IOException;
import java.util.List;

import java.util.Optional;
import javax.mail.MessagingException;
import javax.validation.Valid;
import com.web.blog.dao.CommentDao;
import com.web.blog.dao.PostDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.comment.CommentResponse;
import com.web.blog.model.post.Post;
import com.web.blog.model.comment.CommentRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;
import com.web.blog.model.comment.Comment;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = CommentResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CommentResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = CommentResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = CommentResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class CommentController {

    @Autowired
    CommentDao commentDao;
    @Autowired
    UserDao userDao;
    @Autowired
    PostDao postDao;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/comment/create")
    @ApiOperation(value = "댓글등록")
    public Object create(@Valid @RequestBody CommentRequest req)throws MessagingException, IOException {
        System.out.println("댓글등록");
        System.out.println(req.getArticleId());



        User jwtuser = jwtService.getUser(req.getToken());

        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        if (userOpt.isPresent()) {
            Comment comment = new Comment();
            comment.setArticleId(req.getArticleId());
            comment.setContent(req.getContent());
            comment.setStatus(1);
            comment.setWriter(userOpt.get().getNickname());
            comment.setUserId(userOpt.get().getUserId()); // token값으로 id 받아옴
            commentDao.save(comment);

            Post post = postDao.getPostByArticleId(req.getArticleId());
            post.setCommentNum(post.getCommentNum()+1);
            postDao.save(post);
            System.out.println("댓글 등록!!");
            CommentResponse result = new CommentResponse();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            String message = "로그인 상태를 확인하세요";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/comment/read/{articleId}")
    @ApiOperation(value = "댓글 목록")
    public Object read(@PathVariable int articleId) throws MessagingException, IOException {

        List<Comment> clist = commentDao.findCommentByArticleId(articleId);

        System.out.println("댓글 목록!!");
        CommentResponse result = new CommentResponse();
        result.commentList = clist;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/comment/update")
    @ApiOperation(value = "댓글수정")
    public Object update(@Valid @RequestBody CommentRequest request) {

        int commentId = request.getCommentId();
        Comment comment = commentDao.getCommentByCommentId(commentId);
        comment.setContent(request.getContent());
        commentDao.save(comment);

        System.out.println("댓글 수정");
        CommentResponse result = new CommentResponse();
        result.content = request.getContent();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/comment/delete/{commentId}")
    @ApiOperation(value = "댓글삭제하기")
    public Object delete(@Valid @PathVariable int commentId) {
        Comment comment = commentDao.getCommentByCommentId(commentId);
        commentDao.delete(comment);
        System.out.println("댓글 삭제하기!!");
        CommentResponse result = new CommentResponse();

        Post post = postDao.getPostByArticleId(comment.getArticleId());
        post.setCommentNum(post.getCommentNum()-1);
        postDao.save(post);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    
}