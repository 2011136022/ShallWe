<template>
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

    <div class="container">
      <div class="row">
        <div class="col-3">
          <div
            class="list-group flex-column"
            id="v-pills-tab"
            role="tablist"
            aria-orientation="vertical"
          >
            <a
              class="list-group-item list-group-item-action change-main-color"
              id="v-pills-1-tab"
              data-toggle="pill"
              href="#v-pills-1"
              role="tab"
              aria-controls="v-pills-1"
              aria-selected="true"
              >내가 쓴 게시물</a
            >
            <a
              class="list-group-item list-group-item-action change-main-color"
              id="v-pills-2-tab"
              data-toggle="pill"
              href="#v-pills-2"
              role="tab"
              aria-controls="v-pills-2"
              aria-selected="true"
              >참여한 게시물</a
            >
            <a
              class="list-group-item list-group-item-action change-main-color"
              id="v-pills-3-tab"
              data-toggle="pill"
              href="#v-pills-3"
              role="tab"
              aria-controls="v-pills-3"
              aria-selected="false"
              >거래 완료 게시물</a
            >
            <a
              class="list-group-item list-group-item-action change-main-color"
              id="v-pills-4-tab"
              data-toggle="pill"
              href="#v-pills-4"
              role="tab"
              aria-controls="v-pills-4"
              aria-selected="false"
              >내가 쓴 후기</a
            >
            <a
              class="list-group-item list-group-item-action change-main-color"
              id="v-pills-5-tab"
              data-toggle="pill"
              href="#v-pills-5"
              role="tab"
              aria-controls="v-pills-5"
              aria-selected="false"
              >내가 쓴 커뮤니티</a
            >
            <a
              class="list-group-item list-group-item-action change-main-color"
              id="v-pills-6-tab"
              data-toggle="pill"
              href="#v-pills-6"
              role="tab"
              aria-controls="v-pills-6"
              aria-selected="false"
              >찜 목록</a
            >
            <a
              class="list-group-item list-group-item-action change-main-color"
              id="v-pills-7-tab"
              data-toggle="pill"
              href="#v-pills-7"
              role="tab"
              aria-controls="v-pills-7"
              aria-selected="false"
              >임시 저장</a
            >
          </div>
        </div>
        <div class="col-9">
          <div class="tab-content" id="v-pills-tabContent">
            <div
              class="tab-pane fade show active text-dark"
              id="v-pills-1"
              role="tabpanel"
              aria-labelledby="v-pills-1-tab"
            >
              <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">제목</th>
                    <th scope="col">내용</th>
                    <th scope="col">최소 금액 / 모인 금액</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody
                  v-for="alist in userData.articleList"
                  :key="alist.createTime"
                >
                  <tr>
                    <td>{{ alist.title }}</td>
                    <td>{{ alist.description }}</td>
                    <td>{{ alist.sumPrice }}원 / {{ alist.minPrice }}원</td>
                    <td>
                      <router-link
                        class="btn btn-sm"
                        :to="{
                          name: 'articleDetail',
                          params: { ID: `${alist.articleId}` },
                        }"
                      >
                        <button class="see-detail">
                          <span>보러가기</span
                          ><svg
                            class="arrow-effect"
                            width="13px"
                            height="10px"
                            viewBox="0 0 13 10"
                          >
                            <path d="M1,5 L11,5"></path>
                            <polyline points="8 1 12 5 8 9"></polyline>
                          </svg></button
                      ></router-link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div
              class="tab-pane fade text-dark"
              id="v-pills-2"
              role="tabpanel"
              aria-labelledby="v-pills-2-tab"
            >
              <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">제목</th>
                    <th scope="col">내용</th>
                    <th scope="col">최소 금액 / 모인 금액</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody
                  v-for="jlist in userData.joinList"
                  :key="jlist.createTime"
                >
                  <tr>
                    <td>{{ jlist.title }}</td>
                    <td>{{ jlist.description }}</td>
                    <td>{{ jlist.sumPrice }}원 / {{ jlist.minPrice }}원</td>
                    <td>
                      <router-link
                        class="btn btn-sm"
                        :to="{
                          name: 'articleDetail',
                          params: { ID: `${jlist.articleId}` },
                        }"
                        ><button class="see-detail">
                          <span>보러가기</span
                          ><svg
                            class="arrow-effect"
                            width="13px"
                            height="10px"
                            viewBox="0 0 13 10"
                          >
                            <path d="M1,5 L11,5"></path>
                            <polyline points="8 1 12 5 8 9"></polyline>
                          </svg></button
                      ></router-link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div
              class="tab-pane fade text-dark"
              id="v-pills-3"
              role="tabpanel"
              aria-labelledby="v-pills-3-tab"
            >
              <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">제목</th>
                    <th scope="col">내용</th>
                    <th scope="col">최소 금액 / 모인 금액</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody
                  v-for="clist in userData.completeList"
                  :key="clist.createTime"
                >
                  <tr>
                    <td>{{ clist.title }}</td>
                    <td>{{ clist.description }}</td>
                    <td>{{ clist.sumPrice }}원 / {{ clist.minPrice }}원</td>
                    <td>
                      <router-link
                        class="btn btn-sm"
                        :to="{
                          name: 'articleDetail',
                          params: { ID: `${clist.articleId}` },
                        }"
                        ><button class="see-detail">
                          <span>보러가기</span
                          ><svg
                            class="arrow-effect"
                            width="13px"
                            height="10px"
                            viewBox="0 0 13 10"
                          >
                            <path d="M1,5 L11,5"></path>
                            <polyline points="8 1 12 5 8 9"></polyline>
                          </svg></button
                      ></router-link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div
              class="tab-pane fade text-dark"
              id="v-pills-4"
              role="tabpanel"
              aria-labelledby="v-pills-4-tab"
            >
              <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">제목</th>
                    <th scope="col">내용</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody
                  v-for="rlist in userData.reviewList"
                  :key="rlist.createTime"
                >
                  <tr>
                    <td>{{ rlist.title }}</td>
                    <td>{{ rlist.description }}</td>
                    <td>
                      <router-link
                        class="btn btn-sm"
                        :to="{
                          name: 'articleDetail',
                          params: { ID: `${rlist.articleId}` },
                        }"
                        ><button class="see-detail">
                          <span>보러가기</span
                          ><svg
                            class="arrow-effect"
                            width="13px"
                            height="10px"
                            viewBox="0 0 13 10"
                          >
                            <path d="M1,5 L11,5"></path>
                            <polyline points="8 1 12 5 8 9"></polyline>
                          </svg></button
                      ></router-link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div
              class="tab-pane fade text-dark"
              id="v-pills-5"
              role="tabpanel"
              aria-labelledby="v-pills-5-tab"
            >
              <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">제목</th>
                    <th scope="col">내용</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody
                  v-for="flist in userData.reviewList"
                  :key="flist.createTime"
                >
                  <tr>
                    <td>{{ flist.title }}</td>
                    <td>{{ flist.description }}</td>
                    <td>
                      <router-link
                        class="btn btn-sm"
                        :to="{
                          name: 'articleDetail',
                          params: { ID: `${flist.articleId}` },
                        }"
                        ><button class="see-detail">
                          <span>보러가기</span
                          ><svg
                            class="arrow-effect"
                            width="13px"
                            height="10px"
                            viewBox="0 0 13 10"
                          >
                            <path d="M1,5 L11,5"></path>
                            <polyline points="8 1 12 5 8 9"></polyline>
                          </svg></button
                      ></router-link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div
              class="tab-pane fade text-dark"
              id="v-pills-6"
              role="tabpanel"
              aria-labelledby="v-pills-6-tab"
            >
              <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">제목</th>
                    <th scope="col">내용</th>
                    <th scope="col">최소 금액 / 모인 금액</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody
                  v-for="llist in userData.likeList"
                  :key="llist.createTime"
                >
                  <tr>
                    <td>{{ llist.title }}</td>
                    <td>{{ llist.description }}</td>
                    <td>{{ llist.sumPrice }}원 / {{ llist.minPrice }}원</td>
                    <td>
                      <router-link
                        class="btn btn-sm"
                        :to="{
                          name: 'articleDetail',
                          params: { ID: `${llist.articleId}` },
                        }"
                        ><button class="see-detail">
                          <span>보러가기</span
                          ><svg
                            class="arrow-effect"
                            width="13px"
                            height="10px"
                            viewBox="0 0 13 10"
                          >
                            <path d="M1,5 L11,5"></path>
                            <polyline points="8 1 12 5 8 9"></polyline>
                          </svg></button
                      ></router-link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div
              class="tab-pane fade text-dark"
              id="v-pills-7"
              role="tabpanel"
              aria-labelledby="v-pills-7-tab"
            >
              <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">제목</th>
                    <th scope="col">내용</th>
                    <th scope="col">최소 금액 / 모인 금액</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody
                  v-for="tlist in userData.tempList"
                  :key="tlist.createTime"
                >
                  <tr>
                    <td>{{ tlist.title }}</td>
                    <td>{{ tlist.description }}</td>
                    <td>{{ tlist.sumPrice }}원 / {{ tlist.minPrice }}원</td>
                    <td>
                      <router-link
                        class="btn btn-sm"
                        :to="{
                          name: 'articleDetail',
                          params: { ID: `${tlist.articleId}` },
                        }"
                        ><button class="see-detail">
                          <span>보러가기</span
                          ><svg
                            class="arrow-effect"
                            width="13px"
                            height="10px"
                            viewBox="0 0 13 10"
                          >
                            <path d="M1,5 L11,5"></path>
                            <polyline points="8 1 12 5 8 9"></polyline>
                          </svg></button
                      ></router-link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <hr />
    <div class="mb-5">
      <router-link
        class=" mr-1"
        v-bind:to="{ name: constants.URL_TYPE.USER.EDIT }"
        ><button class="complete-form">회원수정</button></router-link
      >
      <button class="temp-form ml-1" @click="deleteUser">회원탈퇴</button>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL
import constants from "../../lib/constants";
import { mapState, mapActions } from "vuex";

export default {
  name: "Profile",
  data: function() {
    return {
      constants,
      date: "",
    };
  },
  methods: {
    ...mapActions(["deleteUser", "getArticles", "deleteArticle"]),
  },
  computed: {
    ...mapState(["userData", "articles"]),
  },
};
</script>

<style>
.change-main-color:focus {
  background-color: #ee6e9f;
  border: 0.5px solid #ee6e9f;
}
.see-detail {
  transition: all 0.2s ease;
  background-color: transparent;
  border: none;
  outline: none;
}
.profile-block {
  height: 50px;
  background-color: lightgrey;
}
.intro {
  height: 100px;
  border: solid black;
}
.mypage-mypage {
  height: 300px;
}
#v-pills-1 {
  border: solid lightgray 1px;
  border-radius: 1%;
  height: 500px;
}
#v-pills-2 {
  border: solid lightgray 1px;
  border-radius: 1%;
  height: 500px;
}
#v-pills-3 {
  border: solid lightgray 1px;
  border-radius: 1%;
  height: 500px;
}
#v-pills-4 {
  border: solid lightgray 1px;
  border-radius: 1%;
  height: 500px;
}
#v-pills-5 {
  border: solid lightgray 1px;
  border-radius: 1%;
  height: 500px;
}
#v-pills-6 {
  border: solid lightgray 1px;
  border-radius: 1%;
  height: 500px;
}
#v-pills-7 {
  border: solid lightgray 1px;
  border-radius: 1%;
  height: 500px;
}
.profile-photo {
  height: 100px;
  width: 100px;
}
.mypage1 {
  height: 300px;
}
.mypage2 {
  height: 300px;
}
.arrow-effect {
  position: relative;
  top: 0;
  margin-left: 10px;
  fill: none;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke: black;
  stroke-width: 2;
  transform: translateX(-5px);
  transition: all 0.3s ease;
}
.see-detail:hover .arrow-effect {
  transform: translateX(0);
}
.see-detail:active {
  transform: scale(0.96);
}
</style>
