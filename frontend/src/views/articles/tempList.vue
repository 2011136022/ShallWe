<template>
  <div class="mt-5">
    <div class="container">
      <div class="row">
        <div
          class="card col-3"
          style="width: 18rem;"
          v-for="temp in articles"
          :key="temp.pid"
        >
          <img
            src="https://source.unsplash.com/random"
            class="card-img-top"
            alt="..."
          />
          <div class="card-body">
            <h5 class="card-title">{{ temp.title }}</h5>
            <p class="card-text">{{ temp.description }}</p>
            <router-link
              class="btn btn-primary"
              :to="{ name: 'articleUpdate', params: { ID: `${temp.pid}` } }"
              >수정하기</router-link
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import { mapActions, mapState } from "vuex";

export default {
  name: "tempList",
  methods: {
    ...mapActions(["getArticles"]),
  },
  computed: {
    ...mapState(["articles"]),
  },
  created() {
    this.getArticles(false);
  },
};
</script>

<style></style>
