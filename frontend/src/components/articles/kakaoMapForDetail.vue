<template>
  <div id="app" style="font-family: 'Recipekorea', cursive; font-size:19px">
    <div id="map"></div>
    <div class="mt-3">{{articleData.address}}</div>
  </div>
</template>

<script>
const APP_KEY = process.env.VUE_APP_KAKAO_APP_KEY;
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState(["articleData"]),
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${APP_KEY}&libraries=services`;
      document.head.appendChild(script);
    }
  },
  updated() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${APP_KEY}&libraries=services`;
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(36.355539125856275, 127.29863289195224),
        level: 3,
      };

      var map = new kakao.maps.Map(container, options);
      var geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(this.articleData.address, function (
        result,
        status
      ) {
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          var marker = new kakao.maps.Marker({
            map: map,
            position: coords,
          });
          var infowindow = new kakao.maps.InfoWindow({
            content:
              '<div style="width:150px;text-align:center;padding:6px 0;">만남의 장소</div>',
          });
          infowindow.open(map, marker);
          map.setCenter(coords);
        }
      });
    },
  },
};
</script>

<style>
#map {
  width: 400px;
  height: 300px;
}
</style>