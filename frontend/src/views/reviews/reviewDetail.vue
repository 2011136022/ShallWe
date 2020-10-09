<template>
  <div class="container mt-3">
    <div v-if="!isdeleted" class="review-container">
      <div class="review-top">
        <div
          class="review-drop dropdown dropleft m-0 pr-3"
          v-if="review.userId === userData.userId"
        >
          <button type="button" class="review-btn" data-toggle="dropdown">
            <i class="fas fa-ellipsis-h"></i>
          </button>
          <div class="dropdown-menu">
            <router-link
              class="reviewUpdate"
              :to="{
                        name: 'reviewUpdate',
                        params: { ID: this.review.articleId },
                      }"
            >
              <a class="dropdown-item our-main-font">수정</a>
            </router-link>
            <a
              class="dropdown-item our-main-font"
              @click="deleteReview(review.articleId)"
              >삭제</a
            >
          </div>
        </div>
        <div
          :class="{
            'review-info': review.userId !== userData.userId,
            'review-info-ver': review.userId === userData.userId,
          }"
        >
          <div class="review-writer our-main-font p-1">{{ review.writer }}</div>
          <div class="review-time our-main-font">{{ review.timeAgo }}</div>
        </div>
        <div class="review-middle">
          <div class="review-title our-main-font p-1">{{ review.title }}</div>
          <div class="review-like-comment">
            <div class="like-container our-main-font">
              <reviewLike
                @like-change="likeChange"
                :isLiked="isLiked"
                :review="review"
              />
              {{ review.likeNum }}
            </div>
          </div>
        </div>
      </div>
      <div
        class="collapse multi-collapse review-bottom mt-3"
        :id="'review' + review.articleId"
      >
        <div class="review-point d-flex">
          <div v-if="review.image" :class="{ 'review-img-box': review.image }">
            <b-img
              :src="imageUrl"
              fluid
              alt="Responsive image"
              class="review-img"
            ></b-img>
          </div>
          <div
            :class="{
              'review-content': review.image,
              'review-content-ver': review.image == null,
            }"
          >
            <div
              :class="{
                'review-description': review.image,
                'review-description-ver': review.image == null,
              }"
              class="our-main-font"
            >
              {{ review.description }}
            </div>
            <reviewCommentList
              :commentList="review.commentList"
              :reviewId="review.articleId"
              @lengthCheck="lengthCheck"
            />
          </div>
        </div>
      </div>
      <div class="arrow">
        <a
          data-toggle="collapse"
          :href="'#' + toggleId(review.articleId)"
          role="button"
          aria-expanded="false"
          aria-controls="review11"
        >
          <i class="fas fa-angle-down"></i>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import { mapState, mapActions } from "vuex";
import reviewCommentList from "@/components/reviews/reviewCommentList";
import reviewLike from "@/components/reviews/reviewLike";

export default {
  name: "reviewDetail",
  components: {
    reviewCommentList,
    reviewLike,
  },
  props: {
    review: Object,
  },
  data() {
    return {
      isdeleted: false,
      isLiked: false,
      likeFlag: false,
      mainProps: { width: 450, height: 450 },
    };
  },
  computed: {
    ...mapState(["articleData", "userData"]),
    imageUrl() {
      try {
        return "http://i3b203.p.ssafy.io/img/" +
          `${this.review.image}`;
      } catch {}
    },
  },
  methods: {
    ...mapActions([
      "getArticle",
      "createArticleAccuse",
      "deleteArticle",
    ]),
    deleteReview(reviewID) {
      const auth = { token: this.$cookies.get("auth-token") };
      axios
        .delete(`${BACK_URL}/post/delete/${reviewID}`)
        .then(() => {
          this.isdeleted = true;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    likeCheck() {
      const auth = { token: this.$cookies.get("auth-token") };
      axios
        .post(BACK_URL + `/likedcheck/${this.review.articleId}`, auth)
        .then((response) => {
          this.isLiked = response.data.isLiked;
        })
        .catch((err) => console.log(err));
    },
    likeChange() {
      this.likeCheck();
      if (this.isLiked == true) {
        this.review.likeNum -= 1;
      } else {
        this.review.likeNum += 1;
      }
    },
    toggleId(num) {
      const result = "review" + num;
      return result;
    },
    lengthCheck(commentLength) {
      this.review.commentList.length = commentLength;
    },
  },
  created: function() {
    this.likeCheck();
  },
};
</script>

<style>
.review-container {
  border: 1px solid rgb(225, 225, 233);
  border-radius: 5px;
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}
.review-drop {
  display: flex;
  justify-content: flex-end;
}
.review-btn {
  border: none;
  outline: none;
  background-color: transparent;
  color: rgb(197, 192, 192);
}
.review-info {
  display: flex;
  justify-content: space-between;
  margin: 15px 20px 7px 20px;
}
.review-info-ver {
  display: flex;
  justify-content: space-between;
  margin: 2px 20px 7px 20px;
}
.review-title {
  font-weight: bold;
  font-size: 20px;
  margin-left: 5.5px;
}
.review-time {
  opacity: 0.7;
}
.review-writer {
  opacity: 0.8;
}
.review-title,
.review-writer,
.review-time {
  letter-spacing: -0.6px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  line-height: 1.2;
}
.review-middle {
  display: flex;
  justify-content: space-between;
  vertical-align: middle;
  padding: 0px 15px;
}
.review-like-comment {
  display: flex;
}
.like-container,
.review-comment {
  display: flex;
}
.like-container {
  margin: 0 5px;
}
.comment-icon {
  margin: 0 3px;
}
.review-img-box {
  text-align: left;
  padding: 0px 0px 0 20px;
  width: 57%;
}
.arrow {
  opacity: 0.6;
}
.review-description {
  padding: 0 12px;
  height: 50%;
  overflow: auto;
}
.review-description-ver {
  padding: 0 26px;
  margin: 10px 0 50px 0;
}
.review-content {
  display: flex;
  flex-direction: column;
  text-align: left;
  width: 42%;
  height: 100%;
}
.review-content-ver {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: left;
  width: 100%;
}
</style>