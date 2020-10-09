<template>
  <div
    class="mt-5 create-container"
    style="font-family: 'Recipekorea', cursive; font-size:16.5px"
  >
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
          <label style="font-size:16px"
            >어디서 만날까?<small style="font-size: 17px; color: #ee6e9f;"
              >*</small
            ></label
          >
          <input
            type="text"
            v-model="articleData.address"
            :placeholder="articleData.address"
            style="font-family: Recipekorea;"
            readonly
          />
        </div>
        <div class="title">
          <label for="title" style="font-size:16px"
            >제목<small style="font-size: 17px; color: #ee6e9f;"
              >*</small
            ></label
          >
          <div class="btn-group">
            <button
              type="button"
              class="btn btn-outline-secondary btn-sm dropdown-toggle"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              {{ selectedTBG }}
            </button>
            <div class="dropdown-menu">
              <a class="dropdown-item" @click="selectCategory(1)">쉘위배달</a>
              <a class="dropdown-item" @click="selectCategory(2)">쉘위택배</a>
              <a class="dropdown-item" @click="selectCategory(3)">쉘위공구</a>
            </div>
          </div>
          <input
            type="text"
            id="title"
            v-model="articleData.title"
            placeholder=" 제목을 입력하세요"
          />
        </div>
        <div class="price">
          <label style="font-size:16px"
            >시작금액 <small style="font-size: 17px; color: #ee6e9f;">*</small>/
            전체금액<small style="font-size: 17px; color: #ee6e9f;"
              >*</small
            ></label
          >
          <input
            type="number"
            id="myPrice"
            v-model="articleData.myPrice"
            placeholder=" 시작금액"
          />
          <input
            type="number"
            id="minPrice"
            v-model="articleData.minPrice"
            placeholder=" 전체금액"
          />
        </div>
        <div class="endTime">
          <label style="font-size:16px"
            >종료일자 <small style="font-size: 17px; color: #ee6e9f;">*</small>/
            종료시간<small style="font-size: 17px; color: #ee6e9f;"
              >*</small
            ></label
          >
          <input type="date" v-model="articleData.endDate" />
          <input type="time" id="form-endTime" v-model="articleData.endTime" />
        </div>
        <div class="imageInsert d-flex">
          <label style="font-size:16px">사진 업로드</label>
          <input type="file" id="file" name="file" ref="file" />
        </div>
        <div class="url">
          <label for="url" style="font-size:16px">URL</label>
          <input
            type="url"
            id="url"
            v-model="articleData.urlLink"
            placeholder=" url을 입력하세요"
            required
          />
        </div>
        <div class="url">
          <label for="url" style="font-size:16px">오픈 카카오톡 URL</label>
          <input
            type="url"
            id="url"
            v-model="articleData.openLink"
            placeholder=" url을 입력하세요"
          />
        </div>
        <div class="createContent">
          <label for="textarea-rows" style="font-size:16px"
            >내용
            <small style="font-size: 17px; color: #ee6e9f;">*</small>
          </label>
          <div class="article-text-area">
            <b-form-textarea
              id="textarea-rows"
              v-model="articleData.description"
              placeholder="내용을 입력하세요..."
              rows="3"
              max-rows="6"
            ></b-form-textarea>
          </div>
        </div>
      </div>
    </div>
    <div class="tags">
      <label for>태그</label>
      <b-form-tags
        v-model="articleData.tags"
        no-outer-focus
        class="tag-input-box mb-2"
      >
        <template
          v-slot="{
            tags,
            inputAttrs,
            inputHandlers,
            tagVariant,
            addTag,
            removeTag,
          }"
        >
          <b-input-group class="mb-2">
            <b-form-input
              v-bind="inputAttrs"
              v-on="inputHandlers"
              placeholder="엔터를 누르면 태그가 추가돼요!"
              id="tag-input"
              class="form-control tag-submit-input"
            ></b-form-input>
            <b-input-group-append>
              <div>
                <b-button @click="addTag()" class="tag-submit">등록</b-button>
              </div>
            </b-input-group-append>
          </b-input-group>
          <div class="mt-2" style="font-size: 20px;">
            <b-form-tag
              v-for="tag in tags"
              @remove="removeTag(tag)"
              :key="tag"
              :title="tag"
              :variant="tagVariant"
              class="tag-btns mr-2"
              ><div class="tag-name mt-1">{{ tag }}</div></b-form-tag
            >
          </div>
        </template>
      </b-form-tags>
    </div>

    <div class="create-submit">
      <button class="complete-form" @click="articleUpdate">
        <i class="fas fa-check"></i> 완료
      </button>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import { mapActions, mapState } from "vuex";
import kakaoMap from "@/components/articles/kakaoMap";

export default {
  name: "articleUpdate",
  components: {
    kakaoMap,
  },
  data() {
    return {
      articleUpdateData: {},
      selectedTBG: "카테고리",
      coNum: "1",
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
          this.articleData.image = this.path;
          var tmp = this.articleData.image.split(".");
          tmp[1] = tmp[1].toLowerCase();
          this.articleData.image = tmp[0] + "." + tmp[1];
        })
        .catch((error) => {
          console.log(error);
        });
    },
    articleUpdate() {
      this.fileUpload();
      setTimeout(() => {
        var articleUpdateData = this.articleData;
        this.updateArticle({ articleUpdateData, temp: 1 });
      }, 300);
    },
    selectCategory(num) {
      this.articleUpdateData.categoryId = num;
      if (num === 1) {
        this.selectedTBG = "쉘위배달";
      } else if (num === 2) {
        this.selectedTBG = "쉘위택배";
      } else {
        this.selectedTBG = "쉘위공구";
      }
    },
    imageUpload() {
      this.$refs.imageInput.click();
    },
    setAddress(address) {
      this.articleData.address = address;
    },
  },
  created: function() {
    this.getArticle(this.$route.params.ID);
    this.articleUpdateData = this.articleData;
    if (this.articleUpdateData.categoryId === 1) {
      this.selectedTBG = "쉘위배달";
    } else if (this.articleUpdateData.categoryId === 2) {
      this.selectedTBG = "쉘위택배";
    } else {
      this.selectedTBG = "쉘위공구";
    }
    this.articleData.endDate = "";
    this.articleData.endTime = "";
  },
  computed: {
    ...mapState(["articleData"]),
  },
};
</script>
<style></style>
