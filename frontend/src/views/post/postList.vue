<template>
  <div class="article-list-box mt-5 mx-auto">
    <nav class="mt-4 mb-3 mx-1">
      <div
        class="nav nav-tabs d-flex"
        id="nav-tab"
        role="tablist"
        style="font-family: 'Recipekorea', cursive; font-size:18px"
      >
        <a
          @click="changeCategory(100)"
          class="nav-item nav-link active text-dark"
          id="nav-home-tab"
          data-toggle="tab"
          href="#nav-home"
          role="tab"
          aria-controls="nav-home"
          aria-selected="true"
          >전체</a
        >
        <a
          @click="changeCategory(101)"
          class="nav-item nav-link text-dark"
          id="nav-contact-tab"
          data-toggle="tab"
          href="#nav-contact"
          role="tab"
          aria-controls="nav-contact"
          aria-selected="false"
          >공지</a
        >
        <a
          @click="changeCategory(103)"
          class="nav-item nav-link text-dark"
          id="nav-contact-tab"
          data-toggle="tab"
          href="#nav-contact"
          role="tab"
          aria-controls="nav-contact"
          aria-selected="false"
          >자유</a
        >
        <router-link :to="{ name: 'postCreate' }" class="ml-auto">
          <button class="post-write-btn" style="font-size: 16px">글쓰기</button>
        </router-link>
      </div>
    </nav>
    <div
      class="tab-content"
      id="nav-tabContent"
      style="font-family: 'Recipekorea', cursive; font-size:16.5px"
    >
      <div
        v-if="categoryNum === 100"
        class="tab-pane fade show active"
        id="nav-home"
        role="tabpanel"
        aria-labelledby="nav-home-tab"
      >
        <table class="table">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody style="font-size:17px">
            <tr
              v-for="article in articles"
              :key="article.articleId"
              :class="{ 'post-tr': article.categoryId === 101 }"
            >
              <th scope="row" v-if="article.categoryId === 101">
                <span class="notification">공지</span>
              </th>
              <th scope="row" v-else-if="article.categoryId === 103">
                <span class="post-free">자유</span>
              </th>
              <td style="font-size:17px">
                <router-link
                  style="text-decoration:none; color: black"
                  :class="{ 'post-title': article.categoryId === 101 }"
                  :to="{
                    name: 'postDetail',
                    params: { ID: `${article.articleId}` },
                  }"
                  >{{ article.title }}</router-link
                >
              </td>
              <td style="font-size:16px">
                <router-link
                  style="text-decoration:none;"
                  :to="{
                    name: 'postDetail',
                    params: { ID: `${article.articleId}` },
                  }"
                  >{{ article.writer }}</router-link
                >
              </td>
              <td style="font-size:14.5px; color: rgb(165, 165, 165);">
                {{ cutDate(article.createTime) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div
        v-if="categoryNum === 101"
        class="tab-pane fade show active"
        id="nav-home"
        role="tabpanel"
        aria-labelledby="nav-home-tab"
      >
        <table class="table">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody style="font-size:17px">
            <tr v-for="article in articles" :key="article.articleId">
              <th scope="row" v-if="article.categoryId === 101">
                <span class="notification">공지</span>
              </th>

              <th scope="row" v-else-if="article.categoryId === 103">
                <span class="post-free">자유</span>
              </th>
              <td style="font-size:17px">
                <router-link
                  style="text-decoration:none;"
                  :class="{ 'post-title': article.categoryId === 101 }"
                  :to="{
                    name: 'postDetail',
                    params: { ID: `${article.articleId}` },
                  }"
                  >{{ article.title }}</router-link
                >
              </td>
              <td style="font-size:16px">
                <router-link
                  style="text-decoration:none;"
                  :to="{
                    name: 'postDetail',
                    params: { ID: `${article.articleId}` },
                  }"
                  >{{ article.writer }}</router-link
                >
              </td>
              <td style="font-size:14.5px; color: rgb(165, 165, 165);">
                {{ cutDate(article.createTime) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div
        v-if="categoryNum === 103"
        class="tab-pane fade show active"
        id="nav-home"
        role="tabpanel"
        aria-labelledby="nav-home-tab"
      >
        <table class="table">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="article in articles" :key="article.articleId">
              <th scope="row" v-if="article.categoryId === 101">
                <span class="notification">공지</span>
              </th>

              <th scope="row" v-else-if="article.categoryId === 103">
                <span class="post-free">자유</span>
              </th>
              <td style="font-size:17px">
                <router-link
                  style="text-decoration:none;"
                  :class="{ 'post-title': article.categoryId === 101 }"
                  :to="{
                    name: 'postDetail',
                    params: { ID: `${article.articleId}` },
                  }"
                  >{{ article.title }}</router-link
                >
              </td>
              <td style="font-size:16px">
                <router-link
                  style="text-decoration:none;"
                  :to="{
                    name: 'postDetail',
                    params: { ID: `${article.articleId}` },
                  }"
                  >{{ article.writer }}</router-link
                >
              </td>
              <td style="font-size:14.5px; color: rgb(165, 165, 165);">
                {{ cutDate(article.createTime) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <infinite-loading
      @infinite="infiniteHandler"
      :identifier="infiniteId"
      spinner="waveDots"
    >
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px; font-family: 'Recipekorea', cursive; font-size:14.5px"
      >
        더이상 게시물이 존재하지 않습니다!
      </div>
    </infinite-loading>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import { mapState, mapActions } from "vuex";
import InfiniteLoading from "vue-infinite-loading";
import cookies from "vue-cookies";
import axios from "axios";

export default {
  name: "postList",
  data() {
    return {
      categoryNum: 100,
      page: 0,
      onlyOne: true,
      articles: [],
      infiniteId: +new Date(),
    };
  },
  components: {
    InfiniteLoading,
  },
  methods: {
    ...mapActions(["getArticles"]),
    infiniteHandler($state) {
      const auth = { token: cookies.get("auth-token") };
      axios
        .post(`${BACK_URL}/post/read/2/${this.categoryNum}/${this.page}`, auth)
        .then((res) => {
          setTimeout(() => {
            if (res.data.postList.length) {
              this.articles = this.articles.concat(res.data.postList);
              this.page += 1;
              $state.loaded();
            } else {
              $state.complete();
            }
          }, 1000);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    changeCategory(num) {
      this.categoryNum = num;
      this.page = 0;
      this.articles = [];
      this.onlyOne = true;
      this.infiniteId += 1;
    },
    cutDate(date) {
      let CD = date + "";
      const year = CD.substring(0, 4) + ".";
      const month = CD.substring(5, 7) + ".";
      const day = CD.substring(8, 10) + ".";
      const res = year + month + day;
      return res;
    },
  },
};
</script>

<style>
.notification {
  border: 1px solid #da4e83;
  border-radius: 4px;
  padding: 5px 5px;
  font-size: 85%;
  color: #da4e83;
}
.post-tr {
  background-color: rgb(165, 165, 165);
  background-color: rgb(255, 250, 253);
}
.post-free {
  border: 1px solid #9c9a9b;
  border-radius: 4px;
  padding: 5px 5px;
  font-size: 85%;
  color: #a09c9d;
}
.post-write-btn {
  border: none;
  outline: none;
  border-radius: 4px;
  background-color: #ee6e9f;
  padding: 7px 8px 3px 8px;
  color: white;
}
.post-id {
  font-size: 85%;
  opacity: 0.9;
}
.post-title {
  text-decoration: none;
  color: #e25c8f;
}
.post-title:hover {
  text-decoration: none;
}
</style>
