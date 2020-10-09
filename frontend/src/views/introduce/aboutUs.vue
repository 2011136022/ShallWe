<template>
  <div class="introduce-list-box mt-5">
    <div class="about-us-top">
      <div class="shall-we">
        <span>Sh</span>
        <span id="black">are</span>
        <span class="margin-Left">a</span>
        <span id="black">t</span>
        <span class="margin-Left">l</span>
        <span id="black">oca</span>
        <span>l</span>
        <span id="black" class="margin-Left">area</span>
        <span class="margin-Left">w</span>
        <span id="black">ith</span>
        <span class="margin-Left">e</span>
        <span id="black">njoy</span>
      </div>
      <div>
        <h3 class="mainMenu share-kor" style="color: #000">같은 동네에서 즐겁게 공유하세요</h3>
      </div>
    </div>
    <div class="about-us-middle mt-5">
      <div class="col-4 col-6-medium col-12-small main-point">
        <section class="box style1 main-point-box p-3 _card card__one">
          <img src="http://i3b203.p.ssafy.io/localImg/food1.png" class="foodIcon" />
          <h2 class="font-change mb-4">쉘위 배민</h2>
          <h4 class="font-change">
            <span>
              <br />배달음식의 최소주문금액을 함께
              <br />
              <br />채우고
            </span>
            <span style="color:#ee6e9f" class="font-change">가심비를 즐겨보아요</span>
          </h4>
        </section>
      </div>

      <div class="col-4 col-6-medium col-12-small">
        <section class="box style1 main-point-box p-3 _card card__one">
          <div class="infinite">
          <img src="http://i3b203.p.ssafy.io/localImg/truck1.png" class="truckIcon" />
          </div>
          <div class="infinite-bottom">
            <h2 class="font-change mb-4">쉘위 택배</h2>
            <h4 class="font-change">
              물건의 최저금액을 같이 맞춰
              <span style="color:#ee6e9f" class="font-change">
                <br />
                <br />배송비를 절약
              </span>
              <span class="font-change">해 보아요</span>
            </h4>
          </div>
        </section>
      </div>

      <div class="col-4 col-6-medium col-12-small">
        <section class="box style1 main-point-box p-3 _card card__one">
          <img src="http://i3b203.p.ssafy.io/localImg/N1.png" class="nIcon" />
          <h2 class="font-change mb-4">쉘위 공구</h2>
          <h4 class="font-change">
            같은 물건을 대량구매하여 확
            <br />
            <br />
            <span style="color:#ee6e9f" class="font-change">저렴해진 가격</span>
            <span class="font-change">으로 구매해 보아요</span>
          </h4>
        </section>
      </div>
    </div>

    <div class="about-us-bottom">
      <div class="col-2-small">
        <section class="box style1">
          <div class="about-us-point mb-5">
            <h2 class="font-change" style="color: #000">신뢰성</h2>
            <h4>
              <span class="font-change" style="color: #000">
                회원관리에 있어 점수 또는 등급을 부여
                <br/>
                <div class="mt-1 font-change" style="color: #000">카카오 로그인으로 1인 1계정으로 신뢰점수 ↑</div>
              </span>
            </h4>
          </div>
          <img src="http://i3b203.p.ssafy.io/localImg/trust2.png" class="trustIcon" />
        </section>
      </div>
      <div class="col-2-small">
        <section class="box style1">
          <div class="about-us-point mb-5">
            <h2 class="font-change" style="color: #000">안정성</h2>
            <h4>
              <span class="font-change" style="color: #000">
                주변 CCTV가 많은 편의점 지도 API를 통해
                <br />
                <div class="mt-1 font-change" style="color: #000">사용자가 서로의 위치의 중간지점 편의점 지정</div>
              </span>
            </h4>
          </div>
          <img src="http://i3b203.p.ssafy.io/localImg/Convenience2.png" class="ConIcon" />
        </section>
      </div>
    </div>

    <section class="about-us-map-container">
      <section class="box style1">
        <h2 class="font-change">나의 동네에서 싸게 구매하자!!</h2>
        <p class="font-change">회원가입 때 등록한 가장 가까운 편의점 주소로 같은 동네의 거래 상황을 알 수 있습니다.</p>
      </section>
      <div class="about-us-map">
        <img src="http://i3b203.p.ssafy.io/localImg/about-us-map.png" class="mapIcon" />
      </div>
    </section>
  </div>
</template>

<script>
$(document).ready(function () {
  $("div.infinite")
    .mouseenter(function () {
      $(this).css("-webkit-animation-name", "iteration-count");
    })
    .mouseout(function () {
      $(this).css("-webkit-animation-name", 1);
    });
});
export default {
  name: "aboutUs",
  data() {
    return {
      categoryNum: 0,
    };
  },
  methods: {
    changeCategory(num) {
      this.categoryNum = num;
    },
  },
};
</script>

<style>
.about-us-map-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 0 150px 0;
}
.about-us-map {
  width: 800px;
  height: 350px;
}
.mapIcon {
  width: 100%;
  height: 350px;
}
.about-us-middle {
  display: flex;
  margin: 0 0 120px 0;
}
@font-face {
  font-family: "Recipekorea";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/Recipekorea.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
.font-change {
  font-family: Recipekorea;
  font-weight: normal;
  font-style: normal;
}
.about-us-top {
  margin: 90px 0 140px 0;
}
.share-kor {
  color: #000;
}
.margin-Left {
  margin-left: 20px;
}
.shall-we {
  color: #ee6e9f;
  font-family: "Lobster", "cursive";
  font-size: 80px;
}
#pink {
  display: inline;
  /* text-align: center; */
  color: #ee6e9f;
  font-family: "Lobster", "cursive";
  font-size: 80px;
}
#black {
  color: black;
  font-family: "Lobster", "cursive";
  font-size: 80px;
}
.infinite {
  height: 100px;
  width: 100px;
  margin: 20px 20px;
  animation-iteration-count: infinite;
}
.truckIcon {
  margin: 20px 20px;
  height: 100px;
  width: 100px;
  animation-iteration-count: infinite;
}
@-webkit-keyframes iteration-count {
  from {
    left: 10px;
  }
  to {
    left: 120px;
  }
}
div.infinite {
  position: relative;
  animation-timing-function: linear;
  animation-duration: 2s;
}
.foodIcon {
  height: 100px;
  width: 100px;
  margin: 20px 20px;
}

.nIcon {
  height: 100px;
  width: 100px;
  margin: 20px 20px;
}
.trustIcon {
  height: 250px;
  width: 250px;
  margin: 10px 10px;
}
.ConIcon {
  height: 280px;
  width: 320px;
  margin: 5px 0 5px 60px;
}
h3 {
  margin: 20px 20px;
}

.about-us-bottom {
  background: #ee6e9f;
  margin: 30px;
  padding: 40px;
  display: flex;
  margin: 0 0 150px 0;
}
.col-2-small {
  width: 50%;
  margin: 10px auto;
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
  border-radius: 3px;

  @media screen and (min-width: $medium) {
    flex-basis: calc(33.33% - (#{$grid-gutter * 2} + #{$card-padding * 2}));
    margin: 0 $grid-gutter;
  }
}
.card__one {
  border-radius: 3px;
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
    border-radius: 4px;
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
</style>