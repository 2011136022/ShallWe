<template>
  <div class="mt-1">
    <div class="d-flex">
      <div class="review-comment-container">
        <div class="comment-user">
          <router-link
            class="comment-user-link our-main-font"
            :to="{ name: 'userDetail', params: { ID: comment.userId } }"
          >
            <a class="comment-user-link our-main-font">{{
              comment.nickname
            }}</a>
          </router-link>
          <div
            class="comment-drop dropdown dropleft"
            v-if="comment.userId === userData.userId"
          >
            <button type="button" class="comment-btn" data-toggle="dropdown">
              <i class="fas fa-ellipsis-v"></i>
            </button>
            <div class="dropdown-menu our-main-font">
              <a
                class="dropdown-item our-main-font"
                @click="showInput"
                v-if="!flag"
                >수정</a
              >
              <a class="dropdown-item our-main-font" @click="deleteComment"
                >삭제</a
              >
            </div>
          </div>
          <div v-else>
            <button
              type="button"
              class="siren-btn our-main-font"
              data-toggle="modal"
              :data-target="'#exampleModal' + comment.commentId"
            >
              신고
            </button>

            <div
              class="modal fade"
              :id="'exampleModal' + comment.commentId"
              tabindex="-1"
              aria-labelledby="exampleModalLabel"
              aria-hidden="true"
            >
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5
                      class="modal-title our-main-font"
                      id="exampleModalLabel"
                    >
                      신고 접수
                    </h5>
                    <button
                      type="button"
                      class="close"
                      data-dismiss="modal"
                      aria-label="Close"
                    >
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <h6 class="our-main-font">신고 사유</h6>
                    <div class="dropdown">
                      <button
                        class="btn btn-secondary btn-sm dropdown-toggle our-main-font"
                        type="button"
                        id="dropdownMenuButton"
                        data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false"
                      >
                        선택
                      </button>
                      <div
                        class="dropdown-menu"
                        aria-labelledby="dropdownMenuButton"
                      >
                        <a
                          class="dropdown-item our-main-font"
                          href="#"
                          @click="changeAccuseKind(1)"
                          >욕설, 비난을 했어요!</a
                        >
                        <a
                          class="dropdown-item our-main-font"
                          href="#"
                          @click="changeAccuseKind(2)"
                          >약속 장소에 나오지 않았어요!</a
                        >
                        <a
                          class="dropdown-item our-main-font"
                          href="#"
                          @click="changeAccuseKind(3)"
                          >광고를 하고 있어요!</a
                        >
                      </div>
                    </div>
                    <h6 class="mt-3 our-main-font">사유 상세</h6>
                    <b-form-textarea
                      id="textarea-rows"
                      rows="8"
                      v-model="accuseCommentData.accuseReason"
                    ></b-form-textarea>
                    <h6 class="mt-3 our-main-fonts">신고할 게시물 URL</h6>
                    <b-form-input
                      style="width: 400px"
                      id="type-url"
                      type="url"
                      v-model="accuseCommentData.accuseUrl"
                    ></b-form-input>
                    <hr />
                    <button
                      @click="createCommentAccuse({ accuseCommentData })"
                      class="btn btn-danger btn-sm our-main-font"
                    >
                      신고접수
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="comment-content our-main-font">{{ comment.content }}</div>
        <div class="comment-create-time our-main-font">
          {{ cutDate(comment.createTime) }}
        </div>
      </div>
    </div>

    <div v-if="flag" class="comment-update-write">
      <div class="comment-update-text">
        <input class="comment-input" type="text" v-model="comment.content" />
      </div>
      <div class="comment-update-submit">
        <button
          type="button"
          v-if="flag"
          class="comment-update-btn our-main-font"
          @click="updateCancel"
        >
          취소
        </button>
        <button
          type="button"
          v-if="flag"
          class="comment-update-btn our-main-font"
          @click="updateComment"
        >
          수정
        </button>
      </div>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import cookies from "vue-cookies";
import { mapState, mapActions } from "vuex";

export default {
  name: "commentListItem",
  props: {
    comment: Object,
    reviewId: Number,
  },
  data() {
    return {
      watchFlag: false,
      flag: false,
      canceldata: {
        articleId: this.comment.articleId,
        commentId: this.comment.commentId,
        content: this.comment.content,
        userId: this.comment.userId,
        nickname: this.comment.nickname,
        createTime: this.comment.createTime,
        token: this.$cookies.get("auth-token"),
      },
      accuseCommentData: {
        reporter: "",
        defendant: this.comment.nickname,
        accuseIndex: 2,
        accuseValue: this.comment.commentId,
        accuseKind: 0,
        accuseReason: "",
        accuseUrl: "",
        accuseConfirm: 0,
        token: this.$cookies.get("auth-token"),
      },
    };
  },
  computed: {
    ...mapState(["userData"]),
  },
  methods: {
    ...mapActions(["createCommentAccuse"]),
    cutDate(date) {
      let CD = date + "";
      const year = CD.substring(0, 4) + ".";
      const month = CD.substring(5, 7) + ".";
      const day = CD.substring(8, 10) + ". ";
      const hour = CD.substring(11, 13) + ":";
      const minute = CD.substring(14, 16) + "";
      const res = year + month + day + hour + minute;
      return res;
    },
    linkCommentData() {
      this.accuseCommentData.accuseValue = this.comment.commentId;
    },
    changeAccuseKind(kind) {
      this.accuseCommentData.accuseKind = kind;
      this.linkUserData();
      this.linkCommentData();
    },
    linkUserData() {
      this.accuseCommentData.reporter = this.userData.nickname;
    },

    deleteComment() {
      const auth = { token: this.$cookies.get("auth-token") };
      axios
        .delete(BACK_URL + `/comment/delete/${this.comment.commentId}`, auth)
        .then(() => {
          this.watchFlag = true;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    showInput() {
      this.flag = true;
    },
    updateComment() {
      axios
        .put(BACK_URL + "/comment/update", this.comment)
        .then((response) => {
          this.flag = false;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    updateCancel() {
      this.comment = this.canceldata;
      this.watchFlag = true;
      this.flag = false;
    },
  },
  watch: {
    watchFlag() {
      this.$emit("re-render");
    },
  },
};
</script>

<style>
.siren-btn {
  border: none;
  outline: none;
  background-color: transparent;
  color: rgb(218, 37, 37);
  font-size: 80%;
  font-weight: bold;
}
.siren-btn:hover {
  border: none;
  outline: none;
  background-color: transparent;
  color: rgb(224, 34, 34);
}
.siren-btn:focus {
  border: none;
  outline: none;
  background-color: transparent;
  color: rgb(224, 34, 34);
}
.review-comment-container {
  border-bottom: 1px solid rgb(237, 237, 240);
  display: flex;
  flex-direction: column;
  width: 95%;
  margin: 0 auto 1% auto;
  padding: 0 0 0 0.3%;
}
.comment-user {
  text-align: left;
  vertical-align: -webkit-baseline-middle;
  font-weight: bold;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.comment-user-link {
  padding-top: 10px;
}
.comment-content {
  text-align: left;
}
.comment-create-time {
  text-align: left;
  color: rgb(145, 141, 141);
  font-size: small;
  margin-bottom: 1%;
}
.comment-drop {
  display: flex;
  flex-direction: row;
  width: 3%;
}
.comment-btn {
  background-color: transparent;
  color: rgb(182, 182, 182);
  padding: 0;
  font-size: 15px;
  border: none;
  cursor: pointer;
  width: 100%;
  outline: none;
}
.comment-drop:hover {
  border: none;
  outline: none;
}
.comment-btn:hover {
  border: none;
  outline: none;
}
.comment-drop:hover .comment-btn {
  background-color: transparent;
  color: rgb(145, 141, 141);
  border: none;
  outline: none;
}
.comment-btn:focus {
  border: none;
  outline: none;
}
.comment-update-write {
  border: 2px solid rgba(0, 0, 0, 0.1);
  border-radius: 6px;
  width: 100%;
  margin: 1% auto;
  padding: 16px 10px 10px 18px;
  display: flex;
  flex-direction: column;
}
.comment-update-submit {
  display: flex;
  justify-content: flex-end;
  margin: 0 4px 3px 0;
}
.comment-update-btn {
  font-weight: bold;
  border: none;
  outline: none;
  background-color: transparent;
  color: grey;
}
.comment-update-text {
  display: block;
}
.comment-update-input {
  border: none;
  outline: none;
  width: 100%;
}
</style>
