<template>
  <div class="mt-5 create-container" style="font-family: 'Recipekorea', cursive; font-size:16.5px">
    <div class="create-form">
      <div class="kakao-map-select">
        <kakaoMap :coNum="coNum" @setAddress="setAddress" />
      </div>
      <div class="right-items">
        <div class="create-alert-msg">
          <small style="font-size: 15.5px; color: #ee6e9f;">*</small>
          <small style="font-size: 15.5px;">표시는 필수값입니다.</small>
        </div>
      <div class="selected-place">
        <label style="font-size:16px">어디서 만날까?<small style="font-size: 17px; color: #ee6e9f;">*</small></label>
        <input type="text" v-model="articleData.address" :placeholder="address" style="font-family: 'Recipekorea';" readonly />
      </div>
      <div class="title">
        <label for="title" style="font-size:16px">제목<small style="font-size: 17px; color: #ee6e9f;">*</small></label>
        <div class="btn-group">
          <button type="button" class="btn btn-outline-secondary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">{{ selectedTBG }}</button>
          <div class="dropdown-menu">
            <a class="dropdown-item" @click="selectCategory(1)">쉘위배민</a>
            <a class="dropdown-item" @click="selectCategory(2)">쉘위택배</a>
            <a class="dropdown-item" @click="selectCategory(3)">쉘위N빵</a>
          </div>
        </div>
        <input type="text" id="title" v-model="articleData.title" placeholder=" 제목을 입력하세요" />
      </div>
      <div class="price">
        <label for style="font-size:16px">시작금액<small style="font-size: 17px; color: #ee6e9f;">*</small>/전체금액<small style="font-size: 17px; color: #ee6e9f;">*</small></label>
        <input type="number" id="myPrice" v-model="articleData.myPrice" placeholder=" 시작금액" />
        <input type="number" id="minPrice" v-model="articleData.minPrice" placeholder=" 전체금액" />
      </div>
      <div class="endTime">
        <label for style="font-size:16px">종료일자<small style="font-size: 17px; color: #ee6e9f;">*</small>/종료시간<small style="font-size: 17px; color: #ee6e9f;">*</small></label>
        <input type="date" v-model="articleData.endDate" />
        <input type="time" id="form-endTime" v-model="articleData.endTime" />
      </div>
      <div class="imageInsert d-flex">
        <label style="font-size:16px">사진 업로드</label>
        <input type="file" id="file" name="file" ref="file" />
      </div>
      <div class="url">
        <label for="url" style="font-size:16px">URL</label>
        <input type="url" id="url" v-model="articleData.urlLink" placeholder=" url을 입력하세요" required />
      </div>
      <div class="url">
        <label for="url" style="font-size:16px">오픈 카카오톡 URL</label>
        <input type="url" id="url" v-model="articleData.openLink" placeholder=" url을 입력하세요" />
      </div>
      <div class="createContent">
        <label for="textarea-rows" style="font-size:16px">내용<small style="font-size: 17px; color: #ee6e9f;">*</small></label>
        <div class="article-text-area">
          <b-form-textarea id="textarea-rows" v-model="articleData.description" placeholder="내용을 입력하세요..." rows="3" max-rows="6" ></b-form-textarea>
        </div>
      </div>
    </div>
  </div>
  <div class="tags">
    <label>태그</label>
    <b-form-tags v-model="articleData.tags" no-outer-focus class="tag-input-box mb-2">
      <template
        v-slot="{
          tags,
          inputAttrs,
          inputHandlers,
          tagVariant,
          addTag,
          removeTag
        }">
        <b-input-group class="mb-2">
          <b-form-input
            v-bind="inputAttrs"
            v-on="inputHandlers"
            placeholder="엔터를 누르면 태그가 추가돼요!"
            id="tag-input"
            class="form-control tag-submit-input"
          ></b-form-input>
          <b-input-group-append>
            <b-button @click="addTag()" class="tag-submit">등록</b-button>
          </b-input-group-append>
        </b-input-group>
        <div class="mt-2" style="font-size: 20px;">
          <b-form-tag
            v-for="tag in tags"
            @remove="removeTag(tag)"
            :key="tag"
            :title="tag"
            :variant="tagVariant"
            class="tag-btns mr-2">
            <div class="tag-name mt-1">{{ tag }}</div>
          </b-form-tag>
        </div>
      </template>
    </b-form-tags>
  </div>

  <div class="create-submit">
    <button class="temp-form" @click="articleCreate(0)">임시저장</button>
    <button class="complete-form" @click="articleCreate(1)"><i class="fas fa-check"></i> 완료</button>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import { mapActions } from "vuex";
import kakaoMap from "@/components/articles/kakaoMap";

export default {
  name: "CreateView",
  components: {
    kakaoMap,
  },
  data() {
    return {
      articleData: {
        categoryId: null,
        title: null,
        address: null,
        description: null,
        minPrice: null,
        myPrice: null,
        urlLink: null,
        openLink: null,
        endDate: null,
        endTime: null,
        image: "",
        tags: [],
        token: this.$cookies.get("auth-token"),
      },
      imageUrl: null,
      selectedTBG: "카테고리",
      coNum: "1",
      address: "<-  지도에서 만남의 장소를 설정하세요",
      file: "",
      path: "",
      uid: "",
    };
  },
  methods: {
    ...mapActions(["createArticle", "tempSaveArticle"]),
    articleCreate(num) {
      this.fileUpload();
      setTimeout(() => {
        var articleData = this.articleData;
        this.createArticle({ articleData, temp: num });
      }, 300);
    },
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
    selectCategory(num) {
      this.articleData.categoryId = num;
      if (num === 1) {
        this.selectedTBG = "쉘위배민";
      } else if (num === 2) {
        this.selectedTBG = "쉘위택배";
      } else {
        this.selectedTBG = "쉘위N빵";
      }
    },
    imageUpload() {
      this.$refs.imageInput.click();
    },
    setAddress(address) {
      this.articleData.address = address;
    },
  },
  watch: {
    articleData: {
      deep: true,
      handler() {
        try {
          if (this.articleData.minPrice < 0) {
            Swal.fire({
              icon: "warning",
              height: 300,
              width: 350,
              title:
                '<a style="font-size:20px; font-family: Recipekorea; color:black">1이상의 정수만 입력이 가능합니다.</a>',
              confirmButtonText:
                '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
              confirmButtonColor: "#ee6e9f",
            });
            this.articleData.minPrice = null;
          }
          if (this.articleData.myPrice < 0) {
            Swal.fire({
              icon: "warning",
              height: 300,
              width: 350,
              title:
                '<a style="font-size:20px; font-family: Recipekorea; color:black">1이상의 정수만 입력이 가능합니다.</a>',
              confirmButtonText:
                '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
              confirmButtonColor: "#ee6e9f",
            });
            this.articleData.myPrice = null;
          }
        } catch {}
      },
    },
  },
};
</script>

<style>
.create-alert-msg {
  display: flex;
  justify-content: flex-end;
  padding-right: 10px;
}
.create-container {
  padding-left: 0;
}
.create-form {
  display: flex;
  flex-direction: row;
}
.right-items {
  text-align: left;
  margin: 0 0 0 1.5vw;
  width: 55vw;
  border-radius: 4px;
}
.right-items > div {
  margin: 0 0 2vh 0;
}
.right-items > div > label {
  margin-right: 1vw;
}
.selected-place > input,
.imageInsert > input,
.url > input {
  width: 21vw;
}
.price > input {
  width: 8vw;
}
.title > input {
  width: 14.9vw;
  margin-left: 0.5vw;
}
.title > .btn-group > button {
  height: 5.3vh;
  text-align: center;
}
.price > input,
.endTime > input {
  width: 10.25vw;
}
#minPrice,
#form-endTime {
  margin-left: 0.5vw;
}
.tag {
  display: flex;
}
.tag > .tags {
  width: 41vw;
}
.createContent > textarea {
  vertical-align: top;
}
.tags {
  text-align: left;
  display: flex;
  align-items: center;
  margin: 3% 0;
  padding: 0 15vw;
}
.hihi {
  width: 35vw;
}
.complete-form,
.temp-form {
  border: none;
  outline: none;
  margin: 0 0.5vw;
  padding: 10px;
  border-radius: 4px;
  opacity: 0.85;
  transition: 0.4s;
  background-color: rgb(151, 151, 151);
}

._temp-form {
  border: none;
  outline: none;
  margin: 0 0.5vw;
  padding: 7px;
  border-radius: 4px;
  opacity: 0.85;
  transition: 0.4s;
  background-color: rgb(151, 151, 151);
}
.complete-form {
  background-color: #ee6e9f;
}
.temp-form:hover,
.complete-form:hover {
  opacity: 1;
}
input:focus::placeholder {
  color: transparent;
}
.tag-submit {
  border: none;
  outline: none;
  border-radius: 4px;
  background-color: #ee6e9f;
  opacity: 0.85;
  transition: 0.3s;
  margin: 1.5px 0 0 5px;
}
.tag-submit:hover {
  opacity: 1;
  background-color: #ee6e9f;
}
#tag-input {
  outline: none;
}
#tag-input:focus {
  outline: none;
  outline-style: none;
  -webkit-tap-highlight-color: transparent;
}
.tag-btns {
  border: 3px solid #ee6e9f;
  border-radius: 20px;
  background-color: transparent;
  color: black;
  margin-bottom: 1.5%;
  outline: none;
  padding: 7px 9px 5px 9px;
}
.tag-submit-input {
  border-radius: 4px;
}
.tag-submit-input:focus::placeholder {
  border: none;
  outline: none;
}
@media screen and (max-width: 991px) {
  .create-form {
    display: flex;
    flex-direction: column;
  }
  .create-alert-msg {
    padding-right: 8vw;
  }
  .selected-place {
    width: 80vw;
    display: flex;
  }
  .selected-place > input,
  .imageInsert > input,
  .url > input {
    width: 60.5vw;
  }
  .title > input {
    width: 51vw;
  }
  .price > input,
  .endTime > input {
    width: 30vw;
  }
  .right-items {
    width: 87vw;
    margin: 5vh 0 0 0;
  }
  .createContent > .article-text-area {
    width: 77.3vw;
  }
  .tags {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
