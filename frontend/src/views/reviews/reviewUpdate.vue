<template>
  <div class="post-container" style="margin-left: 10%; margin-right: 10%">
    <h5 class="pl-sm-2 pb-sm-1">
      <strong class="our-main-font">후기 수정</strong>
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
import articleUpdateVue from '../articles/articleUpdate.vue';

export default {
  name: "reviewUpdate",
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
  },
  mounted(){
    this.getArticle(this.$route.params.ID);
  },
  computed: {
    ...mapState(["articleData"]),
    aID(){
      this.articleUpdateData=this.articleData
      return this.articleUpdateData.articleId
    }
  },
};
</script>
<style></style>