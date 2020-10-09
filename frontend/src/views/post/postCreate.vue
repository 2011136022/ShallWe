<template>
  <div
    class="post-container"
    style="font-family: 'Recipekorea', cursive; margin-left: 10%; margin-right: 10%;"
  >
    <div class="d-flex justify-content-between">
      <div class="ml-1" style="font-size:19px">자유게시판 글쓰기</div>
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
    <div class="center-btn">
      <button
        style="font-size: 15px;"
        class="temp-form"
        @click="createArticle({ articleData, temp: 0 })"
      >
        임시저장
      </button>
      <button
        style="font-size: 15px;"
        class="complete-form"
        type="submit"
        @click="postCreate"
        value="Submit"
      >
        <i class="fas fa-check"></i> 완료
      </button>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL
import axios from "axios";
import { mapActions } from "vuex";

export default {
  name: "CreateView",
  data() {
    return {
      articleData: {
        categoryId:103,
        title: null,
        description: null,
        token: this.$cookies.get("auth-token"),
      },
      imageUrl: null,
      value: [],
      selectedTBG: "카테고리",
      file: "",
    };
  },
  methods: {
    ...mapActions(["createArticle", "tempSaveArticle"]),
    fileUpload: function() {
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
      if (this.articleData.description&&this.articleData.title) {
        this.fileUpload();
        setTimeout(() => {
          var articleData = this.articleData;
          this.createArticle({ articleData, temp: 2 });
        }, 300);
      } else {
          Swal.fire({
            icon: "success",
            height: 300,
            width: 400,
            title:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">제목과 내용은 필수값입니다.</a>',
            confirmButtonText:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
            confirmButtonColor: "#ee6e9f",
          });
      }
    },
  },
};
</script>

<style>
.post-container {
  margin: 7% 0;
  text-align: left;
}
.center-btn {
  display: flex;
  justify-content: center;
}
</style>
