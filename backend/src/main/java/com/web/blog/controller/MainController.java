package com.web.blog.controller;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.web.blog.dao.PostDao;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostListResponse;
import com.web.blog.model.post.PostResponse;
import com.web.blog.model.user.TokenRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = PostResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = PostResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = PostResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = PostResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class MainController {
    @Autowired
    PostDao postDao;

    @PostMapping("/main/post")
    @ApiOperation(value = "메인페이지 목록")
    public PostListResponse mainRead(@RequestBody TokenRequest req) {
        PostListResponse  result = new PostListResponse();
        // List<Post> plist = new ArrayList<>();
        // MainResponse mainres = new MainResponse();
        result.recentList = new LinkedList<>();
        result.deadLineList = new LinkedList<>();
        result.bestReviewList = new LinkedList<>();
        int max = 6;
        long before = System.currentTimeMillis();
        List<Post> recentList = new LinkedList<>();
        List<Post> deadLineList = new LinkedList<>();
        List<Post> bestReviewList = new LinkedList<>();

        List<Post> list;
        max = 6;
        System.out.println("일반게시물 실시간 게시물");// temp:1 category:0 생성타임내림차순 status:1,2
        list = postDao.findPostByStatusNotAndTempOrderByCreateTimeDesc(0,1);// 일반 게시물 뽑아 온 다음
        for (int i = 0; i < list.size(); i++) {
            if (max == 0)
                break;
            if (list.get(i).getStatus() == 1 || list.get(i).getStatus() == 2|| list.get(i).getStatus()==3) {
                recentList.add(list.get(i));// status에서 1 or 2 뽑아줌
                max--;
            }
        }

        List<Post> elist;
        max = 6;
        System.out.println("마감임박 게시물");// temp:1 category:0 마감시간오름차순 status:1,2
        elist = postDao.findPostByStatusNotAndTempOrderByEndTimeAsc(0,1);// 일반 게시물 뽑아 온 다음
        for (int i = 0; i < elist.size(); i++) {
            if (max == 0)
                break;
            if (elist.get(i).getStatus() == 1 || elist.get(i).getStatus() == 2 || elist.get(i).getStatus()==3) {
                deadLineList.add(elist.get(i));// status에서 1 or 2 뽑아줌
                max--;
            }
        }

        List<Post> likelist;
        max = 6;
        System.out.println("좋아요수에 따른 베스트 후기");// temp:2 category:102 좋아요수내림차순
        likelist = postDao.findPostByStatusNotAndTempAndCategoryIdOrderByLikeNumDesc(0,2, 102);
        for (int i = 0; i < likelist.size(); i++) {
            if (max == 0)
                break;
            if (likelist.get(i).getStatus() == 1 ) {
                bestReviewList.add(likelist.get(i));// status에서 1 or 2 뽑아줌
                max--;
            }
        }

        result.recentList = getPostList(recentList); // 게시물 목록 및 각 게시물의 좋아요 댓글 수
        result.deadLineList = getPostList(deadLineList); // 게시물 목록 및 각 게시물의 좋아요 댓글 수
        result.bestReviewList = getPostList(bestReviewList); // 게시물 목록 및 각 게시물의 좋아요 댓글 수
        System.out.println("리턴!!" + (System.currentTimeMillis() - before) + "초 ");
        return result;
    }

    private List<PostResponse> getPostList(List<Post> plist) {
        List<PostResponse> result = new LinkedList<>();

        for (int i = 0; i < plist.size(); i++) { // 각 게시물 마다 좋아요 수 가져오기
            Post p = plist.get(i);

            List<String> taglist = new LinkedList<>();
            String tag = p.getTag();
            if (tag != null) {
                StringTokenizer st = new StringTokenizer(tag, "#");
                while (st.hasMoreTokens()) {
                    taglist.add(st.nextToken());
                }
            }

            PostResponse presp = new PostResponse();

            presp.articleId = p.getArticleId();
            presp.categoryId = p.getCategoryId();
            presp.userId = p.getUserId();
            presp.title = p.getTitle();
            presp.address = p.getAddress();
            presp.minPrice = p.getMinPrice();
            presp.sumPrice = p.getSumPrice();
            presp.likeNum = p.getLikeNum();
            presp.commentNum = p.getCommentNum();
            presp.description = p.getDescription();
            presp.writer = p.getWriter();
            presp.urlLink = p.getUrlLink();
            presp.openLink = p.getOpenLink();
            presp.image = p.getImage();
            presp.tags = taglist;
            presp.temp = p.getTemp();
            presp.endTime = p.getEndTime();
            presp.timeAgo = BeforeCreateTime(p.getCreateTime());
            presp.createTime = p.getCreateTime();

            result.add(presp);
        }

        return result;
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

}