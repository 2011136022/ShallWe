<template>
  <div class="createMap">
    <div class="map_wrap">
      <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>

      <div id="menu_wrap" class="bg_white">
        <div class="option">
          <div>
            <div>
              <div>
                장소 :
                <input
                  type="text"
                  :value="userData.address"
                  id="keyword"
                  size="15"
                  @keypress.enter="searchPlaces"
                />
                <button class="kakao-search-btn" @click="searchPlaces">
                  검색
                  <i class="fas fa-search"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
        <hr />
        <ul id="placesList"></ul>
        <div id="pagination"></div>
      </div>
    </div>
  </div>
</template>

<script>
const APP_KEY = process.env.VUE_APP_KAKAO_APP_KEY;
import { mapState } from "vuex";
export default {
  props: {
    coNum: String,
  },
  data() {
    return {
      ps: "",
      infowindow: "",
      markers: [],
      map: "",
    };
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
  computed: {
    ...mapState(["userData"]),
  },
  methods: {
    initMap() {
      var mapContainer = document.getElementById("map");
      var mapOptions = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };
      var map = new kakao.maps.Map(mapContainer, mapOptions);
      var ps = new kakao.maps.services.Places();
      this.ps = ps;
      this.map = map;
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      this.infowindow = infowindow;
      this.searchPlaces();
    },
    searchPlaces() {
      if (document.getElementById("keyword").value.indexOf("편의점") === -1) {
        var keyword = document.getElementById("keyword").value + "편의점";
      } else {
        var keyword = document.getElementById("keyword").value;
      }
      this.ps.keywordSearch(keyword, this.placesSearchCB);
    },
    placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        this.displayPlaces(data);
        this.displayPagination(pagination);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 존재하지 않습니다.");
        return;
        this.displayPagination(pagination);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 존재하지 않습니다.");
        return;
      } else if (status === kakao.maps.services.Status.ERROR) {
        alert("검색 결과 중 오류가 발생했습니다.");
        return;
      }
    },
    displayPlaces(places) {
      var map = this.map;
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      var listEl = document.getElementById("placesList"),
        menuEl = document.getElementById("menu_wrap"),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = "";
      this.removeAllChildNods(listEl);
      this.removeMarker();

      for (var i = 0; i < places.length; i++) {
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
          marker = this.addMarker(placePosition, i),
          itemEl = this.getListItem(i, places[i]);
        bounds.extend(placePosition);
        ((marker, title) => {
          var ditemEl = itemEl;
          var content =
            '<div style="padding:5px;z-index:1;">' + title + "</div>";
          kakao.maps.event.addListener(marker, "mouseover", function () {
            infowindow.setContent(content);
            infowindow.open(map, marker);
          });

          kakao.maps.event.addListener(marker, "mouseout", function () {
            infowindow.close();
          });
          kakao.maps.event.addListener(marker, "click", () => {
            try {
              this.$emit(
                "setAddress",
                ditemEl.querySelector(".jibun").innerText
              );
            } catch {
              this.$emit(
                "setAddress",
                ditemEl.querySelector(".info").getElementsByTagName("span")[0]
                  .innerText
              );
            }
          });
          itemEl.onmouseover = function () {
            infowindow.setContent(content);
            infowindow.open(map, marker);
          };
          itemEl.onmouseout = function () {
            infowindow.close();
          };
          itemEl.onclick = () => {
            try {
              this.$emit(
                "setAddress",
                ditemEl.querySelector(".jibun").innerText
              );
            } catch {
              this.$emit(
                "setAddress",
                ditemEl.querySelector(".info").getElementsByTagName("span")[0]
                  .innerText
              );
            }
          };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
      }
      listEl.appendChild(fragment);
      menuEl.scrollTop = 0;
      this.map.setBounds(bounds);
    },
    getListItem(index, places) {
      var el = document.createElement("li"),
        itemStr =
          '<span class="markerbg marker_' +
          (index + 1) +
          '"></span>' +
          '<div class="info">' +
          "   <h5>" +
          places.place_name +
          "</h5>";

      if (places.road_address_name) {
        itemStr +=
          "    <span>" +
          places.road_address_name +
          "</span>" +
          '   <span class="jibun gray">' +
          places.address_name +
          "</span>";
      } else {
        itemStr += "    <span>" + places.address_name + "</span>";
      }

      itemStr += '  <span class="tel">' + places.phone + "</span>" + "</div>";

      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },
    addMarker(position, idx, title) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png",
        imageSize = new kakao.maps.Size(36, 37),
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691),
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10),
          offset: new kakao.maps.Point(13, 37),
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position,
          image: markerImage,
        });

      marker.setMap(this.map);
      this.markers.push(marker);

      return marker;
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    displayPagination(pagination) {
      var paginationEl = document.getElementById("pagination"),
        fragment = document.createDocumentFragment(),
        i;

      while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
      }

      for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement("a");
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
          el.className = "on";
        } else {
          el.onclick = (function (i) {
            return function () {
              pagination.gotoPage(i);
            };
          })(i);
        }

        fragment.appendChild(el);
      }
      paginationEl.appendChild(fragment);
    },
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },
  },
};
</script>

<style>
.createMap {
  width: 36vw;
  margin: 45px 0 0 0;
}
@media screen and (max-width: 991px) {
  .createMap {
    width: 87vw;
    padding-right: 8vw;
  }
}
.kakao-search-btn {
  border: none;
  outline: none;
  border-radius: 4px;
  background-color: rgb(212, 205, 205);
}
#keyword {
  height: 30px;
  width: 100px;
}
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-size: 12px;
}
.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
  color: #000;
  text-decoration: none;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 500px;
}
#menu_wrap {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 250px;
  margin: 10px 0 30px 10px;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
}
.bg_white {
  background: #fff;
}
#menu_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5f5f5f;
  margin: 3px 0;
}
#menu_wrap .option {
  text-align: center;
}
#menu_wrap .option p {
  margin: 10px 0;
}
#menu_wrap .option button {
  margin-left: 5px;
  padding: 5px;
}
#placesList li {
  list-style: none;
}
#placesList .item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
}
#placesList .item span {
  display: block;
  margin-top: 4px;
}
#placesList .item h5,
#placesList .item .info {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
#placesList .item .info {
  padding: 10px 0 10px 55px;
}
#placesList .info .gray {
  color: #8a8a8a;
}
#placesList .info .jibun {
  padding-left: 26px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png)
    no-repeat;
}
#placesList .info .tel {
  color: #009900;
}
#placesList .item .markerbg {
  float: left;
  position: absolute;
  width: 36px;
  height: 37px;
  margin: 10px 0 0 10px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
    no-repeat;
}
#placesList .item .marker_1 {
  background-position: 0 -10px;
}
#placesList .item .marker_2 {
  background-position: 0 -56px;
}
#placesList .item .marker_3 {
  background-position: 0 -102px;
}
#placesList .item .marker_4 {
  background-position: 0 -148px;
}
#placesList .item .marker_5 {
  background-position: 0 -194px;
}
#placesList .item .marker_6 {
  background-position: 0 -240px;
}
#placesList .item .marker_7 {
  background-position: 0 -286px;
}
#placesList .item .marker_8 {
  background-position: 0 -332px;
}
#placesList .item .marker_9 {
  background-position: 0 -378px;
}
#placesList .item .marker_10 {
  background-position: 0 -423px;
}
#placesList .item .marker_11 {
  background-position: 0 -470px;
}
#placesList .item .marker_12 {
  background-position: 0 -516px;
}
#placesList .item .marker_13 {
  background-position: 0 -562px;
}
#placesList .item .marker_14 {
  background-position: 0 -608px;
}
#placesList .item .marker_15 {
  background-position: 0 -654px;
}
#pagination {
  margin: 10px auto;
  text-align: center;
}
#pagination a {
  display: inline-block;
  margin-right: 10px;
}
#pagination .on {
  font-weight: bold;
  cursor: default;
  color: #777;
}
</style>