<template>
  <footer
    class="page-footer font-small unique-color-dark text-white"
    style="font-family: 'Recipekorea', cursive; font-size:13px"
  >
    <!-- Footer Links -->
    <div
      class="container text-center text-md-left mt-5"
      style="margin-top:10px;"
    >
      <!-- Grid row -->
      <div class="row mt-3">
        <!-- Grid column -->
        <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4 mt-5">
          <!-- Content -->
          <h6 class="text-uppercase font-weight-bold">Company name</h6>
          <hr
            class="deep-purple accent-2 mb-4 mt-0 d-inline-block mx-auto"
            style="width: 60px;"
          />
          <p>POA(Pot Of Ambition)</p>
          <p>Cooperation with SSAFY</p>
        </div>
        <!-- Grid column -->

        <!-- Grid column -->
        <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4 mt-5">
          <!-- Links -->
          <h6 class="text-uppercase font-weight-bold">Contents</h6>
          <hr
            class="deep-purple accent-2 mb-4 mt-0 d-inline-block mx-auto"
            style="width: 60px;"
          />
          <p>
            <router-link to="/article" class="ml-0 text-white" id="article"
              >쉘위 게시물</router-link
            >
          </p>
          <p>
            <router-link to="/reviews" class="ml-0 text-white" id="review"
              >쉘위 후기</router-link
            >
          </p>
          <p>
            <router-link to="/posts" class="ml-0 text-white" id="community"
              >쉘위 자유게시판</router-link
            >
          </p>
          <p>
            <router-link to="/localList" class="ml-0 text-white" id="local"
              >내 주변 게시물 찾기</router-link
            >
          </p>
        </div>
        <!-- Grid column -->

        <!-- Grid column -->
        <div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4 mt-5">
          <!-- Links -->
          <h6 class="text-uppercase font-weight-bold">Useful links</h6>
          <hr
            class="deep-purple accent-2 mb-4 mt-0 d-inline-block mx-auto"
            style="width: 60px;"
          />
          <p>
            <a
              href="https://kr.bignox.com/appcenter/game_management/com-sampleapp-on-pc/"
              class="text-white"
              target="_blank"
              >배달의 민족</a
            >
          </p>
          <p>
            <a
              href="https://www.coupang.com/"
              target="_blank"
              class="text-white"
              >쿠팡</a
            >
          </p>
          <p>
            <a href="http://www.ssg.com/" target="_blank" class="text-white"
              >SSG(쓱)</a
            >
          </p>
          <p>
            <router-link to="/introduce" class="ml-0 text-white" id="introduce"
              >도움말</router-link
            >
          </p>
        </div>
        <!-- Grid column -->

        <!-- Grid column -->
        <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4 mt-5">
          <!-- Links -->
          <h6 class="text-uppercase font-weight-bold">Contact</h6>
          <hr
            class="deep-purple accent-2 mb-4 mt-0 d-inline-block mx-auto"
            style="width: 60px;"
          />
          <p><i class="fas fa-home mr-3"></i> 대전, 삼성화재연수원, 한국</p>
          <p><i class="fas fa-envelope mr-3"></i> bigtiger1454@gmail.com</p>
          <p><i class="fas fa-phone mr-3"></i> + 82 010 3481 0904</p>
          <p><i class="fas fa-print mr-3"></i> + 82 010 1234 1234</p>
        </div>
        <!-- Grid column -->
      </div>
      <!-- Grid row -->
    </div>
    <!-- Footer Links -->

    <!-- Copyright -->
    <div class="footer-copyright text-center py-3">
      © 2020 Copyright:
      <a href="http://i3b203.p.ssafy.io/" class="text-white">ShallWe.com</a>
    </div>
    <!-- Copyright -->
  </footer>
  <!-- Footer -->
</template>

<script>
export default {
  name: "Footer",
};
</script>
