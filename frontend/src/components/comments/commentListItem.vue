<template>
  <div>
    <div class="d-flex">
      <div class="comment-box">
        <div class="comment-user" style="font-size:17px">
          <router-link
            :to="{ name: 'userDetail', params: { ID: comment.userId } }"
            >{{ comment.nickname }}</router-link
          >
          <div
            class="comment-drop dropdown dropleft"
            v-if="comment.userId === userData.userId"
          >
            <button type="button" class="comment-btn" data-toggle="dropdown">
              <i class="fas fa-ellipsis-v"></i>
            </button>
            <div class="dropdown-menu">
              <a
                class="dropdown-item comment-update-btn"
                @click="showInput"
                v-if="!flag"
                >수정</a
              >
              <a class="dropdown-item" @click="deleteComment">삭제</a>
            </div>
          </div>
          <div v-else>
            <b-button
              v-if="comment.status === 1"
              v-b-modal="'modal-' + `${this.comment.commentId}`"
              class="siren-btn"
              >신고</b-button
            >
            <div style="font-family: 'Recipekorea', cursive; font-size:18px">
              <b-modal
                :hide-footer="true"
                :id="'modal-' + this.comment.commentId"
                title="신고 접수"
                style="font-family: 'Recipekorea', cursive; font-size:18px"
              >
                <h6 style="font-family: 'Recipekorea', cursive; font-size:18px">
                  신고 사유
                </h6>
                <div
                  class="dropdown"
                  style="font-family: 'Recipekorea', cursive; font-size:18px"
                >
                  <button
                    class="btn btn-secondary btn-sm dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton"
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  >
                    {{ selectedKind }}
                  </button>
                  <div
                    class="dropdown-menu"
                    aria-labelledby="dropdownMenuButton"
                  >
                    <a
                      class="dropdown-item"
                      href="#"
                      @click="changeAccuseKind(1)"
                      >욕설, 비난을 했어요!</a
                    >
                    <a
                      class="dropdown-item"
                      href="#"
                      @click="changeAccuseKind(2)"
                      >약속 장소에 나오지 않았어요!</a
                    >
                    <a
                      class="dropdown-item"
                      href="#"
                      @click="changeAccuseKind(3)"
                      >광고를 하고 있어요!</a
                    >
                  </div>
                </div>
                <h6
                  class="mt-3"
                  style="font-family: 'Recipekorea', cursive; font-size:18px"
                >
                  사유 상세
                </h6>
                <b-form-textarea
                  id="textarea-rows"
                  style="font-family: 'Recipekorea', cursive; width: 400px; font-size:18px"
                  rows="8"
                  v-model="accuseCommentData.accuseReason"
                ></b-form-textarea>
                <hr />
                <button
                  @click="createCommentAccuse({ accuseCommentData })"
                  class="btn btn-danger btn-sm"
                  style="font-family: 'Recipekorea', cursive; font-size:18px"
                >
                  신고접수
                </button>
              </b-modal>
            </div>
          </div>
        </div>
        <div class="comment-content mt-1" style="font-size:18px">
          {{ comment.content }}
        </div>
        <div class="comment-create-time mt-1" style="font-size:12px">
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
          class="comment-update-btn"
          @click="updateCancel"
        >
          취소
        </button>
        <button
          type="button"
          v-if="flag"
          class="comment-update-btn"
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
import { mapState, mapActions } from "vuex";

export default {
  name: "commentListItem",
  props: {
    comment: Object,
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
        accuseUrl: document.URL,
        accuseConfirm: 0,
        token: this.$cookies.get("auth-token"),
      },
      selectedKind: "선택",
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
      if (kind === 1) {
        this.selectedKind = "욕설, 비난을 했어요!";
      } else if (kind === 2) {
        this.selectedKind = "약속 장소에 나오지 않았어요!";
      } else {
        this.selectedKind = "광고를 하고 있어요!";
      }
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
.comment-box {
  border-bottom: 1px solid rgb(237, 237, 240);
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-bottom: 1%;
  padding: 0 0 0 0.3%;
}
.comment-user {
  text-align: left;
  font-weight: bold;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
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
.comment-btn:focus {
  border: none;
  outline: none;
}
.comment-drop:hover .comment-btn {
  background-color: transparent;
  color: rgb(145, 141, 141);
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
