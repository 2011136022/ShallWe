<template>
  <div>
    <div>
      <hr />
      <div>
        <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333;"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <router-link to="/introduce" class="ml-0" id="introduce">
            <b-carousel-slide>
              <template v-slot:img>
                <img
                  class="d-block img-fluid w-100"
                  width="1024"
                  height="480"
                  src="http://i3b203.p.ssafy.io/localImg/c2.png"
                  alt="image slot"
                />
              </template>
            </b-carousel-slide>
          </router-link>

          <router-link to="/article" class="ml-0" id="article">
            <b-carousel-slide background="white">
              <template v-slot:img>
                <img
                  class="d-block img-fluid w-100"
                  width="1024"
                  height="480"
                  src="http://i3b203.p.ssafy.io/localImg/캐러셀1.png"
                  alt="image slot"
                />
              </template>
            </b-carousel-slide>
          </router-link>

          <router-link to="/article" class="ml-0" id="article">
            <b-carousel-slide background="white">
              <template v-slot:img>
                <img
                  class="d-block img-fluid w-100"
                  width="1024"
                  height="480"
                  src="http://i3b203.p.ssafy.io/localImg/캐러셀2.png"
                  alt="image slot"
                />
              </template>
            </b-carousel-slide>
          </router-link>

          <router-link to="/article" class="ml-0" id="article">
            <b-carousel-slide background="white">
              <template v-slot:img>
                <img
                  class="d-block img-fluid w-100"
                  width="1024"
                  height="480"
                  src="http://i3b203.p.ssafy.io/localImg/캐러셀3.png"
                  alt="image slot"
                />
              </template>
            </b-carousel-slide>
          </router-link>
        </b-carousel>
      </div>
      <hr />
      <div class="division-box homeMenu d-flex align-items-center justify-content-center">
        <h2 class="mt-0" style="font-family: 'Recipekorea', cursive; color: #ee6e9f;">실시간 게시물</h2>
      </div>
      <hr class="division-box-underline-1" />

      <b-container class="bv-example-row">
        <b-row align-h="start">
          <b-col cols="12" sm="6" lg="4" v-for="article in recentList" :key="article.keyVal">
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
      <hr />
      <div class="division-box homeMenu d-flex align-items-center justify-content-center">
        <h2 style="font-family: 'Recipekorea', cursive; color: #ee6e9f;">마감 임박 게시물</h2>
      </div>
      <hr class="division-box-underline-2" />

      <b-container class="bv-example-row">
        <b-row align-h="start">
          <b-col cols="12" sm="6" lg="4" v-for="article in deadLineList" :key="article.keyVal">
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
      <hr />
      <div class="division-box homeMenu d-flex align-items-center justify-content-center">
        <h2 style="font-family: 'Recipekorea', cursive; color: #ee6e9f;">베스트 후기</h2>
      </div>
      <hr class="division-box-underline-3" />

      <b-container>
        <b-row>
          <b-col cols="12" sm="4" v-for="review in bestReview" :key="review.keyVal">
            <b-card class="text-center mt-3">
              <b-card-text>
                <i class="fas fa-quote-left"></i>
                {{ review.description }}
                <i class="fas fa-quote-right"></i>
              </b-card-text>
            </b-card>
          </b-col>
        </b-row>
      </b-container>
      <hr />
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;

import axios from "axios";

export default {
  name: "Home",
  data() {
    return {
      slide: 0,
      sliding: null,
      recentList: [],
      deadLineList: [],
      bestReview: [],
    };
  },
  computed: {
    imageUrl() {
      return (article) => {
        try {
          return "http://i3b203.p.ssafy.io/img/" + `${article.image}`;
        } catch {}
      };
    },
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    getMainArticle() {
      const auth = { token: this.$cookies.get("auth-token") };
      axios
        .post(`${BACK_URL}/main/post`, auth)
        .then((response) => {
          for (let i = 0; i < 6; i++) {
            response.data.recentList.newVal = i;
          }
          for (let i = 6; i < 12; i++) {
            response.data.deadLineList.newVal = i;
          }
          for (let i = 12; i < 18; i++) {
            response.data.bestReviewList.newVal = i;
          }
          this.recentList = response.data.recentList;
          this.deadLineList = response.data.deadLineList;
          this.bestReview = response.data.bestReviewList;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created() {
    this.getMainArticle();
  },
};
</script>

<style scoped>
.carouselBtn {
  border: none;
  outline: none;
  border-radius: 4px;
  background-color: #ee6e9f;
  padding: 5px 10px;
  color: white;
  font-weight: bold;
}
.carousel-img {
  display: flex;
  width: 100%;
}
.carousel-inner {
  height: 100%;
  width: 50%;
}
.caption {
  position: absolute;
  bottom: 8px;
  right: 100px;
  font-size: 18px;
}
.division-box {
  height: 10%;
  margin-top: 4%;
}
.division-box-small {
  height: 30px;
}
.division-box-underline-1 {
  margin-top: 0.4%;
  margin-bottom: 0;
  width: 13%;
  height: 1px;
  background-color: #ee6e9f;
}
.division-box-underline-2 {
  margin-top: 0.4%;
  margin-bottom: 0;
  width: 15%;
  height: 1px;
  background-color: #ee6e9f;
}
.division-box-underline-3 {
  margin-top: 0.4%;
  margin-bottom: 0;
  width: 10%;
  height: 1px;
  background-color: #ee6e9f;
}
@font-face {
  font-family: "BMJUA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
.homeMenu {
  font-family: BMJUA;
  font-weight: normal;
  font-style: normal;
  font-size: 1.2em;
}
</style>

<style scoped lang="scss">
$color-bg: #f8f8f8;
$card-padding: 20px;
$grid-gutter: 31px;
$small: 480px;
$medium: 768px;
$large: 992px;
$x-large: 1200px;

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
  transition: transform 0.3s;

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
.navs {
  display: flex;
  justify-content: space-between;
}
.main-tabs {
  display: flex;
}
.write-btn {
  border: none;
  outline: none;
  border-radius: 4px;
  background-color: #ee6e9f;
  padding: 5px 10px;
  color: white;
  font-weight: bold;
}
</style>