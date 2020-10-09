<template>
  <div>
    <div class="login-input">
      <form>
        <h4 class="mb-4 our-main-font">관리자 로그인</h4>
        <div class="form-group mb-1">
          <div class="container">
            <input
              class="login-email"
              v-model="loginData.adminId"
              id="adminEmail"
              style="font-family: FontAwesome;"
              :placeholder="id"
              type="text"
            />
          </div>
        </div>
        <div class="form-group">
          <div class="container">
            <input
              class="login-pw"
              v-model="loginData.password"
              type="password"
              id="adminPassword"
              style="font-family: FontAwesome;"
              :placeholder="pw"
              @keypress.enter="adminLogin(loginData)"
            />
          </div>
        </div>
      </form>
      <div class="login-sumit">
        <div class="container mb-3">
          <button
            type="submit"
            class="submit-btn"
            @click="adminLogin(loginData)"
            data-dismiss="modal"
          >로그인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
export default {
  data() {
    return {
      id: " \uf0e0" + "  ID",
      pw: " \uf084" + "  비밀번호",
      loginData: {
        adminId: "",
        password: "",
      },
    }
  },
  methods:{
    ...mapActions(['adminLogin'])
  }
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
.modal {
  text-align: center;
}
@media screen and (min-width: 768px) {
  .modal:before {
    display: inline-block;
    vertical-align: middle;
    content: " ";
    height: 100%;
  }
}
.modal-dialog {
  display: inline-block;
  text-align: left;
  vertical-align: middle;
}
.modal-dialog.modalsize {
  width: 27%;
  height: 85%;
  margin: 0;
  padding: 0;
}
.modal-content.modalsize {
  height: auto;
  min-width: 10%;
}
.close-btn {
  border: none;
  outline: none;
  background-color: transparent;
  width: 100%;
}
.close {
  float: right;
}
.login-input {
  margin: 7% 0 5% 0;
}
.login-email:focus::placeholder {
  color: transparent;
}
.login-pw:focus::placeholder {
  color: transparent;
}
.login-email,
.login-pw {
  width: 35%;
  border-radius: 2px;
}
.login-email {
  margin-bottom: 0.5%;
}
.modal-body .login-submit {
  margin: 0 0 10% 0;
}
.submit-btn,
.kakao-btn,
.google-btn {
  border-radius: 4px;
  border: none;
  font-weight: bold;
  height: 35px;
  text-align: center;
  vertical-align: middle;
}
.submit-btn {
  background-color: #ee6e9f;
  color: white;
  margin: 0 0 0;
  width: 35%;
  opacity: 0.8;
}
.submit-btn:hover {
  transition-duration: 0.5s;
  opacity: 1;
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
  background-color: #3a4049;
}
.searchInput {
  height: 35px;
  width: 180px;
  border-radius: 4px 0px 0px 4px;
  border-style: solid;
  border-width: 1.5px;
  border-color: #3a4049;
  padding-left: 0.6%;
}
.searchInput:focus {
  outline: none;
}
.searchInput:focus::placeholder {
  color: transparent;
}
.searchBtn {
  height: 35px;
  border-style: solid;
  border-radius: 0px 4px 4px 0px;
  border-color: #3a4049;
  background-color: #3a4049;
  color: #faf9fa;
}
.loginBtn:hover {
  cursor: pointer;
}
.goJoin {
  font-weight: bold;
}
</style>
