<template>
  <div class="container review-list mt-5">
    <div class="review-list-top">
    </div>
    <reviewDetail
      v-for="review in reviews"
      :key="review.articleId"
      :review="review"
      @re-render="infiniteHandler"
    />
    <infinite-loading @infinite="infiniteHandler" :identifier="infiniteId" spinner="waveDots">
      <div
        slot="no-more"
        class="our-main-font"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;"
      >더이상 게시물이 존재하지 않습니다!</div>
    </infinite-loading>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import { mapState, mapActions } from "vuex";
import InfiniteLoading from "vue-infinite-loading";
import cookies from "vue-cookies";
import axios from "axios";
import reviewDetail from "./reviewDetail";

export default {
  name: "reviewList",
  data() {
    return {
      reviews: [],
      onlyOne: true,
      categoryNum: 102,
      page: 0,
      infiniteId: +new Date(),
    };
  },
  components: {
    InfiniteLoading,
    reviewDetail,
  },
  computed: {
    ...mapState(["userData"]),
  },
  methods: {
    ...mapActions(["search", "getUserData"]),
    infiniteHandler($state) {
      const auth = { token: cookies.get("auth-token") };
      axios
        .post(`${BACK_URL}/post/read/2/102/${this.page}`, auth)
        .then((res) => {
          setTimeout(() => {
            if (res.data.postList.length) {
              this.reviews = this.reviews.concat(res.data.postList);
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
  },
  created() {
    this.getUserData();
  },
};
</script>

<style>
.review-list-top {
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 0 15px;
}
.review-length {
  align-self: center;
}
.review-list {
  width: 90%;
}
</style>