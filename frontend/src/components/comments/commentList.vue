<template>
  <div class="comments-box mt-4" style="font-family: 'Recipekorea', cursive;">
    <div class="comment-start" style="font-size:19px">
      <i class="far fa-comment-dots"></i>
      댓글 {{ comments.length }}
    </div>
    <commentListItem
      v-for="comment in comments"
      :key="comment.commentId"
      :comment="comment"
      @re-render="getArticle($route.params.ID)"
    />
    <div class="comment-write">
      <div class="comment-text">
        <input
          class="comment-input"
          placeholder="댓글을 남겨보세요 :)"
          type="text"
          v-model="commentData.content"
        />
      </div>
      <div class="comment-submit">
        <button type="button" class="comment-submit-btn" @click="createComment">
          등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import { mapState, mapActions, mapMutations } from "vuex";
import commentListItem from "@/components/comments/commentListItem";

export default {
  name: "commentList",
  components: {
    commentListItem,
  },
  props: {
  },
  data() {
    return {
      commentData: {
        articleId: this.$route.params.ID,
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
    createComment() {
      if (this.commentData.content) {
        axios
          .post(`${BACK_URL}/comment/create`, this.commentData)
          .then(() => {
            this.commentData.content = "";
            this.getArticle(this.$route.params.ID);
          })
          .catch((err) => {
            console.error(err);
          });
      } else {
        alert("댓글을 입력해주세요.");
      }
    },
  },
};
</script>

<style>
.comments-box {
  width: 75%;
  margin: auto;
}
.comment-start {
  width: 100%;
  padding: 0 0 0.5% 0.5%;
  text-align: left;
  border-bottom: 0.5px solid rgb(218, 215, 215);
  margin: 0 0 2% 0;
}
.comment-start-line {
  width: 85%;
}
.comment-write {
  border: 2px solid rgba(0, 0, 0, 0.1);
  border-radius: 6px;
  width: 100%;
  margin: 5% auto;
  padding: 16px 10px 10px 18px;
  display: flex;
  flex-direction: column;
}
.comment-submit {
  display: flex;
  justify-content: flex-end;
  margin: 0 4px 3px 0;
}
.comment-submit-btn {
  border: none;
  outline: none;
  background-color: transparent;
  color: grey;
  font-size: 16.5px;
}
.comment-submit-btn:hover {
  border: none;
  outline: none;
}
.comment-submit-btn:focus {
  border: none;
  outline: none;
}
.comment-text {
  display: block;
}
.comment-input {
  border: none;
  outline: none;
  width: 100%;
}
.comment-input::placeholder {
  font-size: 16px;
}
.comment-input:focus::placeholder {
  color: transparent;
}
</style>
