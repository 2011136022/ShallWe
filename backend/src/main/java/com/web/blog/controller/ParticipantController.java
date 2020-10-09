package com.web.blog.controller;

import java.io.IOException;
import java.util.List;

import java.util.Optional;
import javax.mail.MessagingException;
import javax.validation.Valid;
import com.web.blog.dao.ParticipantDao;
import com.web.blog.dao.PostDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.comment.CommentResponse;
import com.web.blog.model.participant.ParticipantRequest;
import com.web.blog.model.participant.ParticipantListResponse;
import com.web.blog.model.post.Post;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;
import com.web.blog.model.participant.Participant;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ParticipantController {

    @Autowired
    PostDao postDao;

    @Autowired
    ParticipantDao participantDao;

    @Autowired
    UserDao userDao;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/participant/create")
    @ApiOperation(value = "참가자등록")
    public Object create(@Valid @RequestBody ParticipantRequest request) throws MessagingException, IOException {
        System.out.println(request.getArticleId());
        String token = request.getToken();
        User jwtuser = jwtService.getUser(token);
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        if (userOpt.isPresent()) {
            int articleId = request.getArticleId();
            Participant part = participantDao.getParticipantByUserIdAndArticleId(userOpt.get().getUserId(), articleId);
            if (part != null) {// 이 게시물(ArticleId)에 해당하는 유저아이디(UserId)가 Participant DB에 있으면
                String message = "이미 참가되셨습니다.";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            String title = request.getTitle();
            int price = request.getPrice();
            if (price < 0) {
                String message = "0원보다 값이 작습니다.";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            String description = request.getDescription();

            Participant participant = new Participant();
            participant.setUserId(userOpt.get().getUserId()); // token값으로 id 받아옴
            participant.setArticleId(request.getArticleId());
            participant.setTitle(title);
            participant.setPrice(price);
            participant.setStatus(0); // 상태값 0으로 지정
            participant.setWriter(userOpt.get().getNickname());
            participant.setDescription(description);
            participantDao.save(participant);// 참가자 DB에 등록 완료

            // 게시물 sum_price에 더하기
            // Post post = postDao.getPostByArticleId(request.getArticleId());// 해당 구매게시물을 얻어옴
            // int sumPrice = post.getSumPrice();// sumPrice를 얻어옴
            // sumPrice = sumPrice + price;// 참가자의 가격을 더해줌
            // post.setSumPrice(sumPrice);
            // postDao.save(post);// 다시 DB에 넣어줌

            return new ResponseEntity<>("참가자 등록", HttpStatus.OK);
        } else {
            String message = "로그인 상태를 확인하세요";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/participant/accept/{articleId}/{nickname}")
    @ApiOperation(value = "참가 수락")
    public Object accept(@PathVariable int articleId, @PathVariable String nickname)
            throws MessagingException, IOException {
        System.out.println("참가자 수락!!");

        Post post = postDao.getPostByArticleId(articleId);
        if (post.getStatus() == 1 || post.getStatus() == 2 || post.getStatus() == 3) {
            
            User user = userDao.getUserByNickname(nickname);
            Participant part = participantDao.findParticipantByArticleIdAndUserId(articleId, user.getUserId());
            part.setStatus(1);
            participantDao.save(part);
           
            // 수락시 sumPrice 변경되도록!
            post.setSumPrice(post.getSumPrice() + part.getPrice());
            if (post.getMinPrice() <= post.getSumPrice()) {
                post.setStatus(3); // 금액 채워지도록 변경 
            }
            if(post.getStatus() == 1 ){//첫 참가자 였다면, 
                post.setStatus(2);
            }

            postDao.save(post);
            return new ResponseEntity<>("참가 수락처리 되었습니다. ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이미 마감된 게시물 입니다.", HttpStatus.OK);
        }
    }

    @PutMapping("/participant/denied/{articleId}/{nickname}")
    @ApiOperation(value = "참가 거부")
    public Object denied(@PathVariable int articleId, @PathVariable String nickname)
            throws MessagingException, IOException {
        System.out.println("참가 거부");

        Post post = postDao.getPostByArticleId(articleId);
        if (post.getStatus() == 1 || post.getStatus() == 2 || post.getStatus() == 3) {
            User user = userDao.getUserByNickname(nickname);
            Participant part = participantDao.findParticipantByArticleIdAndUserId(articleId, user.getUserId());
            part.setStatus(2);
            participantDao.save(part);

            return new ResponseEntity<>("참가 거부처리 되었습니다. ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이미 마감된 게시물 입니다.", HttpStatus.OK);
        }
    }

    


    @Transactional(readOnly = true)
    @GetMapping("/participant/read/{articleId}")
    @ApiOperation(value = "참가자 목록")
    public Object read(@PathVariable int articleId) throws MessagingException, IOException {

        System.out.println("참가자 목록 출력!!");

        List<Participant> plist;

        plist = participantDao.findParticipantByArticleId(articleId);
        ParticipantListResponse result = new ParticipantListResponse();
        result.participantList = plist;

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping("/participant/update")
    @ApiOperation(value = "참가자 수정")
    public Object update(@Valid @RequestBody ParticipantRequest request) {
        System.out.println(request.toString());
        String token = request.getToken();
        User user = jwtService.getUser(token);
        Optional<User> userOpt = userDao.findUserByEmail(user.getEmail());
        if (userOpt.isPresent()) {
            // System.out.println("유저아이디 : " + user.getUserId());
            int articleId = request.getArticleId();
            Participant part = participantDao.getParticipantByUserIdAndArticleId(userOpt.get().getUserId(), articleId);
            int old_price = part.getPrice();
            int new_price = request.getPrice();

            if (new_price < 0) {
                String message = "수정할 값이 0원보다 값이 작습니다.";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            int difference = (new_price - old_price);// article가격 추가될 것

            // 게시물 sum_price에 수정
            Post post = postDao.getPostByArticleId(request.getArticleId());// 해당 구매게시물을 얻어옴
            int sumPrice = post.getSumPrice();// sumPrice를 얻어옴
            sumPrice = (sumPrice + difference);// 참가자의 수정 가격을 더해줌
            post.setSumPrice(sumPrice);
            postDao.save(post);// 다시 DB에 넣어줌

            part.setTitle(request.getTitle());
            part.setPrice(new_price);
            part.setDescription(request.getDescription());
            participantDao.save(part);// 다시 세팅해서 참가자 DB에 넣어줌

            System.out.println(part.getNo() + "번째 참가자 수정 완료");

            return new ResponseEntity<>("참가자 수정 완료", HttpStatus.OK);
        } else {
            String message = "로그인 상태를 확인하세요";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/participant/delete/{no}")
    @ApiOperation(value = "참가자삭제하기")
    public Object delete(@Valid @PathVariable int no) {
        Participant participant = participantDao.getParticipantByNo(no);// 참가자의 해당 정보를 가져옴

        participantDao.delete(participant);
        System.out.println("참가자 삭제하기!!");
        CommentResponse result = new CommentResponse();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}