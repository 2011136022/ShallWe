import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import cookies from "vue-cookies";
import router from "../router";

const BACK_URL = process.env.VUE_APP_BACK_URL;

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authToken: cookies.get("auth-token"),
    adminToken: cookies.get("admin-token"),
    isLoggedin: false,
    isTerm: false,
    isChecked: false,
    isAdmin: false,
    userData: {
      name: "",
      address: "",
      email: "",
      nickname: "",
      userId: "",
      password: "",
      birthday: "",
      userPoint: "",
      articleCount: "",
      reviewCount: "",
      likeCount: "",
      tempCount: "",
      articleList: [],
      reviewList: [],
      likeList: [],
      tempList: [],
      joinList: [],
      completeList: [],
      grade: "",
    },
    articleData: {
      articleId: "",
      userId: "",
      categoryId: "",
      title: "",
      writer: "",
      address: "",
      description: "",
      minPrice: "",
      sumPrice: "",
      urlLink: "",
      image: "",
      temp: "",
      endTime: "",
      createTime: "",
      likeNum: "",
      commentNum: "",
      timeAgo: "",
      partList: [],
      tags: [],
    },
    articles: [],
    comments: [],
    users: [],
    accuseData: {
      reporter: "",
      defendant: "",
      accuseIndex: "",
      accuseValue: "",
      accuseKind: 0,
      accuseReason: "",
      accuseUrl: "",
      accuseConfirm: 0,
    },
    accuses: [],
  },

  getters: {},

  mutations: {
    SET_TOKEN(state, token) {
      router.push("/");
      state.authToken = token;
      cookies.set("auth-token", token, 0);
      state.isLoggedin = true;
      Swal.fire({
        icon: "success",
        height: 300,
        width: 280,
        title:
          '<a style="font-size:20px; font-family: Recipekorea; color:black">로그인 되었습니다!</a>',
        confirmButtonText:
          '<a style="font-size:20px; font-family: Recipekorea; color:black; border: none; outline: none;">확인</a>',
        confirmButtonColor: "#ee6e9f",
      });
    },
    adminCheck(state) {
      state.adminToken = cookies.get("admin-token");
      state.isAdmin = true;
    },
    SET_ADMIN_TOKEN(state, token) {
      state.adminToken = token;
      state.isAdmin = true;
      cookies.set("admin-token", token, 0);
    },
    REMOVE_TOKEN(state) {
      if (state.adminToken) {
        cookies.remove("admin-token");
        Swal.fire({
          icon: "success",
          height: 300,
          width: 290,
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">로그아웃 되었습니다.</a>',
          confirmButtonText:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: "#ee6e9f",
        });
        state.adminToken = null;
      }
      state.authToken = null;
      cookies.remove("auth-token");
      state.isLoggedin = false;
      state.isAdmin = false;
      state.modal = true;
      router.push("/");
    },
    loginCheck(state) {
      if (!!state.authToken) {
        state.isLoggedin = true;
      } else {
        state.isLoggedin = false;
      }
    },
    termCheck(state) {
      if (state.isTerm) {
        state.isTerm = false;
      } else {
        state.isTerm = true;
      }
    },
    duCheck(state, bool) {
      state.isChecked = bool;
    },
    GET_USERDATA(state, userData) {
      state.userData = userData;
    },
    GET_ARTICLES(state, articles) {
      state.articles = articles;
    },
    GET_ARTICLE(state, response) {
      state.articleData = response.data;
    },
    GET_COMMENTS(state, comments) {
      state.comments = comments;
    },
    GET_USERS(state, users) {
      state.users = users;
    },
    GET_USER(state, res) {
      state.userData = res.data;
    },
    GET_ACCUSES(state, accuses) {
      state.accuses = accuses;
    },
  },

  actions: {
    duCheck({ commit }, nickname) {
      axios
        .get(`${BACK_URL}/account/nicknamecheck/${nickname}`)
        .then((response) => {
          if (response.data === "사용가능한 닉네임입니다.") {
            commit("duCheck", true);
          } else {
            commit("duCheck", false);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    signUp({ state, commit }, signUpData) {
      if (signUpData.address && signUpData.nickname) {
        if (state.isChecked) {
          if (state.isTerm) {
            axios
              .post(`${BACK_URL}/account/signup`, signUpData)
              .then((response) => {
                Swal.fire({
                  height: 300,
                  width: 350,
                  icon: "success",
                  title:
                    '<a style="font-size:20px; font-family: Recipekorea; color:black">Shall We 에 가입되신걸 축하합니다.</a>',
                  confirmButtonText:
                    '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
                  confirmButtonColor: "#ee6e9f",
                });
                commit("SET_TOKEN", response.data);
                this.commit("termCheck");
                router.push("/");
              })
              .catch((err) => {
                console.log(err);
              });
          } else {
            swal.fire({
              height: 300,
              width: 300,
              icon: "warning",
              title:
                '<a style="font-size:20px; font-family: Recipekorea; color:black">약관에 동의해 주세요.</a>',
              confirmButtonText:
                '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
              confirmButtonColor: "#ee6e9f",
            });
          }
        } else {
          swal.fire({
            height: 300,
            width: 300,
            icon: "warning",
            title:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">닉네임을 입력해 주세요.</a>',
            confirmButtonText:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
            confirmButtonColor: "#ee6e9f",
          });
        }
      } else {
        swal.fire({
          height: 300,
          width: 300,
          icon: "warning",
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">빈 칸을 모두 채워주세요.</a>',
          confirmButtonText:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: "#ee6e9f",
        });
      }
    },
    adminLogin({ commit }, loginData) {
      axios
        .post(`${BACK_URL}/admin/login`, loginData)
        .then((response) => {
          commit("SET_ADMIN_TOKEN", response.data.adminToken);
          commit("SET_TOKEN", response.data.token);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getUserData({ state, commit }) {
      const auth = { token: state.authToken };
      axios
        .get(`${BACK_URL}/account/read`, auth)
        .then((response) => {
          commit("GET_USERDATA", response.data);
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getUserDatailData({ state, commit }, userId) {
      const auth = { token: state.authToken };
      axios
        .get(`${BACK_URL}/account/read/${userId}`, auth)
        .then((res) => {
          commit("GET_USER", res);
        })
        .catch((err) => {
          console.error(err);
        });
    },
    editUser({ state, commit }, editData) {
      editData.token = state.authToken;
      axios
        .put(`${BACK_URL}/account/update`, editData)
        .then(() => {
          Swal.fire({
            icon: "success",
            height: 300,
            width: 350,
            title:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">수정이 완료되었습니다!</a>',
            text: "다시 로그인 해주세요!",
            confirmButtonText:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
            confirmButtonColor: "#ee6e9f",
          });
          commit("REMOVE_TOKEN");
        })
        .catch((err) => {
          console.error(err);
        });
    },
    deleteUser({ state, commit }) {
      const auth = { token: state.authToken };
      axios
        .delete(`${BACK_URL}/account/delete`, auth)
        .then(() => {
          commit("REMOVE_TOKEN");
          Swal.fire({
            icon: "success",
            height: 300,
            width: 350,
            title:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">회원탈퇴가 완료되었습니다.</a>',
            confirmButtonText:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
            confirmButtonColor: "#ee6e9f",
          });
          router.push("/");
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getArticles({ state, commit }, data) {
      const auth = { token: state.authToken };
      axios
        .post(`${BACK_URL}/post/read/${data.temp}/${data.categoryId}`, auth)
        .then((response) => {
          commit("GET_ARTICLES", response.data.postList);
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getArticle({ state, commit }, articleID) {
      const auth = { token: state.authToken };
      axios
        .post(`${BACK_URL}/post/detail/${articleID}`, auth)
        .then((response) => {
          commit("GET_ARTICLE", response);
          commit("GET_COMMENTS", response.data.commentList);
        })
        .catch((err) => {
          console.error(err);
        });
    },
    createArticle(context, articleData) {
      if (
        articleData.temp === 1 &&
        (!articleData.articleData.categoryId ||
          !articleData.articleData.title ||
          !articleData.articleData.address ||
          !articleData.articleData.description ||
          !articleData.articleData.minPrice ||
          !articleData.articleData.myPrice ||
          !articleData.articleData.endDate ||
          !articleData.articleData.endTime)
      ) {
        Swal.fire({
          icon: "warning",
          height: 300,
          width: 400,
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">필수 입력칸을 모두 채워주세요</a>',
          confirmButtonText:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: "#ee6e9f",
        });
      } else {
        if (articleData.temp === 1 || articleData.temp === 0) {
          if (articleData.articleData.endTime.length < 8) {
            articleData.articleData.endTime =
              articleData.articleData.endTime + ":00";
          }
        }
        if (!articleData.articleData.image) {
          articleData.articleData.image = "default.jpg";
        }
        if (cookies.get("admin-token")) {
          articleData.articleData.categoryId = 101;
        }
        axios
          .post(
            `${BACK_URL}/post/create/${articleData.temp}`,
            articleData.articleData
          )
          .then(() => {
            if (articleData.temp === 1 || articleData.temp === 0) {
              router.push("/article");
            } else {
              if (articleData.articleData.categoryId === 102) {
                router.push("/reviews");
              } else {
                router.push("/posts");
              }
            }
          })
          .catch((err) => console.log(err));
      }
    },
    updateArticle({ state }, updateData) {
      if (
        updateData.temp === 1 &&
        (!updateData.articleUpdateData.categoryId ||
          !updateData.articleUpdateData.title ||
          !updateData.articleUpdateData.address ||
          !updateData.articleUpdateData.description ||
          !updateData.articleUpdateData.minPrice ||
          !updateData.articleUpdateData.myPrice ||
          !updateData.articleUpdateData.endDate ||
          !updateData.articleUpdateData.endTime)
      ) {
        Swal.fire({
          icon: "warning",
          height: 300,
          width: 400,
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">필수 입력칸을 모두 채워주세요</a>',
          confirmButtonText:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: "#ee6e9f",
        });
      } else {
        if (updateData.temp == 1) {
          if (updateData.articleUpdateData.endTime) {
            if (updateData.articleUpdateData.endTime.length < 8) {
              updateData.articleUpdateData.endTime =
                updateData.articleUpdateData.endTime + ":00";
            }
          }
        }
        if (!updateData.articleUpdateData.image) {
          updateData.articleUpdateData.image = "default.jpg";
        }
        updateData.articleUpdateData.token = state.authToken;
        axios
          .put(
            `${BACK_URL}/post/update/${updateData.temp}`,
            updateData.articleUpdateData
          )
          .then(() => {
            if (updateData.temp === 2) {
              if (updateData.articleUpdateData.categoryId === 102) {
                router.push(`/reviews`);
              } else {
                router.push(
                  `/pdetail/${updateData.articleUpdateData.articleId}`
                );
              }
            } else {
              router.push(`/detail/${updateData.articleUpdateData.articleId}`);
            }
          })
          .catch((err) => {
            console.error(err);
          });
      }
    },
    deleteArticle({ state, dispatch }, data) {
      const auth = { token: state.authToken };
      axios
        .delete(`${BACK_URL}/post/delete/${data.articleId}`)
        .then(() => {
          if (data.categoryId < 10) {
            router.push(`/article`);
          } else {
            router.push(`/posts`);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    search({ commit }, searchData) {
      if (!searchData.searchDataForSend.word) {
        alert("검색어를 입력해 주세요");
      } else {
        cookies.set("searchData", searchData, 0);
        searchData.categoryId = 0;
        cookies.set('searchData',searchData, 0)
        axios
          .post(
            `${BACK_URL}/post/search/1/${searchData.categoryId}`,
            searchData.searchDataForSend
          )
          .then((res) => {
            commit("GET_ARTICLES", res.data.postList);
            cookies.set("searchKeyword", searchData.searchDataForSend.word, 0);
            router.push({ name: "searchList" });
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    detailSearch({ commit }, searchData) {
      if (searchData.categoryId === 0) {
        searchData.categoryId = "temp";
      }
      if (
        searchData.searchDataForSend.word &&
        searchData.searchDataForSend.subject &&
        searchData.categoryId &&
        searchData.temp
        ) {
          if (searchData.categoryId === "temp") {
            searchData.categoryId = 0;
          }
        cookies.set("searchData", searchData, 0);
        axios
          .post(
            `${BACK_URL}/post/search/${searchData.temp}/${searchData.categoryId}`,
            searchData.searchDataForSend
          )
          .then((res) => {
            commit("GET_ARTICLES", res.data.postList);
            router.push("/searchlist");
          })
          .catch((err) => {
            console.log(err);
          });
      } else if (!searchData.temp) {
        Swal.fire({
          icon: "warning",
          height: 300,
          width: 350,
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">게시글 종류를 선택해 주세요!</a>',
          confirmButtonText:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: "#ee6e9f",
        });
      } else if (!searchData.categoryId) {
        Swal.fire({
          icon: "warning",
          height: 300,
          width: 350,
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">카테고리를 선택해 주세요!</a>',
          confirmButtonText:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: "#ee6e9f",
        });
      } else if (!searchData.searchDataForSend.subject) {
        Swal.fire({
          icon: "warning",
          height: 300,
          width: 350,
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">분류를 선택해 주세요!</a>',
          confirmButtonText:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: "#ee6e9f",
        });
      } else if (!searchData.searchDataForSend.word) {
        Swal.fire({
          icon: "warning",
          height: 300,
          width: 350,
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">검색어를 입력해 주세요!</a>',
          confirmButtonText:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: "#ee6e9f",
        });
      }
    },
    getUsers({ state, commit }, users) {
      const auth = { token: state.authToken };
      axios
        .post(`${BACK_URL}/account/readAll`, auth)
        .then((res) => {
          commit("GET_USERS", res.data);
          console.log(res)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateGrade({ state }, updateGrade) {
      changedObj.changedGrade.token = state.authToken;
      axios
        .post(`${BACK_URL}/`, changedObj.changedGrade)
        .then(() => {
          router.push("/user/admin");
        })
        .catch((err) => {
          console.error(err);
        });
    },
    createArticleAccuse(context, accuseArticleData) {
      if(accuseArticleData.accuseArticleData.accuseKind&&accuseArticleData.accuseArticleData.accuseReason){
        axios
          .post(`${BACK_URL}/accuse/create`, accuseArticleData.accuseArticleData)
          .then(() => {
            router.go()
            Swal.fire({
              icon: 'warning',
              height: 300,
              width: 350,
              title: '<a style="font-size:20px; font-family: Recipekorea; color:black">신고가 접수되었습니다!</a>',
              confirmButtonText :'<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
              confirmButtonColor: '#ee6e9f'
            })
          })
          .catch((err) => console.log(err));
      }else{
        Swal.fire({
          icon: 'warning',
          height: 300,
          width: 350,
          title: '<a style="font-size:20px; font-family: Recipekorea; color:black">신고 종류와 사유를 모두 입력해주세요!</a>',
          confirmButtonText :'<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
          confirmButtonColor: '#ee6e9f'
        })
      }
    },
    createCommentAccuse(context, accuseCommentData) {
      console.log(accuseCommentData.accuseCommentData);
      axios
        .post(`${BACK_URL}/accuse/create`, accuseCommentData.accuseCommentData)
        .then(() => {
          Swal.fire({
            icon: 'warning',
            height: 300,
            width: 350,
            title: '<a style="font-size:20px; font-family: Recipekorea; color:black">신고가 접수되었습니다!</a>',
            confirmButtonText :'<a style="font-size:20px; font-family: Recipekorea; color:black">확인</a>',
            confirmButtonColor: '#ee6e9f'
          })
          router.go()
        })
        .catch((err) => console.log(err));
    },
    getAccuses({ state, commit }) {
      const admin = { token: state.adminToken };
      axios
        .get(`${BACK_URL}/accuse/read`, admin)
        .then((res) => {
          commit("GET_ACCUSES", res.data.accuseList);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    decideAccuse({ state }, decisionData) {
      const admin = { token: state.adminToken };
      axios
        .put(`${BACK_URL}/accuse/applyto`, decisionData)
        .then(() => {
          router.push("/user/accuselist");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  modules: {},
});
