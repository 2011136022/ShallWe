<template>
  <div class="mt-5">
  <div class="mt-5" style="font-family: 'Recipekorea', cursive;">
    <div class="mb-4 container">
      <div class="row">
        <div class="col-6">
          <b-card no-body class="overflow-hidden">
            <b-row no-gutters>
              <b-col>
                <h5 class="mt-5">"{{ userData.nickname }}" 님의 프로필</h5>
                <hr style="width: 70%;" />
                <h6 class="mt-0 mb-4">{{ userData.email }}</h6>
                <h6 class="mt-0">닉네임: {{ userData.nickname }}</h6>
                <h6 class="mt-0">지역: {{ userData.address }}</h6>
              </b-col>
              <b-col style="border-left: solid 1px lightgray">
                <b-card-body>
                  <b-card-text>
                    <div class="text-left">
                      <h6 class="text-center mt-5">자기소개</h6>
                      <div
                        class="mb-5"
                        style="border: solid 1px lightgray; height: 100px;"
                      >{{userData.introduce}}</div>
                    </div>
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
          </b-card>
        </div>
        <div class="col-6">
          <b-card no-body class="overflow-hidden h-100">
            <b-row no-gutters>
              <b-col class="d-flex justify-content-center mt-5 mb-5">
                <!-- 등급 이미지 -->
                <img
                  v-if="userData.grade === 0"
                  class="mt-1 mb-4"
                  style="width: 140px;"
                  src="http://i3b203.p.ssafy.io/localImg/Level_0.png"
                  alt=""
                />
                <img
                  v-if="userData.grade === 1"
                  class="mt-1 mb-4"
                  style="width: 140px;"
                  src="http://i3b203.p.ssafy.io/localImg/Level_1.png"
                  alt=""
                />
                <img
                  v-if="userData.grade === 2"
                  class="mt-1 mb-4"
                  style="width: 140px;"
                  src="http://i3b203.p.ssafy.io/localImg/Level_2.png"
                  alt=""
                />
                <img
                  v-if="userData.grade === 3"
                  class="mt-1 mb-4"
                  style="width: 140px;"
                  src="http://i3b203.p.ssafy.io/localImg//Level_3.png"
                  alt=""
                />
                <img
                  v-if="userData.grade === 4"
                  class="mt-1 mb-4"
                  style="width: 140px;"
                  src="http://i3b203.p.ssafy.io/localImg/Level_4.png"
                  alt=""
                />
                <img
                  v-if="userData.grade === 5"
                  class="mt-1 mb-4"
                  style="width: 140px;"
                  src="http://i3b203.p.ssafy.io/localImg/Level_5.png"
                  alt=""
                />

                <div class="ml-5 mt-4" style="border-left: solid 1px lightgray">
                  <h6 class="mb-5 mt-3 ml-5">
                    등급: Level {{ userData.grade }}
                  </h6>
                  <h6 class="ml-5">
                    포인트: {{ userData.userPoint
                    }}<small class="ml-1">Point</small>
                  </h6>
                </div>
              </b-col>
            </b-row>
          </b-card>
        </div>
      </div>
    </div>
    <hr>
    <button v-if="isAdmin" class="btn btn-danger" @click="changeUserStatus()">회원삭제</button>
  </div>
</template>

<script>
  import { mapState, mapActions } from "vuex";
  import axios from "axios";
  import cookies from "vue-cookies";

  const BACK_URL = process.env.VUE_APP_BACK_URL;

  export default {
    name: 'userDetail',
    computed: {
      ...mapState([ "userData","isAdmin" ]),
    },
    methods: {
      ...mapActions([ "getUserDatailData" ]),
      changeUserStatus() {
        this.userData.status = 0
        this.sendChangedUserStatus();
      },
      sendChangedUserStatus() {
        this.userData.token = this.$cookies.get('admin-token')
        axios
          .post(`${BACK_URL}/accuse/disabled`, this.userData)
          .then(() => {
          })
          .catch(( err ) => {
            console.error(err);
          });
      },
    },
    created() {
      this.getUserDatailData(this.$route.params.ID);
    }
  }

</script>

<style>

</style>