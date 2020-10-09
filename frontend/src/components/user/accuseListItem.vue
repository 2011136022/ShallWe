<template>
  <div>
    <b-media tag="li" class="text-left">

      <div class="d-flex justify-content-between">
        <small v-if="accuse.accuseIndex === 1">
          <span class="badge badge-pill badge-dark mr-2 our-main-font">게시물 신고</span>
          <span v-if="accuse.accuseKind == 1" class="our-main-font">욕설, 비난을 했어요!</span>
          <span v-if="accuse.accuseKind == 2" class="our-main-font">약속 장소에 나오지 않았어요!</span>
          <span v-if="accuse.accuseKind == 3" class="our-main-font">광고를 하고 있어요!</span>
        </small>
        <small v-if="accuse.accuseIndex === 2">
          <span class="badge badge-pill badge-light mr-2 our-main-font" style="background-color: lightgray;">댓글 신고</span>
          <span v-if="accuse.accuseKind == 1" class="our-main-font">욕설, 비난을 했어요!</span>
          <span v-if="accuse.accuseKind == 2" class="our-main-font">약속 장소에 나오지 않았어요!</span>
          <span v-if="accuse.accuseKind == 3" class="our-main-font">광고를 하고 있어요!</span>
        </small>
        <div>
          <div class="article-drop dropdown dropleft">
            <button type="button" class="article-btn" data-toggle="dropdown">
              <i class="fas fa-ellipsis-v"></i>
            </button>
            <div class="dropdown-menu">
              <a :href="accuse.accuseUrl" class="dropdown-item our-main-font">신고상세</a>
              <a @click="decideAccuse(1)" class="dropdown-item our-main-font">신고접수</a>
              <a @click="decideAccuse(2)" class="dropdown-item our-main-font">허위신고</a>
            </div>
          </div>
        </div>
      </div>
      <h5 class="mt-0 mb-2 mt-1 our-main-font"># {{ accuse.accuseId }}. "{{ accuse.reporter }}" 님의 신고 내용</h5>
      <h6 class="our-main-font">피신고자: {{ accuse.defendant }}</h6>
      <p class="mb-0 our-main-font">{{ accuse.accuseReason }}</p>
    </b-media>
    <hr>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import cookies from "vue-cookies";

export default {
  name: "accuseListItem",
  props: {
    accuse: Object,
  },
  data() {
    return {};
  },
  methods: {
    decideAccuse(num) {
      this.accuse.accuseConfirm=num
      this.accuse.token = this.$cookies.get("admin-token");
      axios
        .post(`${BACK_URL}/accuse/applyto`, this.accuse)
        .then(() => {
          this.$emit('get')
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
};
</script>

<style>
</style>