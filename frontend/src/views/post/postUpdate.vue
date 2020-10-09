<template>
  <div
    class="post-container"
    style="font-family: 'Recipekorea', cursive; margin-left: 10%; margin-right: 10%;"
  >
    <div class="d-flex justify-content-between">
      <div class="ml-1" style="font-size:19px">자유게시판 수정</div>
      <div class="alert-msg">
        <small style="font-size: 13.5px; color: #ee6e9f;">*</small>
        <small style="font-size: 13.5px;">표시는 필수값입니다.</small>
      </div>
    </div>
    <table class="table mt-1" style="font-size:17px">
      <tbody>
        <tr>
          <th scope="row">
            제목
            <small style="font-size: 17px; color: #ee6e9f;">*</small>
          </th>
          <td class="d-flex">
            <b-form-input
              type="text"
              v-model="articleData.title"
            ></b-form-input>
          </td>
        </tr>
        <tr>
          <th scope="row">
            내용
            <small style="font-size: 17px; color: #ee6e9f;">*</small>
          </th>
          <td>
            <div>
              <b-form-textarea
                id="textarea-rows"
                rows="8"
                v-model="articleData.description"
              ></b-form-textarea>
            </div>
          </td>
        </tr>
        <tr>
          <th scope="row">사진첨부</th>
          <td>
            <input
              type="file"
              id="file"
              name="file"
              ref="file"
              style="width: 100%;font-size: 14px;"
            />
          </td>
        </tr>
      </tbody>
    </table>
    <hr />
    <div class="d-flex justify-content-center">
    <button class="complete-form our-main-font" type="submit" @click="postUpdate">
      수정
    </button>
    <button
      class="temp-form our-main-font"
      type="submit"
      @click="deleteArticle({ id: articleUpdateData.articleId, temp: 2 })"
    >
      삭제
    </button>
    </div>
    <div style="display: none;">{{aID}}</div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import { mapActions, mapState } from "vuex";

export default {
  name: "articleUpdate",
  data() {
    return {
      articleUpdateData: {},
    };
  },
  methods: {
    ...mapActions(["getArticle", "deleteArticle", "updateArticle"]),

    fileUpload: function() {
      var formData = new FormData();
      this.file = this.$refs.file.files[0];
      formData.append("file", this.file);
      formData.append("uid", 10);
      axios
        .post(`${BACK_URL}/file`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          this.path = response.data;
          this.articleUpdateData.image = this.path;
          var tmp = this.articleUpdateData.image.split(".");
          tmp[1] = tmp[1].toLowerCase();
          this.articleUpdateData.image = tmp[0] + "." + tmp[1];
        })
        .catch((error) => {
          console.log(error);
        });
    },
    postUpdate() {
      this.fileUpload();
      setTimeout(() => {
        var articleUpdateData = this.articleUpdateData;
        this.updateArticle({ articleUpdateData, temp: 2 });
      }, 300);
    },
  },
  created: function() {
    this.getArticle(this.$route.params.ID);
    this.articleUpdateData = this.articleData;
  },
  computed: {
    ...mapState(["articleData"]),
  },
};
</script>
<style></style>
