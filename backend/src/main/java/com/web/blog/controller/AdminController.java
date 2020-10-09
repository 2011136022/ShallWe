package com.web.blog.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.web.blog.dao.AccuseDao;
import com.web.blog.dao.AdminDao;
import com.web.blog.dao.CommentDao;
import com.web.blog.dao.PostDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.accuse.Accuse;
import com.web.blog.model.accuse.AccuseListResponse;
import com.web.blog.model.accuse.AccuseRequest;
import com.web.blog.model.accuse.AccuseResponse;
import com.web.blog.model.admin.Admin;
import com.web.blog.model.admin.AdminLoginRequest;
import com.web.blog.model.comment.Comment;
import com.web.blog.model.post.Post;
import com.web.blog.model.user.AdminLoginResponse;
import com.web.blog.model.user.User;
import com.web.blog.model.user.UserResponse;
import com.web.blog.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = UserResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = UserResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = UserResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class AdminController {
    @Autowired
    AccuseDao accuseDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PostDao postDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    AdminDao adminDao;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/admin/login") // SWAGGER UI에 보이는 REQUEST명
    @ApiOperation(value = "관리자 로그인") // SWAGGER UI에 보이는 이름
    public Object login(@RequestBody AdminLoginRequest req) {

        String adminId = req.getAdminId();
        String password = req.getPassword();
        System.out.println(adminId  + " " + password);
        Optional<Admin> adminOpt = adminDao.getAdminByAdminIdAndPassword(adminId, password);
        System.out.println(adminOpt.get().toString());
        if (adminOpt.isPresent()) {
            System.out.println("로그인 성공  : " + adminId);
            Admin admin = new Admin(adminId, password);
            String admintoken = jwtService.createAdminLoginToken(admin);

            User usert = userDao.getUserByEmail(adminId);
            
            User user = new User();
            user.setNickname(usert.getNickname());
            user.setEmail(adminId);
            String token = jwtService.createLoginToken(user);
            AdminLoginResponse result = new AdminLoginResponse();
            result.setAdminToken(admintoken);
            result.setToken(token);
 
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            System.out.println("로그인 실패");
            return new ResponseEntity<>("로그인 실패 ", HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/accuse/create")
    @ApiOperation(value = "신고글 등록")
    public Object create(@RequestBody AccuseRequest req) throws MessagingException, IOException {

        String token = req.getToken();
        User jwtuser = jwtService.getUser(token);
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        if (userOpt.isPresent()) {
            Accuse accuse = new Accuse(req.getReporter(), req.getDefendant(), req.getAccuseIndex(),
                    req.getAccuseValue(), req.getAccuseKind(), req.getAccuseReason(), req.getAccuseUrl(),
                    req.getAccuseConfirm());

            System.out.println(accuse);
            accuseDao.save(accuse);
            System.out.println("신고글 등록!!");

            return new ResponseEntity<>("신고글 등록", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 상태를 확인하세요", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/accuse/read")
    @ApiOperation(value = "신고글 목록")
    public Object read(@RequestBody AccuseRequest req) throws MessagingException, IOException {
        String token = req.getToken();
        Admin jwtadmin = jwtService.getAdmin(token);
        Optional<Admin> adminOpt = adminDao.findAdminByAdminIdAndPassword(jwtadmin.getAdminId(), jwtadmin.getPassword());
        if (adminOpt.isPresent()) {
            System.out.println("신고글 목록 출력!!");

            List<Accuse> alist = accuseDao.findAccuseByAccuseConfirm(0);
            AccuseListResponse result = new AccuseListResponse();

            result.accuseList = new LinkedList<>();
            for (int i = 0; i < alist.size(); i++) {
                Accuse a = alist.get(i);
                result.accuseList.add(new AccuseResponse(a.getAccuseId(), a.getReporter(), a.getDefendant(),
                        a.getAccuseIndex(), a.getAccuseValue(), a.getAccuseKind(), a.getAccuseReason(),
                        a.getAccuseUrl(), a.getAccuseConfirm()));
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("관리자가 아닙니다.", HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/accuse/applyto")
    @ApiOperation(value = "신고 적용하기")
    public Object applyto(@Valid @RequestBody AccuseRequest req) {
        String token = req.getToken();
        Admin jwtadmin = jwtService.getAdmin(token);
        Optional<Admin> adminOpt = adminDao.findAdminByAdminIdAndPassword(jwtadmin.getAdminId(), jwtadmin.getPassword());

        if (adminOpt.isPresent()) {
            String reporter = req.getReporter();
            String defendant = req.getDefendant();
            User userR = userDao.getUserByNickname(reporter);
            User userD = userDao.getUserByNickname(defendant);
            // 신고를 확인하고 접수 완료하면서 점수 깎고 상태를 1로 만들어주어 디비에는 남기되 화면에는 띄우지 않음
            if (req.getAccuseConfirm() == 1) {
                if (req.getAccuseKind() == 1) { // 1. 허위로 기재된 게시글 -20
                    userD.setUserPoint(userD.getUserPoint() - 20);
                } else if (req.getAccuseKind() == 2) { // 2. 욕설, 비방, 도배 -50
                    userD.setUserPoint(userD.getUserPoint() - 50);
                } else if (req.getAccuseKind() == 3) { // 3. 광고 글입니다. -30
                    userD.setUserPoint(userD.getUserPoint() - 30);
                } 
                // else if (req.getAccuseKind() == 4) { // 4. 기타 - 10
                //     user.setUserPoint(user.getUserPoint() - 10);
                // }
                int up = userD.getUserPoint();
                if(up <= 1000){
                    userD.setGrade(1);
                }else if(up <= 1500){ 
                    userD.setGrade(2);
                }else if(up <= 2500){ 
                    userD.setGrade(3);
                }else if(up <= 4000){ 
                    userD.setGrade(4);
                }else { 
                    userD.setGrade(5);
                }
                userDao.save(userD);
                System.out.println("점수(user point) 수정");
                Accuse accuse = accuseDao.findAccuseByAccuseId(req.getAccuseId());
                accuse.setAccuseConfirm(1);
                accuseDao.save(accuse);
                System.out.println("신고 게시물 상태 수정");
                // 게시물에 대한 신고를 확실하게 판단하여 완료하면
                if (req.getAccuseIndex() == 1 || req.getAccuseIndex() == 3 || req.getAccuseIndex() == 5) {
                    Post post = postDao.getPostByArticleId(req.getAccuseValue());
                    post.setStatus(0); // 비활성화 게시물로 만들어주기
                    postDao.save(post);
                } else if (req.getAccuseIndex() == 2 || req.getAccuseIndex() == 4 || req.getAccuseIndex() == 6) {
                    Comment comment = commentDao.getCommentByCommentId(req.getAccuseValue());
                    comment.setStatus(0); // 비활성화 댓글로 만들어주기
                    commentDao.save(comment);
                }

            }
            // 신고가 허위일 경우
            else if (req.getAccuseConfirm() == 2) {
                userR.setUserPoint(userR.getUserPoint() - 30);
                int up = userR.getUserPoint();
                if(up <= 1000){
                    userR.setGrade(1);
                }else if(up <= 1500){ 
                    userR.setGrade(2);
                }else if(up <= 2500){ 
                    userR.setGrade(3);
                }else if(up <= 4000){ 
                    userR.setGrade(4);
                }else { 
                    userR.setGrade(5);
                }
                userDao.save(userR);
                Accuse accuse = accuseDao.findAccuseByAccuseId(req.getAccuseId());
                accuse.setAccuseConfirm(2);
                accuseDao.save(accuse);
                userDao.save(userR);
                System.out.println("점수(user point) 수정");
                
            }
            
            return new ResponseEntity<>("신고 사항을 접수하였습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("관리자가 아닙니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accuse/disabled")
    @ApiOperation(value = "비활성화시키기")
    public Object disabled(@Valid @RequestBody AccuseRequest req) {
        String token = req.getToken();
        Admin jwtadmin = jwtService.getAdmin(token);
        Optional<Admin> adminOpt = adminDao.findAdminByAdminIdAndPassword(jwtadmin.getAdminId(), jwtadmin.getPassword());

        if (adminOpt.isPresent()) {
            String nickname = req.getNickname();
            User user = userDao.getUserByNickname(nickname);
            user.setStatus(0);
            userDao.save(user);

            System.out.println("user 비활성화시켜버리기!!!");

            return new ResponseEntity<>("유저를 비활성화했습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("관리자가 아닙니다.", HttpStatus.BAD_REQUEST);
        }
    }

}