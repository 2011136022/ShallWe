<template>
  <div>
    <div id="map" style="width:100%;height:350px;"></div>
    <div style="display:none">{{articles[0]}}</div>
  </div>
</template>

<script>
const APP_KEY=process.env.VUE_APP_KAKAO_APP_KEY
import {mapState} from 'vuex'
export default {
  props:{
    articles:Array,
  },
  data(){
    return{
      map:'',
      infowindow:'',
    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${APP_KEY}&libraries=services`;
      document.head.appendChild(script);
    }
  },
  updated(){
    this.initMap()
  },
  methods: {
    initMap() {
      var infowindow = new kakao.maps.InfoWindow({zIndex:1});
      this.infowindow=infowindow
      var mapContainer = document.getElementById('map'),
          mapOption = {
              center: new kakao.maps.LatLng(37.566826, 126.9786567),
              level: 3
          };  
      var map = new kakao.maps.Map(mapContainer, mapOption);
      this.map=map 

      for(const article of this.articles){
        var ps = new kakao.maps.services.Places();
        ps.keywordSearch(article.address, (data, status, pagination)=>{
          if (status === kakao.maps.services.Status.OK) {
              var bounds = new kakao.maps.LatLngBounds();

              for (var i=0; i<data.length; i++) {
                if(data[i].category_group_code==="CS2"){
                  this.displayMarker(data[i]);
                  bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
                }
              }
              map.setBounds(bounds);
          } 
        },);
      } 
    },
    displayMarker(place) {
        var marker = new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(place.y, place.x) 
        });
        marker.address=place.address_name
        kakao.maps.event.addListener(marker, 'click', ()=>{
          this.$emit("setAddress", marker.address);
          this.infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
          this.infowindow.open(this.map, marker);
        });
    },
  },
};
</script>

<style>

</style>