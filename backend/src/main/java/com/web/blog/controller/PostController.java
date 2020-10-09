package com.web.blog.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.validation.Valid;
import com.web.blog.dao.CommentDao;
import com.web.blog.dao.LikeDao;
import com.web.blog.dao.ParticipantDao;
import com.web.blog.dao.PostDao;
import com.web.blog.dao.TagDao;
import com.web.blog.dao.UserDao;
import com.web.blog.model.post.PostResponse;
import com.web.blog.model.tag.Tag;
import com.web.blog.model.like.Like;
import com.web.blog.model.participant.Participant;
import com.web.blog.model.post.PostRequest;
import com.web.blog.model.post.Post;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

import com.web.blog.model.comment.Comment;
import com.web.blog.model.comment.CommentRes;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = PostResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = PostResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = PostResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = PostResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class PostController {
    @Autowired
    PostDao postDao;

    @Autowired
    UserDao userDao;

    @Autowired
    LikeDao likeDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    ParticipantDao participantDao;

    @Autowired
    private JwtService jwtService;

    // 게시물 만료시간 지나면 자동 비활성화
    @Scheduled(cron = "*/30 * * * * *")
    public void articleTimeOut() {
        System.out.println("30초마다 실행중.");
        List<Post> plist = postDao.findAll();
        for (Post p : plist) {
            if (p.getStatus() == 1 || p.getStatus() == 2) {
                if (p.getTemp() == 1 && datetimeTosec(p.getEndTime()) < datetimeTosec(LocalDateTime.now())) {
                    p.setStatus(5);// 만료
                    postDao.save(p);
                }
            }
        }
    }

    @PostMapping("/post/create/{temp}")
    @ApiOperation(value = "게시글 및 임시글 등록")
    public Object create(@Valid @RequestBody PostRequest req, @PathVariable int temp) throws IOException {
        String token = req.getToken();
        String endDate = req.getEndDate();
        String endT = req.getEndTime();
        LocalDateTime endTime = null;
        if (endDate != null || endT != null) {
            endTime = LocalDateTime.parse(endDate + " " + endT, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        System.out.println(token);
        User jwtuser = jwtService.getUser(token);
        System.out.println(jwtuser.toString());
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        if (userOpt.isPresent()) {
            if (temp == 0) {// 임시저장
                Post post = new Post();
                post.setCategoryId(req.getCategoryId());
                post.setUserId(userOpt.get().getUserId());
                post.setTitle(req.getTitle());
                post.setWriter(userOpt.get().getNickname());
                post.setAddress(req.getAddress());
                post.setDescription(req.getDescription());
                post.setMinPrice(req.getMinPrice());
                post.setSumPrice(req.getMyPrice());
                post.setImage(req.getImage());
                post.setUrlLink(req.getUrlLink());
                post.setOpenLink(req.getOpenLink());
                post.setTemp(temp);
                post.setEndTime(endTime);
                post.setStatus(1);
                post.setLikeNum(0);
                post.setCommentNum(0);
                post.setUrlLink(req.getUrlLink());
                postDao.save(post);

                System.out.println("임시저장!!");

                return new ResponseEntity<>("임시저장 완료", HttpStatus.OK);
            } else if (temp == 1) {
                Post post = new Post();
                post.setCategoryId(req.getCategoryId());
                post.setUserId(userOpt.get().getUserId());
                post.setTitle(req.getTitle());
                post.setWriter(userOpt.get().getNickname());
                post.setAddress(req.getAddress());
                post.setDescription(req.getDescription());
                post.setMinPrice(req.getMinPrice());
                post.setSumPrice(req.getMyPrice());
                post.setUrlLink(req.getUrlLink());
                post.setOpenLink(req.getOpenLink());
                post.setImage(req.getImage());
                post.setTemp(temp);
                post.setEndTime(endTime);
                post.setStatus(1);
                post.setLikeNum(0);
                post.setCommentNum(0);

                // 태그 등록
                String[] tags = req.getTags();// 태그 내용
                if (tags.length == 0) {
                    post.setTag("태그");
                } else {
                    String ptag = "#";
                    for (int i = 0; i < tags.length; i++) {
                        ptag += tags[i] + "#";

                    }
                    post.setTag(ptag.substring(0, ptag.length() - 1));
                }
                postDao.save(post);
                int artiId = post.getArticleId();
                System.out.println("게시물 등록!!");

                // 게시물 등록과 동시에 참가자 등록하기
                int myPrice = req.getMyPrice();
                if (myPrice <= 0) {
                    String message = "price 값이 유효하지 않습니다.";
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                }
                String def_mes = "게시자 본인입니다.";
                Participant participant = new Participant();
                participant.setUserId(userOpt.get().getUserId()); // token값으로 id 받아옴
                participant.setArticleId(artiId);

                System.out.println(post.getTitle() + " " + artiId);
                participant.setTitle(def_mes);
                participant.setPrice(myPrice);
                participant.setWriter(userOpt.get().getNickname());
                participant.setStatus(1);// 게시자 본인은 활성화 상태
                participant.setDescription(def_mes);
                participantDao.save(participant);// 참가자 DB에 등록 완료
                tagAdd(tags, artiId);

                System.out.println("참가자 등록!!");

                return new ResponseEntity<>("태그 등록 및 참가자 등록 및 게시물 등록", HttpStatus.OK);

            } else if (temp == 2) { // 자유게시물
                Post post = new Post();
                post.setUserId(userOpt.get().getUserId());
                post.setCategoryId(req.getCategoryId());
                post.setTitle(req.getTitle());
                post.setWriter(userOpt.get().getNickname());
                post.setDescription(req.getDescription());
                post.setImage(req.getImage());
                post.setStatus(1);
                post.setTemp(temp);
                String ptag = "#자유글";

                if (req.getCategoryId() == 101) {
                    ptag = "#공지글";
                } else if (req.getCategoryId() == 102) {
                    ptag = "#후기글 ";
                } else if (req.getCategoryId() == 103) {
                    ptag = "#자유게시글";
                }

                post.setTag(ptag.substring(0, ptag.length() - 1));

                postDao.save(post);

                return new ResponseEntity<>("자유게시물 등록", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("로그인 상태를 확인하세요 ", HttpStatus.BAD_REQUEST);

        }
    }

    @Transactional(readOnly = true)
    @PostMapping("/post/detail/{articleId}") // SWAGGER UI에 보이는 REQUEST명
    @ApiOperation(value = "게시물상세보기") // SWAGGER UI에 보이는 이름
    public Object detail(@PathVariable int articleId, @RequestBody TokenRequest request) {
        // 토큰 받아오면 그 토큰으로 유효성 검사 후 uid 받아와서 좋아요 한지 여부 확인
        long before = System.currentTimeMillis();

        // System.out.println("상세보기 들어옴 " + request.toString());
        Post p = postDao.findPostByArticleId(articleId);

        if (p != null) {
            String token = request.getToken();
            User jwtuser = jwtService.getUser(token);
            System.out.println(jwtuser.toString());
            String tag = p.getTag();
            StringTokenizer st = new StringTokenizer(tag, "#");

            List<String> taglist = new LinkedList<>();
            while (!st.hasMoreTokens()) {
                taglist.add(st.nextToken());
            }

            Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());

            PostResponse result = new PostResponse();

            result.articleId = p.getArticleId();
            result.categoryId = p.getCategoryId();
            result.userId = p.getUserId();
            result.title = p.getTitle();
            result.address = p.getAddress();
            result.minPrice = p.getMinPrice();
            result.sumPrice = p.getSumPrice();
            result.likeNum = p.getLikeNum();
            result.commentNum = p.getCommentNum();
            result.description = p.getDescription();
            result.writer = p.getWriter();
            result.urlLink = p.getUrlLink();
            result.openLink = p.getOpenLink();
            result.image = p.getImage();
            result.tags = taglist;
            result.temp = p.getTemp();
            result.endTime = p.getEndTime();
            result.timeAgo = BeforeCreateTime(p.getCreateTime());
            result.createTime = p.getCreateTime();

            result.status = p.getStatus();
            // 이 게시물에 해당되는 태그는 다 보내기
            List<Tag> tlist = tagDao.findTagByArticleId(articleId);
            List<String> tags = new LinkedList<>();
            for (int i = 0; i < tlist.size(); i++) {
                tags.add(tlist.get(i).getName());
            }
            List<Participant> partlist = participantDao.findParticipantByArticleId(articleId);
            result.partList = partlist;
            result.tags = tags;
            result.nameList = new LinkedList<>();
            result.scoreList = new LinkedList<>();
            result.rgbCodeList = new LinkedList<>();
            int sum = 0;
            for (int i = 0; i < partlist.size(); i++) {
                if (partlist.get(i).getStatus() == 1) {
                    sum += partlist.get(i).getPrice();
                    result.nameList.add(partlist.get(i).getWriter());
                    result.scoreList.add(partlist.get(i).getPrice());
                    if (i == 0) {
                        result.rgbCodeList.add("#FF6A89");
                    } else if (i == 1) {
                        result.rgbCodeList.add("#50B4FF");
                    } else if (i == 2) {
                        result.rgbCodeList.add("#FFE13C");
                    } else if (i == 3) {
                        result.rgbCodeList.add("#93FB93");
                    } else if (i == 4) {
                        result.rgbCodeList.add("#DD78F6");
                    }
                }
            }
            if (sum >= p.getMinPrice()) {// 이미 금액 충족

            } else {
                result.nameList.add("남은금액");
                result.scoreList.add(p.getMinPrice() - sum);
                result.rgbCodeList.add("#ADADAD");
            }
            if (userOpt.isPresent()) {// 로그인 상태일때

                Optional<Like> isILiked = likeDao.findLikeByUserIdAndArticleId(userOpt.get().getUserId(), articleId);
                if (isILiked.isPresent()) // 좋아요 한 경우
                    result.isLiked = true;
                else // 좋아요 하지 않은경우
                    result.isLiked = false;

                System.out.println("게시물 상세보기 !!!");
            } else {
                System.out.println("비로그인 / 로그인 여부 확인 !!!");
                result.isLiked = false;
            }

            List<Comment> clist = commentDao.findCommentByArticleId(p.getArticleId());
            result.commentList = new LinkedList<>(); // 댓글 리스트 가져옴
            for (int i = 0; i < clist.size(); i++) {
                User user = userDao.getUserByUserId(clist.get(i).getUserId());
                String nickname = user.getNickname();
                CommentRes c = new CommentRes();
                c.setCommentId(clist.get(i).getCommentId());
                c.setArticleId(clist.get(i).getArticleId());
                c.setUserId(clist.get(i).getUserId());

                c.setStatus(clist.get(i).getStatus());
                if (clist.get(i).getStatus() == 0) {
                    c.setNickname("신고된 댓글");
                    c.setContent("신고된 댓글 입니다.");
                } else {
                    c.setNickname(nickname);
                    c.setContent(clist.get(i).getContent());
                }
                c.setTimeAgo(BeforeCreateTime(clist.get(i).getCreateTime()));
                c.setCreateTime(clist.get(i).getCreateTime());

                result.commentList.add(c);

                System.out.println(nickname);
            }

            System.out.println("리턴!!" + (System.currentTimeMillis() - before) + "초 ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/post/update/{temp}")
    @ApiOperation(value = "게시글 및 임시글 수정")
    public Object update(@Valid @RequestBody PostRequest req, @PathVariable int temp) {
        Post p = postDao.findPostByArticleId(req.getArticleId());

        String token = req.getToken();
        User jwtuser = jwtService.getUser(token);
        int userId;
        Optional<User> userOpt = userDao.findUserByEmail(jwtuser.getEmail());
        if (p != null) {
            userId = userOpt.get().getUserId();
        } else {
            return new ResponseEntity<>("로그인 상태를 확인하세요(token값 유효하지 않음)", HttpStatus.BAD_REQUEST);
        }

        if (userOpt.get().getUserId() != p.getUserId()) {
            return new ResponseEntity<>("로그인한 회원과 게시자가 일치하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        String endDate = req.getEndDate();
        String endT = req.getEndTime();
        LocalDateTime endTime = null;
        if (endDate != null || endT != null) {
            endTime = LocalDateTime.parse(endDate + " " + endT, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        if (temp == 0) {
            Post post = postDao.getPostByArticleId(req.getArticleId());
            post.setCategoryId(req.getCategoryId());
            post.setTitle(req.getTitle());
            post.setAddress(req.getAddress());
            post.setDescription(req.getDescription());
            post.setMinPrice(req.getMinPrice());
            post.setUrlLink(req.getUrlLink());
            post.setImage(req.getImage());
            post.setTemp(temp);
            post.setEndTime(endTime);

            postDao.save(post);

            System.out.println("임시글 수정");

            return new ResponseEntity<>("임시글 수정 완료", HttpStatus.OK);
        } else if (temp == 1) {

            Post post = postDao.getPostByArticleId(req.getArticleId());
            if (post.getStatus() == 1) { // 게시물이 거래대기중인 상태만
                post.setCategoryId(req.getCategoryId());
                post.setTitle(req.getTitle());
                post.setAddress(req.getAddress());
                post.setMinPrice(req.getMinPrice());
                post.setDescription(req.getDescription());
                post.setUrlLink(req.getUrlLink());
                post.setImage(req.getImage());
                post.setTemp(temp);
                post.setEndTime(endTime);

                postDao.save(post);

                int myPrice = req.getMyPrice();
                if (myPrice < 0) {
                    String message = "0원보다 값이 작습니다.";
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                }

                Participant part = participantDao.getParticipantByUserIdAndArticleId(userId, req.getArticleId());
                if (part == null) {// 존재하지않는 참가자입니다.
                    return new ResponseEntity<>("존재하지 않는 참가자 입니다.", HttpStatus.BAD_REQUEST);
                }

                int old_price = part.getPrice();// 원래 참가자의 가격정보

                part.setArticleId(req.getArticleId());
                part.setPrice(myPrice);
                participantDao.save(part);// 참가자 DB에 등록 완료

                // 게시물 sum_price에 더하기
                post = postDao.getPostByArticleId(req.getArticleId());// 해당 구매게시물을 얻어옴
                int sumPrice = post.getSumPrice();// sumPrice를 얻어옴
                sumPrice = sumPrice + (myPrice - old_price);// 참가자의 가격을 더해줌
                post.setSumPrice(sumPrice);
                postDao.save(post);// 다시 DB에 넣어줌

                tagDelete(req.getArticleId());
                // 태그 수정
                String[] tags = req.getTags();// 태그 내용
                tagAdd(tags, req.getArticleId());

                System.out.println(post.getArticleId() + "번째 게시물 수정 완료 ");
                return new ResponseEntity<>("게시물 수정 완료 ", HttpStatus.OK);
            } else {
                System.out.println("수정이 불가능한 게시물입니다.");
                return new ResponseEntity<>("수정이 불가능한 게시물 입니다.", HttpStatus.OK);
            }
        } else if (temp == 2) { // 자유게시판

            Post post = postDao.getPostByArticleId(req.getArticleId());

            post.setTitle(req.getTitle());
            post.setDescription(req.getDescription());
            post.setImage(req.getImage());
            postDao.save(post);

            // 태그 삭제
            tagDelete(req.getArticleId());
            // 태그 수정
            String[] tags = req.getTags();// 태그 내용
            tagAdd(tags, req.getArticleId());

            System.out.println(post.getArticleId() + "번째 자유글 수정 완료 ");
            return new ResponseEntity<>("자유글 수정 완료 ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/post/complete/{articleId}")
    @ApiOperation(value = "거래완료")
    public Object complete(@Valid @PathVariable int articleId) {
        Post post = postDao.findPostByArticleId(articleId);
        post.setStatus(4); // 거래완료로 변경
        List<Participant> partlist = participantDao.findParticipantByArticleId(articleId);
        // 서브쿼리 필요함 ..
        for (Participant part : partlist) {
            if (part.getStatus() == 1) {
                User user = userDao.getUserByUserId(part.getUserId());
                if (part.getUserId() == post.getUserId()) {
                    user.setUserPoint(user.getUserPoint() + 50);
                } else {
                    user.setUserPoint(user.getUserPoint() + 30);
                }
                int up = user.getUserPoint();
                if (up <= 1000) {
                    user.setGrade(1);
                } else if (up <= 1500) {
                    user.setGrade(2);
                } else if (up <= 2500) {
                    user.setGrade(3);
                } else if (up <= 4000) {
                    user.setGrade(4);
                } else {
                    user.setGrade(5);
                }
                userDao.save(user);
            } else if (part.getStatus() == 0) {
                part.setStatus(2);// 참가수락하지 않은 참가자는 거부로 변경
                participantDao.save(part);
            }
        }
        postDao.save(post);

        System.out.println("거래완료");
        return new ResponseEntity<>("자유글 수정 완료 ", HttpStatus.OK);
    }

    @DeleteMapping("/post/delete/{articleId}")
    @ApiOperation(value = "삭제하기")
    public Object delete(@Valid @PathVariable int articleId) {

        Post post = postDao.getPostByArticleId(articleId);
        if (post.getStatus() == 1) {
            // 참여자 삭제
            List<Participant> partList = participantDao.getParticipantByArticleId(articleId);
            int pasize = partList.size();
            for (int i = 0; i < pasize; i++) {
                Participant p = partList.get(i);
                participantDao.delete(p);
            }
            // 댓글 삭제
            List<Comment> commentList = commentDao.getCommentByArticleId(articleId);
            int csize = commentList.size();
            for (int i = 0; i < csize; i++) {
                Comment c = commentList.get(i);
                commentDao.delete(c);
            }
            /// 좋아요 삭제
            List<Like> likeList = likeDao.getLikeByArticleId(articleId);
            int lsize = likeList.size();
            for (int i = 0; i < lsize; i++) {
                Like l = likeList.get(i);
                likeDao.delete(l);
            }
            // 태그 삭제
            tagDelete(articleId);
            postDao.delete(post);
            System.out.println("삭제하기!! ");
            PostResponse result = new PostResponse();

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("삭제할 수 없는 상태의 게시물입니다.", HttpStatus.OK);
        }
    }

    @PostMapping("/file")
    public String fileTest(@RequestPart("file") MultipartFile ff) throws IllegalStateException, IOException {

        System.out.println(ff.toString());
        String forSaveImg = ff.getOriginalFilename().toLowerCase();
        System.out.println(forSaveImg.toString());
        long nowtime = datetimeTosec(LocalDateTime.now());
        System.out.println(System.getProperty("user.dir") + "\\frontend\\src\\assets\\images\\");
        // File file = new File("C:\\Users\\multicampus\\Desktop\\image\\"+ nowtime +
        // forSaveImg);
        File file = new File("/home/ubuntu/shallwe/s03p13b203/frontend/src/assets/images/" + nowtime + forSaveImg);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        ff.transferTo(file);
        System.out.println(file.getName());

        return file.getName();

    }

    private static String BeforeCreateTime(LocalDateTime createTime) {
        String result = "";
        int before = 0;
        LocalDateTime nowTime = LocalDateTime.now();
        if (createTime.getYear() <= nowTime.getYear() + 1) {
            if (createTime.getMonthValue() == nowTime.getMonthValue()) {
                if (createTime.getDayOfYear() == nowTime.getDayOfYear()) {
                    if (createTime.getHour() == nowTime.getHour()) {
                        if (createTime.getMinute() == nowTime.getMinute()) {
                            result = "약 1분 전";
                        } else {
                            before = nowTime.getMinute() - createTime.getMinute();
                            result = "약 " + before + "분 전";
                        }
                    } else if (createTime.getHour() == nowTime.getHour() + 1
                            && createTime.getMinute() > nowTime.getMinute()) {
                        before = 60 + nowTime.getMinute() - createTime.getMinute();
                        result = "약 " + before + "분 전";
                    } else {
                        before = nowTime.getHour() - createTime.getHour();
                        result = "약 " + before + "시간 전";
                    }
                } else if (createTime.getDayOfYear() == nowTime.getDayOfYear() + 1
                        && createTime.getHour() > nowTime.getHour()) {
                    before = 24 + nowTime.getHour() - createTime.getHour();
                    result = "약 " + before + "시간 전";
                } else {
                    before = nowTime.getDayOfYear() - createTime.getDayOfYear();
                    result = "약 " + before + "일 전";
                }
            } else if (createTime.getDayOfYear() == nowTime.getDayOfYear() + 1
                    && createTime.getHour() > nowTime.getHour()) {
                before = 24 + nowTime.getHour() - createTime.getHour();
                result = "약 " + before + "일 전";
            } else {
                before = nowTime.getDayOfYear() - createTime.getDayOfYear();
                result = "약 " + before + "일 전";
            }
        } else {
            before = nowTime.getYear() - createTime.getYear();

            result = "약 " + before + "년 전";
        }
        return result;

    }

    private void tagDelete(int articleId) {
        List<Tag> tList = tagDao.getTagByArticleId(articleId);
        int atsize = tList.size();
        for (int i = 0; i < atsize; i++) {
            Tag l = tList.get(i);
            tagDao.delete(l);
        }
    }

    private void tagAdd(String[] tags, int articleId) {
        for (int i = 0; i < tags.length; i++) {
            Tag tag = new Tag();
            tag.setName(tags[i]);
            tag.setArticleId(articleId);
            tagDao.save(tag);
        }
    }

    private long datetimeTosec(LocalDateTime ldt) {
        long result = 0L;
        result += ((((((((ldt.getYear() - 2000) * 365) + ldt.getDayOfYear()) * 24) + ldt.getHour()) * 60)
                + ldt.getMinute()) * 60);
        return result;
    }
}