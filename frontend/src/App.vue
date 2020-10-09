<template>
  <div id="app">
    
    <div class="container" id="Top">
      <Header :isHeader="isHeader" />
    </div>

    <div class="container">
      <router-view />
    </div>

    <div class="footer-background">
      <div class="container">
        <Footer />
      </div>
    </div>

    <a href="#Top">
      <button class="totheTop">
        <i class="fas fa-arrow-up"></i>
      </button>
    </a>

  </div>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<script>
  import Header from "./components/common/Header.vue";
  import Footer from "./components/common/Footer.vue";
  import constants from "./lib/constants";

  export default {
    name: "App",
    components: {
      Header,
      Footer,
    },
    created() {
      let url = this.$route.name;
      this.checkUrl(url);
    },
    watch: {
      $route(to) {
        this.checkUrl(to.name);
      },
    },
    methods: {
      checkUrl(url) {
        let array = [constants.URL_TYPE.USER.LOGIN, constants.URL_TYPE.USER.JOIN];
        let isHeader = true;
        array.map((path) => {
          if (url === path) isHeader = false;
          }
        );
        this.isHeader = isHeader;
      },
    },
    data() {
      return {
        isHeader: true,
        constants,
      };
    },
  };
</script>

<style></style>