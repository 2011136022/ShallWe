<template>
  <div>
    <nav
      class="navbar navbar-light d-flex justify-content-end navbar1 p-0 my-navbar"
      style="font-family: 'Recipekorea', cursive;"
    >
      <ul class="navbar-nav d-flex flex-row">
        <li class="nav-item mr-1">
          <a
            href="https://kauth.kakao.com/oauth/authorize?client_id=5a3a01519efdeba53b7a039bffafd62d&redirect_uri=http://i3b203.p.ssafy.io:8080/account/kakaoLogin&response_type=code"
            class="nav-link navbar1-item loginBtn"
            v-if="!isLoggedin"
            @click="REMOVE_TOKEN"
            style="font-size: 13.5px"
          >카카오 로그인</a>
        </li>
        <li class="nav-item mr-1">
          <a
            href="#"
            v-if="isLoggedin"
            @click="REMOVE_TOKEN"
            class="nav-link navbar1-item"
            style="font-size: 13.5px"
          >로그아웃</a>
        </li>

        <div class="wall" v-show="isAdmin||isLoggedin">|</div>

        <li class="nav-item mr-1">
          <router-link
            v-if="isLoggedin&&!isAdmin"
            v-bind:to="{ name: constants.URL_TYPE.USER.PROFILE }"
            class="nav-link navbar1-item"
            style="font-size: 13.5px"
          >마이페이지</router-link>
        </li>
      </ul>
      <div v-if="isAdmin" class="d-flex align-items-starts mr-1">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a href="/user/userlist" class="nav-link navbar1-item" style="font-size: 13.5px">유저관리</a>
          </li>
        </ul>
      </div>

      <div class="wall" v-show="isAdmin">|</div>

      <div v-if="isAdmin" class="d-flex align-items-starts">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a href="/user/accuselist" class="nav-link navbar1-item" style="font-size: 13.5px">신고관리</a>
          </li>
        </ul>
      </div>
    </nav>
    <nav class="navbar navbar-light d-flex justify-content-center my-navbar p-1">
      <a
        class="navbar-brand mt-3"
        href="/"
        style="color: #ee6e9f; font-family: 'Lobster', cursive; 
      font-size: 35px"
      >Shall we?</a>
    </nav>
    <nav class="navbar navbar-expand-lg navbar-light p-0 mt-4" style="font-size: 18px">
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item pb-0">
            <router-link to="/introduce" class="nav-link mainMenu pb-0" id="aboutUs">쉘위 소개</router-link>
          </li>

          <li class="nav-item pb-0">
            <router-link to="/article" class="nav-link mainMenu pb-0" id="article">게시글</router-link>
          </li>

          <li class="nav-item pb-0">
            <router-link to="/reviews" class="nav-link mainMenu pb-0" id="community">후기</router-link>
          </li>

          <li class="nav-item pb-0">
            <router-link to="/posts" class="nav-link mainMenu pb-0" id="community">커뮤니티</router-link>
          </li>

          <li class="nav-item pb-0">
            <router-link to="/localList" class="nav-link mainMenu pb-0" id="community">우리 동네</router-link>
          </li>
        </ul>
        <input
          class="searchInput"
          type="search"
          style="font-family: Recipekorea; padding-top: 9px;"
          placeholder="검색"
          v-model="searchData.searchDataForSend.word"
          @keypress.enter="search(searchData)"
        />
        <button class="searchBtn my-2 my-sm-0" type="submit" @click="search(searchData)">
          <i class="fas fa-search"></i>
        </button>
      </div>
    </nav>
  </div>
</template>

<script>
import constants from "../../lib/constants";
import { mapState, mapMutations, mapActions } from "vuex";
import router from "@/router";
import axios from "axios";
const BACK_URL = process.env.VUE_APP_BACK_URL;
const API_KEY = process.env.VUE_APP_KAKAO_API_KEY;

export default {
  name: "Header",
  components: {},
  props: ["isHeader"],
  data: function () {
    return {
      email: " \uf0e0" + "  이메일",
      pw: " \uf084" + "  비밀번호",
      constants,
      searchData: {
        searchDataForSend: {
          word: "",
          subject: "headertitle",
        },
      },
      loginData: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    ...mapMutations(["REMOVE_TOKEN", "loginCheck","adminCheck"]),
    ...mapActions(["search", "login", "getUserData"]),
  },
  computed: {
    ...mapState(["isLoggedin", "userData", "isAdmin"]),
  },
  created: function () {
    this.loginCheck();
    if(this.$cookies.get("admin-token")){
      this.adminCheck()
    }
    if (this.$cookies.get("auth-token")) {
      this.getUserData();
    }
  },
};
</script>

<style>
@font-face {
  font-family: "Recipekorea";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/Recipekorea.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
.row-items {
  margin: 5% 0 3% 0;
  width: 100%;
  height: 40%;
}
.or {
  border: 0px solid grey;
  margin: 2px 1.5% 0 1.5%;
  font-size: small;
  color: rgb(153, 151, 151);
}
.bottom-line {
  margin: 7px 0 8px 0;
  width: 44%;
  border-bottom: 1px solid rgb(216, 211, 211);
}
.kakao-btn,
.google-btn {
  margin: 2% 0 0 0;
  width: 100%;
  background-color: transparent;
  color: rgb(26, 12, 12);
}
.kakao-img {
  height: 23px;
  width: 24px;
}
.google-img {
  height: 24px;
  width: 23px;
}
.blank {
  width: 10px;
}
.mainMenu {
  font-family: Recipekorea;
  font-weight: normal;
  font-style: normal;
}
#article {
  margin-left: 10px;
  color: #3a4049;
}
#community {
  margin-left: 10px;
  color: #3a4049;
}
#aboutUs {
  margin-left: -5px;
  color: #3a4049;
}
.navbar1 {
  height: 30px;
}
.navbar1-item {
  font-size: 12px;
}
.wall {
  padding: 0.5rem 0.2rem;
  font-size: 12px;
  margin-left: -4px;
  margin-right: 1px;
}
.down {
  padding-bottom: 3px;
  margin-bottom: 5px;
}
.downBtn {
  height: 35px;
  vertical-align: middle;
  background-color: #676d74;
}
.downBtn:hover {
  background-color: #206ada;
}
.searchInput {
  height: 35px;
  width: 180px;
  border-radius: 40px 0px 0px 40px;
  border-style: solid;
  border-width: 1.5px;
  border-color: #000;
  padding-left: 1.3%;
  padding-bottom: 0.6%;
}
.searchInput:focus {
  outline: none;
}
.searchInput:focus::placeholder {
  color: transparent;
}
.searchInput::placeholder {
  font-family: "Recipekorea";
  font-size: 15px;
}
.searchBtn {
  height: 35px;
  border: none;
  border-radius: 0px 40px 40px 0px;
  border-color: #3a4049;
  background-color: #3a4049;
  color: #faf9fa;
  padding: 0 12px 0 8px;
}
.loginBtn:hover {
  cursor: pointer;
}
.goJoin {
  font-weight: bold;
}
</style>
