<template>
  <div class="like-content">
    <button v-if="!isLiked" @click="like" class="like-review">
      <i class="fas fa-heart"></i> 찜
    </button>

    <button v-if="isLiked" @click="like" class="like-review">
      <i class="fas fa-heart-broken"></i> 찜 취소
    </button>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
const BACK_URL = process.env.VUE_APP_BACK_URL;

export default {
  name: "articleLike",
  props: {
    isLiked: Boolean,
  },
  methods: {
    like() {
      this.articleData.token = this.$cookies.get("auth-token");
      axios
        .post(
          `${BACK_URL}/like/${this.articleData.articleId}`,
          this.articleData
        )
        .then(() => {
          this.$emit("like-change");
        })
        .catch((err) => console.log(err));
    },
  },
  computed: {
    ...mapState(["articleData"]),
  },
};
</script>

<style>
.like-content {
  font-size: 17px;
  text-align: center;
}
.like-content .like-review {
  display: block;
  text-align: center;
  background: #ee6e9f;
  border-radius: 3px;
  box-shadow: 0 10px 20px -8px rgb(240, 75, 113);
  padding: 12px 12px 7px 12px;
  font-size: 17px;
  cursor: pointer;
  border: none;
  outline: none;
  color: #ffffff;
  text-decoration: none;
  -webkit-transition: 0.3s ease;
  transition: 0.3s ease;
}
.like-content .like-review:hover {
  transform: translateY(-3px);
}
.like-content .like-review .fa {
  margin-right: 5px;
}
</style>