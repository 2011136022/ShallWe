<template>
  <div class="mt-5" style="font-family: 'Recipekorea', cursive; font-size:17.5px">
    <div class="dropdown mr-1">
      <div class="mainSearch">
        <a
          :class="{
            beforeSelect: searchData.temp != 1,
            colorChange: searchData.temp == 1,
          }"
          href="#"
          @click="selectTemp(1)"
        >게시글</a>
        <a
          :class="{
            beforeSelect: searchData.temp != 2,
            colorChange: searchData.temp == 2,
          }"
          href="#"
          @click="selectTemp(2)"
        >커뮤니티</a>
      </div>
      <div v-if="searchData.temp !== 0" class="select-item-row mt-2">
        <div v-if="searchData.temp === 1">
          <a
            :class="{
              beforeSelect: searchData.categoryId != 1,
              colorChange: searchData.categoryId == 1,
            }"
            href="#"
            @click="selectCategory(1)"
          >쉘위배민</a>
          <a
            :class="{
              beforeSelect: searchData.categoryId != 2,
              colorChange: searchData.categoryId == 2,
            }"
            href="#"
            @click="selectCategory(2)"
          >쉘위택배</a>
          <a
            :class="{
              beforeSelect: searchData.categoryId != 3,
              colorChange: searchData.categoryId == 3,
            }"
            href="#"
            @click="selectCategory(3)"
          >쉘위N빵</a>
        </div>
        <div v-if="searchData.temp === 2">
          <a
            :class="{
              beforeSelect: searchData.categoryId != 101,
              colorChange: searchData.categoryId == 101,
            }"
            href="#"
            @click="selectCategory(101)"
          >공지</a>
          <a
            :class="{
              beforeSelect: searchData.categoryId != 102,
              colorChange: searchData.categoryId == 102,
            }"
            href="#"
            @click="selectCategory(102)"
          >후기</a>
          <a
            :class="{
              beforeSelect: searchData.categoryId != 103,
              colorChange: searchData.categoryId == 103,
            }"
            href="#"
            @click="selectCategory(103)"
          >자유</a>
        </div>
        <div>|</div>
        <div v-if="searchData.temp !== 0" class="dropdown mr-1">
          <a
            :class="{
              beforeSelect: searchData.searchDataForSend.subject != 'title',
              colorChange: searchData.searchDataForSend.subject == 'title',
            }"
            href="#"
            @click="selectSubject(0)"
          >제목</a>
          <a
            :class="{
              beforeSelect: searchData.searchDataForSend.subject != 'writer',
              colorChange: searchData.searchDataForSend.subject == 'writer',
            }"
            href="#"
            @click="selectSubject(1)"
          >작성자</a>
          <a
            v-if="searchData.temp === 1"
            :class="{
              beforeSelect: searchData.searchDataForSend.subject != 'address',
              colorChange: searchData.searchDataForSend.subject == 'address',
            }"
            href="#"
            @click="selectSubject(2)"
          >주소</a>
        </div>
      </div>
    </div>

    <input
      class="detail-search mt-4 mb-4 ml-3"
      style="font-family: 'Recipekorea', cursive; font-size:15px"
      placeholder="Search"
      v-model="searchData.searchDataForSend.word"
      @keypress.enter="detailSearch(searchData)"
    />
    <button class="detail-search-btn" type="submit" @click="detailSearch(searchData)">
      <i class="fas fa-search"></i>
    </button>

    <b-container class="bv-example-row">
      <b-row align-h="start">
        <b-col cols="12" sm="4" v-for="article in articles" :key="article.articleId">
          <router-link
            :to="{
              name: 'articleDetail',
              params: { ID: `${article.articleId}` },
            }"
            class="text-decoration-none text-dark"
          >
            <b-card
              :no-body="true"
              class="article-card m-4 _card card__one"
              align="left"
              footer-bg-variant="#ee6e9f"
              footer-class="card-end"
            >
              <div
                v-if="article.status != 4 && article.status != 5"
                class="article-img-box m-0 p-0"
                :style="{
                      height: '100%',
                      width: '100%',
                      backgroundImage:
                        'url(' +
                        'http://i3b203.p.ssafy.io/img/' +
                          article.image +
                        ')',
                      backgroundSize: 'cover',
                    }"
              >
                <img
                  v-if="
                        article.categoryId == 1 &&
                          article.status != 4 &&
                          article.status != 5
                      "
                  src="http://i3b203.p.ssafy.io/localImg/type2.png"
                  class="article-type-img"
                  style="max-width: 100%; height: auto;"
                />
                <img
                  v-if="
                        article.categoryId == 2 &&
                          article.status != 4 &&
                          article.status != 5
                      "
                  src="http://i3b203.p.ssafy.io/localImg/type1.png"
                  class="article-type-img"
                  style="max-width: 100%; height: auto;"
                />
                <img
                  v-if="
                        article.categoryId == 3 &&
                          article.status != 4 &&
                          article.status != 5
                      "
                  src="http://i3b203.p.ssafy.io/localImg/type3.png"
                  class="article-type-img"
                  style="max-width: 100%; height: auto;"
                />
              </div>
              <div
                v-else-if="article.status == 4"
                class="article-img-box m-0 p-0"
                :style="{
                      height: '100%',
                      width: '100%',
                      backgroundImage:
                        'url(' + 'http://i3b203.p.ssafy.io/localImg/DealEnd2.png' + ')',
                      backgroundSize: 'cover',
                    }"
              ></div>
              <div
                v-else-if="article.status === 5"
                class="article-img-box m-0 p-0"
                :style="{
                      height: '100%',
                      width: '100%',
                      backgroundImage:
                        'url(' + 'http://i3b203.p.ssafy.io/localImg/TimeOver2.png' + ')',
                      backgroundSize: 'cover',
                    }"
              ></div>

              <b-card-text class="p-2">
                <h5
                  class="article-title mt-3 pt-1"
                  style="font-family: 'Recipekorea', cursive; font-size:18px"
                >{{ article.title }}</h5>
                <h6
                  class="article-address py-1"
                  style="font-family: 'Recipekorea', cursive; font-size:16px"
                >{{ article.address }}</h6>
                <br />
                <h6
                  class="article-price py-1"
                  style="font-family: 'Recipekorea', cursive; font-size:16px"
                >가격: {{ article.sumPrice }}원/{{ article.minPrice }}원</h6>
              </b-card-text>
              <template v-slot:footer>
                <div
                  class="d-flex justify-content-between"
                  style="font-family: 'Recipekorea', cursive; font-size:14.5px"
                >
                  <div class="articleList-subInfo">
                    <div class="articleList-heart">
                      <i class="far fa-heart" style="color: #ee6e9f; "></i>
                      {{ article.likeNum }}
                    </div>
                    <div class="articleList-chat ml-2">
                      <i class="far fa-comment-dots"></i>
                      {{ article.commentNum }}
                    </div>
                  </div>
                  <small class="text-muted">{{ article.timeAgo }}</small>
                </div>
              </template>
            </b-card>
          </router-link>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "searchList",
  data() {
    return {
      searchData: {
        searchDataForSend: {
          subject: "",
          word: "",
        },
        categoryId: "",
        temp: 0,
      },
      item1: "",
      item2: "",
      item3: "",
    };
  },
  methods: {
    ...mapActions(["detailSearch", "search"]),
    selectTemp(num) {
      if (num === 1) {
        this.searchData.temp = 1;
        this.item3 = "게시글";
      } else {
        this.searchData.temp = 2;
        this.item3 = "커뮤니티";
      }
      this.item1 = "";
      this.item2 = "";
    },
    selectSubject(num) {
      if (num == 0) {
        this.searchData.searchDataForSend.subject = "title";
        this.item1 = "제목";
      } else if (num == 1) {
        this.searchData.searchDataForSend.subject = "writer";
        this.item1 = "작성자";
      } else {
        this.searchData.searchDataForSend.subject = "address";
        this.item1 = "주소";
      }
    },
    selectCategory(num) {
      if (num == 1) {
        this.searchData.categoryId = 1;
        this.item2 = "쉘위배민";
      } else if (num == 2) {
        this.searchData.categoryId = 2;
        this.item2 = "쉘위택배";
      } else if (num == 3) {
        this.searchData.categoryId = 3;
        this.item2 = "쉘위N빵";
      } else if (num == 101) {
        this.searchData.categoryId = 101;
        this.item2 = "공지";
      } else if (num == 102) {
        this.searchData.categoryId = 102;
        this.item2 = "후기";
      } else if (num == 103) {
        this.searchData.categoryId = 103;
        this.item2 = "자유";
      }
    },
  },
  computed: {
    ...mapState(["articles"]),
  },
  created: function () {
    this.search(this.$cookies.get("searchData"));
  },
};
</script>

<style scoped lang="scss">
$color-bg: #f8f8f8;
$card-padding: 20px;
$grid-gutter: 31px;

$small: 480px;
$medium: 768px;
$large: 992px;
$x-large: 1200px;
.select-item-row {
  display: flex;
  justify-content: center;
}
.beforeSelect {
  text-decoration: none;
  margin: 0 5px;
}
.beforeSelect:hover {
  color: black;
  cursor: pointer;
}
.colorChange {
  color: #ee6e9f;
  text-decoration: none;
  font-weight: bold;
  margin: 0 5px;
}
._card {
  position: relative;
  flex: 1 1 100%;
  background: lighten($color-bg, 3%);

  @media screen and (min-width: $medium) {
    flex-basis: calc(33.33% - (#{$grid-gutter * 2} + #{$card-padding * 2}));
    margin: 0 $grid-gutter;
  }
}
.card__one {
  transition: transform 0.5s;

  &::after {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    transition: opacity 2s cubic-bezier(0.165, 0.84, 0.44, 1);
    box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2),
      0 6px 20px 0 rgba(0, 0, 0, 0.15);
    content: "";
    opacity: 0;
    z-index: -1;
  }

  &:hover,
  &:focus {
    transform: scale3d(1.006, 1.006, 1);

    &::after {
      opacity: 1;
    }
  }
}
.search-row {
  display: flex;
  justify-content: center;
}
.main-select {
  margin: 0 10px;
}
.second-select,
.third-select {
  margin: 0 5px;
}
.detail-search {
  border-top: 0px;
  border-left: 0px;
  border-right: 0px;
  border-radius: 0;
  width: 250px;
}
.detail-search:focus {
  border-top: 0px;
  border-left: 0px;
  border-right: 0px;
  border-radius: 0;
  outline: none;
}
.detail-search-btn {
  border: none;
  outline: none;
  background-color: transparent;
  color: #ee6e9f;
}
</style>
