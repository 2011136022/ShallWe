<template>
  <div class="review-comment-box">
    <div class="review-comment-start our-main-font">
      <i class="far fa-comment-dots"></i>
      댓글 {{ commentList.length }}
    </div>
    <div class="review-comment-list-box mb-3">
      <reviewCommentListItem
        v-for="comment in commentList"
        :key="comment.commentId"
        :comment="comment"
        :reviewId="reviewId"
        @re-render="delComment(reviewId)"
      />
    </div>
    <div class="review-comment-write">
      <div class="review-comment-text">
        <input
          class="review-comment-input our-main-font"
          placeholder="댓글을 남겨보세요 :)"
          type="text"
          v-model="commentData.content"
        />
      </div>
      <div class="review-comment-submit">
        <button type="button" class="comment-submit-btn our-main-font" @click="createComment">
          등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import cookies from "vue-cookies";
import { mapState, mapActions, mapMutations } from "vuex";
import reviewCommentListItem from "@/components/reviews/reviewCommentListItem";

export default {
  name: "commentList",
  components: {
    reviewCommentListItem,
  },
  props: {
    reviewId: Number,
    commentList: Array,
  },
  data() {
    return {
      commentData: {
        articleId: this.reviewId,
        content: "",
        token: this.$cookies.get("auth-token"),
      },
    };
  },
  computed: {
    ...mapState(["comments"]),
  },
  methods: {
    ...mapActions(["getArticle"]),
    ...mapMutations(["GET_COMMENTS"]),
    delComment(reviewID) {
      const auth = { token: cookies.get("auth-token") };
      axios
        .post(`${BACK_URL}/post/detail/${reviewID}`, auth)
        .then((response) => {
          this.commentList = response.data.commentList;
          this.$emit("lengthCheck", this.commentList.length);
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getReview(reviewID) {
      const auth = { token: cookies.get("auth-token") };
      axios
        .post(`${BACK_URL}/post/detail/${reviewID}`, auth)
        .then((response) => {
          this.commentList.push(
            response.data.commentList[response.data.commentList.length - 1]
          );
        })
        .catch((err) => {
          console.error(err);
        });
    },
    createComment() {
      if (this.commentData.content) {
        axios
          .post(`${BACK_URL}/comment/create`, this.commentData)
          .then(() => {
            this.commentData.content = "";
            this.getReview(this.reviewId);
            this.$emit("lengthCheck", this.commentList.length);
          })
          .catch((err) => {
            console.error(err);
          });
      } else {
         Swal.fire({
            icon: 'warning',
            height: 300,
            width: 300,  
            title: '<a style="font-size:20px; font-family: Recipekorea; color:black">댓글을 작성해주세요.</a>',
            showConfirmButton: false,
            timer: 1500
          })
      }
    },
  },
  created() {
    this.getArticle(this.reviewId);
  },
};
</script>

<style>
.review-comment-box {
  width: 100%;
}
.review-comment-start {
  width: 95%;
  padding: 0 0 0.5% 0.5%;
  text-align: left;
  border-bottom: 0.5px solid rgb(218, 215, 215);
  margin: 0 auto;
}
.review-comment-start-line {
  width: 85%;
}
.review-comment-write {
  border: 2px solid rgba(0, 0, 0, 0.1);
  border-radius: 6px;
  width: 95%;
  margin: 1% auto;
  padding: 16px 10px 10px 18px;
  display: flex;
  flex-direction: column;
}
.review-comment-submit {
  display: flex;
  justify-content: flex-end;
  margin: 0 4px 3px 0;
}
.review-comment-submit-btn {
  font-weight: bold;
  border: none;
  outline: none;
  background-color: transparent;
  color: grey;
}
.review-comment-text {
  display: block;
}
.review-comment-input {
  border: none;
  outline: none;
  width: 100%;
}
.review-comment-input:focus::placeholder {
  color: transparent;
}
.review-comment-list-box {
  max-height: 230px;
  overflow: auto;
}
.review-comment-list-box::-webkit-scrollbar {
  width: 7.5px;
}
.review-comment-list-box::-webkit-scrollbar-track {
  background-color: rgb(184, 184, 184);
  border-radius: 10px;
  box-shadow: inset 0px 0px 5px rgb(209, 205, 205);
}
.review-comment-list-box::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background: #727171;
  background-clip: padding-box;
  border: 2px solid transparent;
}
</style>
