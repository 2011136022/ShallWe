<template>
  <div class="post-container" style="margin-left: 10%; margin-right: 10%">
    <h5 class="pl-sm-2 pb-sm-1">
      <strong class="our-main-font">후기 쓰기</strong>
    </h5>
            <div class="create-alert-msg">
          <small style="font-size: 15.5px; color: #ee6e9f;" class="our-main-font">*</small>
          <small style="font-size: 15.5px;" class="our-main-font">표시는 필수값입니다.</small>
        </div>
    <table class="table mt-1">
      <tbody>
        <tr>
          <th scope="row" class="our-main-font">제목<small style="font-size: 17px; color: #ee6e9f;">*</small></th>
          <td class="d-flex">
            <b-form-input type="text" v-model="articleData.title"></b-form-input>
          </td>
        </tr>
        <tr>
          <th scope="row" class="our-main-font">내용<small style="font-size: 17px; color: #ee6e9f;">*</small></th>
          <td>
            <div>
              <b-form-textarea id="textarea-rows" rows="8" v-model="articleData.description"></b-form-textarea>
            </div>
          </td>
        </tr>
        <tr>
          <th scope="row" class="our-main-font">사진첨부</th>
          <td>
          <div class="">
            <input type="file" id="file" name="file" ref="file" class="w-100 our-main-font"/>
          </div>
          </td>
        </tr>
      </tbody>
    </table>
    <hr />
    <button class="temp-form our-main-font" @click="createArticle({articleData,temp:0})">임시저장</button>
    <button
      class="complete-form our-main-font"
      type="submit"
      @click="createArticle({articleData,temp:2})"
      value="Submit"
    >
      <i class="fas fa-check"></i> 완료
    </button>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL
import axios from "axios";
import { mapActions } from "vuex";

export default {
  name: "reviewCreate",
  data() {
    return {
      articleData: {
        categoryId: 102,
        title: null,
        description: null,
        token: this.$cookies.get("auth-token"),
      },
      imageUrl: null,
      value: [],
    };
  },
  methods: {
    ...mapActions(["createArticle", "tempSaveArticle"]),
    imageUpload() {
      this.$refs.imageInput.click();
    },
    fileUpload: function () {
      var formData = new FormData();
      this.file = this.$refs.file.files[0];
      formData.append("file", this.file);
      axios
        .post(`${BACK_URL}/file`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          this.path = response.data;
          this.articleData.image = this.path;
          var tmp = this.articleData.image.split(".");
          tmp[1] = tmp[1].toLowerCase();
          this.articleData.image = tmp[0] + "." + tmp[1];
        })
        .catch((error) => {
          console.log(error);
        });
    },
    postCreate() {
      this.fileUpload();
      setTimeout(() => {
        var articleData = this.articleData;
        this.createArticle({ articleData, temp: 2 });
      }, 300);
    },
  },
};
</script>

<style>
.post-container {
  margin: 7% 0 0 0;
  text-align: left;
}
</style>