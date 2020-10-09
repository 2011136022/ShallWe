<template>
  <div class="mt-5">
    <div class="container detail" style="font-family: 'Recipekorea', cursive; font-size:16.5px">
      <div class="top">
        <div class="top-row">
          <div class="detail-title ml-1">{{ articleData.title }}</div>
          <div v-if="udflag">
            <div
              class="article-drop dropdown dropleft"
              v-if="articleData.userId === userData.userId"
            >
              <button type="button" class="article-btn" data-toggle="dropdown">
                <i class="fas fa-ellipsis-h"></i>
              </button>
              <div class="dropdown-menu px-0 w-1 pr-0 control-width">
                <router-link
                  class="articleUpdate"
                  style="text-decoration: none; color: black"
                  :to="{
                    name: 'articleUpdate',
                    params: { ID: this.$route.params.ID },
                  }"
                >
                  <a class="dropdown-item pr-0 articleUpdate">수정</a>
                </router-link>
                <a
                  class="dropdown-item pr-0"
                  @click="
                    deleteArticle({
                      articleId: articleData.articleId,
                      categoryId: articleData.categoryId,
                    })
                  "
                >삭제</a>
              </div>
            </div>
          </div>
          <div v-if="articleData.userId !== userData.userId">
            <b-button v-b-modal.modal-1 class="siren-btn">신고</b-button>

            <b-modal :hide-footer="true" id="modal-1">
              <h6 class="our-main-font">신고 사유</h6>
              <div class="dropdown our-main-font">
                <button
                  class="btn btn-secondary btn-sm dropdown-toggle our-main-font"
                  type="button"
                  id="dropdownMenuButton"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >{{ kind }}</button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                  <a class="dropdown-item" href="#" @click="changeAccuseKind(1)">욕설, 비난을 했어요!</a>
                  <a class="dropdown-item" href="#" @click="changeAccuseKind(2)">약속 장소에 나오지 않았어요!</a>
                  <a class="dropdown-item" href="#" @click="changeAccuseKind(3)">광고를 하고 있어요!</a>
                </div>
              </div>
              <h6 class="mt-3 our-main-font">사유 상세</h6>
              <b-form-textarea
                id="textarea-rows"
                rows="8"
                style="width: 400px"
                v-model="accuseArticleData.accuseReason"
              ></b-form-textarea>
              <hr />
              <button
                @click="createArticleAccuse({ accuseArticleData })"
                class="btn btn-danger btn-sm our-main-font"
              >신고접수</button>
            </b-modal>
          </div>
          <br />
        </div>
        <div
          :class="{
            tagList: tagsLength(articleData.tags),
            nomargin: !tagsLength(articleData.tags),
          }"
        >
          <button
            class="tag-btn raise-btn"
            v-for="tag in articleData.tags"
            :key="tag.no"
            @click="putWord(tag)"
          >#{{ tag }}</button>
        </div>
        <div class="in-the-top ml-1">
          <div class="writer mt-1">
            <router-link
              style="text-decoration: none; color: black"
              :to="{
                name: 'userDetail',
                params: { ID: articleData.userId },
              }"
            >{{ articleData.writer }}</router-link>
            <br />
          </div>
          <div class="create-time" style="font-size:12px">{{ articleData.timeAgo }}</div>
        </div>
      </div>
      <hr class="top-line" />
      <div class="middle-row">
        <img class="MyImage" :src="imageUrl" alt="..." />
        <div class="articleInfo">
          <div class="detail-info">
            <div class="detail-address">만남의 장소: {{ articleData.address }}</div>
            <div class="detail-price">
              <div class="min-price">최소 주문 금액 : \{{ minPrice }}</div>
              <div class="min-price mt-2">모인 금액 : \{{ sumPrice }}</div>
            </div>
            <div class="detail-endTime">마감 시간 : {{ cutDate(articleData.endTime) }}까지</div>
            <div class="article-url mt-2" style="overflow:hidden; word-wrap:break-word;">
              사이트 url :
              <div>{{ articleData.urlLink }}</div>
            </div>
            <div v-if="checkedStatus" class="mt-2">
              오픈 채팅방 url :
              <div>{{ articleData.openLink }}</div>
            </div>
          </div>
          <div class="detail-btns mt-2">
            <articleLike @like-change="likeChange" :isLiked="isLiked" />
            <a
              href="javascript:;"
              class="mx-1 kakao-share-link"
              @click="shareContent"
              id="kakao-link"
            >
              <button @click="shareContent" class="detail-share">
                <i class="fas fa-share-alt"></i> 공유
              </button>
            </a>
            <div v-if="articleData.status === 4 && checkParticipant" class="like-content">
              <router-link
                :to="{ name: 'reviewCreate' }"
                class="ml-auto routerLink"
                style="text-decoration: none; color: black"
              >
                <button class="review-create-button">
                  <i class="fas fa-pencil-alt"></i> 후기
                </button>
              </router-link>
            </div>
            <div v-if="articleData.status < 4">
              <b-button id="show-btn" v-b-modal.join-modal class="participate-btn" v-if="joinFlag">
                <i class="fas fa-user-plus"></i> 참여
              </b-button>
            </div>
            <div v-if="articleData.writer === userData.nickname">
              <div v-if="articleData.status === 3">
                <b-button
                  id="show-btn"
                  class="detail-join"
                  v-if="articleData.minPrice <= articleData.sumPrice"
                  @click="confirmPurchase"
                >
                  <i class="fas fa-user-plus"></i>확정
                </b-button>
              </div>
            </div>
            <b-modal id="join-modal" size="xl" ref="modal" class="form-input" @ok="sendJoinData">
              <form ref="form" class="form-input" @submit.stop.prevent="handleSubmit">
                <b-form-group label="제목" label-for="title-input" class="our-main-font">
                  <b-form-input
                    style="width: 400px;"
                    id="title-input"
                    v-model="joinData.title"
                    class="form-input our-main-font"
                  ></b-form-input>
                </b-form-group>

                <b-form-group label="url" label-for="url-input" class="our-main-font">
                  <b-form-input
                    style="width: 400px;"
                    id="url-input"
                    v-model="joinData.url"
                    class="form-input our-main-font"
                  ></b-form-input>
                </b-form-group>

                <b-form-group label="가격(숫자만 입력하세요.)" label-for="price-input" class="our-main-font">
                  <b-form-input
                    style="width: 400px;"
                    id="price-input"
                    v-model="joinData.price"
                    class="form-input our-main-font"
                  ></b-form-input>
                </b-form-group>

                <b-form-group label="요구사항" label-for="order-input" class="our-main-font">
                  <b-form-input
                    style="width: 400px;"
                    id="order-input"
                    v-model="joinData.description"
                    class="form-input our-main-font"
                  ></b-form-input>
                </b-form-group>
              </form>
            </b-modal>

            <b-modal id="update-modal" size="xl" ref="modal" title="수정하기" @ok="updateJoinData">
              <form ref="form" @submit.stop.prevent="handleSubmit">
                <b-form-group label="제목" label-for="title-input" class="our-main-font">
                  <b-form-input id="title-input" v-model="joinData.title"></b-form-input>
                </b-form-group>

                <b-form-group label="url" label-for="url-input" class="our-main-font">
                  <b-form-input id="url-input" v-model="joinData.url"></b-form-input>
                </b-form-group>

                <b-form-group label="가격" label-for="price-input" class="our-main-font">
                  <b-form-input id="price-input" v-model="joinData.price"></b-form-input>원
                </b-form-group>

                <b-form-group label="요구사항" label-for="order-input" class="our-main-font">
                  <b-form-input id="order-input" v-model="joinData.description"></b-form-input>
                </b-form-group>
              </form>
            </b-modal>
          </div>
        </div>
      </div>
      <div class="detail-content" id="item-1" style="overflow:hidden; word-wrap:break-word;">
        <div>{{ articleData.description }}</div>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="col-5 d-flex justify-content-end mt-5" style="margin-left: 8%;">
          <kakaoMapForDetail />
        </div>
        <div
          class="col-5 d-flex justify-content-start"
          style="margin-top: 6%; margin-left: 7%; font-family: 'Recipekorea', cursive;"
        >
          <DoughnutChart />
        </div>
      </div>
    </div>
    <div class="members mt-5" style="font-family: 'Recipekorea', cursive;">
      <div class="members-start" style="font-size:19px">
        <i class="fas fa-users"></i>
        참여 멤버 (총 {{ articleData.partList.length }}명)
      </div>
      <div class>
        <div class="member-list" v-for="participant in articleData.partList" :key="participant.no">
          <div class="member">
            <div class="member-writer-info">
              <div class="members-status">
                <div class="member-writer" style="font-size:18px">
                  <router-link
                    style="text-decoration: none; color: black"
                    :to="{
                      name: 'userDetail',
                      params: { ID: participant.userId },
                    }"
                  >{{ participant.writer }}</router-link>
                  <i v-if="articleData.userId === participant.userId" class="fas fa-crown"></i>
                </div>
                <div
                  class="article-participant-status"
                  v-if="participant.userId != articleData.userId"
                >
                  <button v-if="participant.status === 0" class="waiting-btn">대기중</button>
                  <button v-if="participant.status === 1" class="accepted-btn">수락됨</button>
                  <button v-if="participant.status === 2" class="denied-btn">거절됨</button>
                </div>
              </div>
            </div>
            <div v-if="participant.status === 0">
              <div
                class="article-drop dropdown dropleft"
                v-if="participant.userId === userData.userId"
              >
                <button type="button" class="article-btn" data-toggle="dropdown">
                  <i class="fas fa-ellipsis-v"></i>
                </button>
                <div class="dropdown-menu">
                  <b-button variant="light" size="sm" v-b-modal.update-modal>수정</b-button>
                  <b-button variant="light" size="sm" @click="cancel(participant.no)">삭제</b-button>
                </div>
              </div>

              <div
                class="article-drop dropdown dropleft"
                v-if="articleData.writer === userData.nickname"
              >
                <div v-if="participant.status === 0">
                  <button type="button" class="article-btn" data-toggle="dropdown">
                    <i class="fas fa-ellipsis-v"></i>
                  </button>
                  <div class="dropdown-menu dropleft">
                    <b-button
                      class="dropdown-item"
                      size="sm"
                      variant="light"
                      @click="acceptParticpation(participant.writer)"
                    >수락</b-button>
                    <b-button
                      class="dropdown-item"
                      size="sm"
                      variant="light"
                      @click="$bvModal.show('modal-scoped')"
                    >거절</b-button>

                    <b-modal id="modal-scoped">
                      <p class="our-main-font">정말 거절하시겠습니까?</p>
                      <template v-slot:modal-footer="{ ok }">
                        <b-button
                          size="sm"
                          variant="danger"
                          @click="denyParticpation(participant)"
                          class="our-main-font"
                        >거절</b-button>
                        <b-button class="our-main-font" size="sm" variant="success" @click="ok()">취소</b-button>
                      </template>
                    </b-modal>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div
            v-if="articleData.userId !== participant.userId"
            class="member-title"
            style="font-size:18px"
          >{{ participant.title }}</div>
          <div
            v-if="articleData.userId !== participant.userId"
            class="member-content mt-1"
            style="font-size:17.5px"
          >요구사항: {{ participant.description }}</div>
          <div
            class="member-price py-1 mt-1"
            style="font-size:17.5px"
          >가격: \{{ parPrice(participant.price) }}</div>
        </div>
      </div>
    </div>
    <commentList />
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import { mapState, mapActions } from "vuex";
import commentList from "@/components/comments/commentList";
import articleLike from "@/components/articles/articleLike";
import kakaoMapForDetail from "@/components/articles/kakaoMapForDetail";
import DoughnutChart from "@/components/articles/DoughnutChart";

export default {
  name: "articleDetail",
  components: {
    commentList,
    articleLike,
    kakaoMapForDetail,
    DoughnutChart,
  },
  data() {
    return {
      isLiked: false,
      joinData: {
        articleId: this.$route.params.ID,
        title: "",
        url: "",
        price: "",
        description: "",
        token: this.$cookies.get("auth-token"),
      },
      accuseArticleData: {
        reporter: "",
        defendant: "",
        accuseIndex: 1,
        accuseValue: "",
        accuseKind: 0,
        accuseReason: "",
        accuseUrl: document.URL,
        accuseConfirm: 0,
        token: this.$cookies.get("auth-token"),
      },
      isDenied: false,
      searchData: {
        searchDataForSend: {
          subject: "tag",
          word: "",
        },
        categoryId: 0,
        temp: 1,
      },
      kind: "선택",
    };
  },
  computed: {
    ...mapState(["articleData", "userData"]),
    minPrice() {
      var res = new Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(this.articleData.minPrice);
      let result = res + "";
      const real = result.substring(1, res.length);
      return real;
    },
    sumPrice() {
      var res = new Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(this.articleData.sumPrice);
      let result = res + "";
      const real = result.substring(1, res.length);
      return real;
    },
    parPrice() {
      return (price) => {
        var res = new Intl.NumberFormat("ko-KR", {
          style: "currency",
          currency: "KRW",
        }).format(price);
        let result = res + "";
        const real = result.substring(1, res.length);
        return real;
      };
    },
    imageUrl() {
      try {
        return "http://i3b203.p.ssafy.io/img/" + `${this.articleData.image}`;
      } catch {}
    },
    udflag() {
      var tmp = 0;
      for (const t of this.articleData.partList) {
        if (t.status === 1) {
          tmp++;
        }
      }

      return tmp === 1 ? true : false;
    },
    joinFlag() {
      const tempList = [];
      for (const p of this.articleData.partList) {
        tempList.push(p.writer);
      }
      if (tempList.includes(this.userData.nickname)) {
        return false;
      } else {
        return true;
      }
    },
    checkedStatus() {
      const tempList = [];
      for (const p of this.articleData.partList) {
        if (p.status === 1 || p.status == 0) {
          tempList.push(p.writer);
        }
      }
      if (tempList.includes(this.userData.nickname)) {
        return true;
      } else {
        return false;
      }
    },
    checkParticipant() {
      const participantList = [];
      for (const par of this.articleData.partList) {
        if (par.status === 1) {
          participantList.push(par.userId);
        }
      }
      if (participantList.includes(this.userData.userId)) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    ...mapActions([
      "getArticle",
      "createArticleAccuse",
      "detailSearch",
      "deleteArticle",
    ]),
    denyConfirm() {
      this.isDenied = true;
    },
    acceptParticpation(participant) {
      axios
        .put(
          `${BACK_URL}/participant/accept/${this.articleData.articleId}/${participant}`
        )
        .then((response) => {
          Swal.fire({
            icon: "success",
            height: 300,
            width: 300,

            title:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">참여자 수락되었습니다!</a>',
            showConfirmButton: false,
            timer: 1500,
          });
          this.getArticle(this.$route.params.ID);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    denyParticpation(participant) {
      axios
        .put(
          `${BACK_URL}/participant/denied/${this.articleData.articleId}/${participant.writer}`
        )
        .then((response) => {
          alert(response.data);
          this.isDenied = false;
          participant.status = 2;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    confirmPurchase() {
      axios
        .put(`${BACK_URL}/post/complete/${this.articleData.articleId}`)
        .then((response) => {
          Swal.fire({
            icon: "success",
            height: 300,
            width: 300,

            title:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">구매확정 되었습니다!</a>',
            showConfirmButton: false,
            timer: 1500,
          });
          this.getArticle(this.$route.params.ID);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    putWord(tag) {
      this.searchData.searchDataForSend.word = tag;
      this.detailSearch(this.searchData);
    },
    changeAccuseKind(kind) {
      if (kind === 1) {
        this.kind = "욕설, 비난을 했어요!";
      } else if (kind === 2) {
        this.kind = "약속 장소에 나오지 않았어요!";
      } else {
        this.kind = "광고를 하고 있어요!";
      }
      this.accuseArticleData.accuseKind = kind;
      this.linkArticleData();
      this.linkUserData();
    },
    linkArticleData() {
      this.accuseArticleData.accuseIndex = 1;
      this.accuseArticleData.accuseValue = this.articleData.articleId;
      this.accuseArticleData.defendant = this.articleData.writer;
    },
    linkUserData() {
      this.accuseArticleData.reporter = this.userData.nickname;
    },

    tagsLength(tagList) {
      if (tagList.length > 0) return true;
      else false;
    },

    cutDate(date) {
      let CD = date + "";
      const year = CD.substring(0, 4) + "년 ";
      const month = CD.substring(5, 7) + "월";
      const day = CD.substring(8, 10) + "일 ";
      const hour = CD.substring(11, 13) + "시";
      const minute = CD.substring(14, 16) + "분";
      const res = year + month + day + hour + minute;
      return res;
    },
    sendJoinData() {
      if (
        this.joinData.title &&
        this.joinData.price &&
        this.joinData.description &&
        this.joinData.url
      ) {
        axios
          .post(`${BACK_URL}/participant/create`, this.joinData)
          .then((response) => {
            this.getparticipantData();
            Swal.fire({
              icon: "success",
              height: 300,
              width: 200,
              title:
                '<a style="font-size:20px; font-family: Recipekorea; color:black">참가 완료!</a>',
              showConfirmButton: false,
              timer: 1500,
            });
          })
          .catch((error) => {
            console.log(error);
            Swal.fire({
              icon: "error",
              height: 300,
              width: 300,
              title:
                '<a style="font-size:20px; font-family: Recipekorea; color:black">이미 참여한 게시물입니다.</a>',
              showConfirmButton: false,
              timer: 1500,
            });
          });
      } else {
        Swal.fire({
          icon: "warning",
          height: 300,
          width: 350,
          title:
            '<a style="font-size:20px; font-family: Recipekorea; color:black">참여폼을 다 채워주세요!</a>',
          showConfirmButton: false,
          timer: 1800,
        });
      }
    },
    updateJoinData() {
      this.joinData.articleId = this.articleData.articleId;
      axios
        .put(`${BACK_URL}/participant/update`, this.joinData)
        .then((response) => {
          this.getparticipantData();
          Swal.fire({
            icon: "success",
            height: 300,
            width: 200,
            title:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">수정 완료!</a>',
            showConfirmButton: false,
            timer: 1500,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getparticipantData() {
      axios
        .get(`${BACK_URL}/participant/read/${this.$route.params.ID}`)
        .then((response) => {
          this.articleData.partList = response.data.participantList;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    cancel(no) {
      axios
        .delete(`${BACK_URL}/participant/delete/${no}`)
        .then((response) => {
          this.getparticipantData();
          Swal.fire({
            icon: "success",
            height: 300,
            width: 200,
            title:
              '<a style="font-size:20px; font-family: Recipekorea; color:black">삭제 완료!</a>',
            showConfirmButton: false,
            timer: 1500,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },

    shareContent() {
      Kakao.Link.createDefaultButton({
        container: "#kakao-link",
        objectType: "feed",
        content: {
          title: this.articleData.title,
          description: this.articleData.description,
          imageUrl: "https://ifh.cc/g/5VcUjM.png",
          link: {
            mobileWebUrl: "https://developers.kakao.com",
            androidExecParams: "test",
          },
        },
        social: {
          likeCount: this.articleData.likeNum,
          commentCount: this.articleData.commentNum,
        },
        buttons: [
          {
            title: "웹으로 이동",
            link: {
              mobileWebUrl: "https://developers.kakao.com",
            },
          },
        ],
        success: function (response) {
          console.log(response);
        },
        fail: function (error) {
          console.log(error);
        },
      });
    },

    likeCheck() {
      const auth = { token: this.$cookies.get("auth-token") };
      axios
        .post(BACK_URL + `/likedcheck/${this.$route.params.ID}`, auth)
        .then((response) => {
          this.isLiked = response.data.isLiked;
        })
        .catch((err) => console.log(err));
    },
    likeChange() {
      this.getArticle(this.$route.params.ID);
      this.likeCheck();
      if(this.articleData.status===0){
        articleData.title="이 게시글은 신고된 게시글입니다"
        articleData.description="이 게시글은 신고된 게시글입니다"
        articleData.address="이 게시글은 신고된 게시글입니다"
      }
    },
  },
  created: function () {
    this.getArticle(this.$route.params.ID);
    this.likeCheck();
  },
};
</script>

<style>
.members-status {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}
.member-writer {
  text-align: center;
  padding-top: 3px;
}
.article-participant-status {
  vertical-align: 4px;
}
.waiting-btn,
.accepted-btn,
.denied-btn {
  outline: none;
  border-radius: 5px;
  background-color: transparent;
  padding-top: 4px;
  font-size: 14px;
  margin-left: 5px;
  vertical-align: text-bottom;
  height: 27px;
  cursor: none;
}
.waiting-btn,
.accepted-btn,
.denied-btn:focus {
  border: none;
  outline: none;
}
.waiting-btn {
  border: 2px solid rgb(240, 219, 31);
  color: rgb(240, 219, 31);
}
.accepted-btn {
  border: 2px solid rgb(102, 102, 247);
  color: rgb(102, 102, 247);
}
.denied-btn {
  border: 2px solid rgb(218, 37, 37);
  color: rgb(218, 37, 37);
}
.control-width {
  width: 30px;
}
.member-writer-info {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.kakao-share-link {
  text-decoration: none;
}
.kakao-share-link:hover {
  text-decoration: none;
}
.routerLink {
  text-decoration: none;
}
.review-create-button {
  text-decoration: none;
  border: none;
  outline: none;
  background-color: #451453;
  color: #eee;
  border-radius: 3px;
  box-shadow: 0 10px 20px -8px #1d0622;
  padding: 12px 12px 7px 12px;
  -webkit-transition: 0.3s ease;
}
.review-create-button:hover {
  transform: translateY(-3px);
}
.review-create-button .fa {
  margin-right: 5px;
}
.kakao-map {
  display: flex;
  width: 700px;
  margin: 3% 0 0 0;
  justify-content: center;
  text-align: center;
}
.member-content,
.member-title,
.member-price,
.member-create-time {
  text-align: left;
}
.member-create-time {
  color: rgb(145, 141, 141);
  font-size: small;
  margin-bottom: 1%;
}
.member-list {
  border-bottom: 1px solid rgb(237, 237, 240);
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-bottom: 1.5%;
  padding: 0 0 1.5% 0.3%;
}
.member {
  text-align: left;
  font-weight: bold;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.members {
  width: 75%;
  margin: 3% auto;
}
.fa-crown {
  color: rgb(245, 230, 24);
  vertical-align: text-top;
  margin-left: 4px;
}
.members-start {
  width: 100%;
  padding: 0 0 0.5% 0.5%;
  text-align: left;
  border-bottom: 0.5px solid rgb(218, 215, 215);
  margin: 0 0 2% 0;
}
@media screen and (max-width: 768px) {
  b-modal:before {
    display: inline-block;
    vertical-align: middle;
    content: " ";
    height: 100%;
  }
}
b-modal {
  display: inline-block;
  text-align: left;
  vertical-align: middle;
}
b-modal.form-input {
  width: 27%;
  height: 85%;
  margin: 0;
  padding: 0;
}
.detail {
  border: 1px solid rgb(224, 221, 221);
  border-radius: 1.5%;
  width: 75%;
}
.MyImage {
  width: 45%;
  height: 300px;
  margin: 10px 5px 10px 2px;
}
.totheContent {
  background-color: #676d74;
  color: white;
  padding: 8px;
  border-radius: 5px;
}
a {
  justify-content: bottom;
  text-decoration: none;
  color: black;
}

.top {
  margin-bottom: 2%;
  margin-top: 2%;
  text-align: left;
}
.top-row {
  display: flex;
  justify-content: space-between;
}
.detail-title {
  font-size: x-large;
  font-weight: bold;
  width: 90%;
}
.create-time {
  font-size: small;
  font-weight: normal;
  opacity: 0.6;
}
.top-line {
  margin: 0% 0% 2% 0%;
}
.map-btn {
  font-size: medium;
  color: white;
  border: none;
  background-color: rgb(15, 190, 182);
  border-radius: 3px;
  padding: 1% 2% 1% 2%;
}
.middle-row {
  display: flex;
}
.articleInfo {
  text-align: left;
  padding: 15px 30px 35px 35px;
  width: 55%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
@media screen and (max-width: 990px) {
  .middle-row {
    display: flex;
    flex-direction: column;
    text-align: left;
  }
  .articleInfo {
    margin: 0;
    padding: 0;
    width: 100%;
  }
  .detail-content {
    margin: 0;
    padding: 0;
    width: 100%;
  }
}
.detail-address {
  font-size: large;
  font-weight: bold;
  margin-bottom: 5px;
}
.detail-price {
  margin: 5% 0 0 0;
}
.detail-endTime {
  margin: 5% 0 0 0;
  font-size: medium;
}
.kakao {
  height: 25px;
  width: 25px;
}
.kakao-share {
  text-decoration: none;
  color: white;
  font-weight: normal;
  text-align: center;
  vertical-align: middle;
}
.kakao-share:hover {
  text-decoration: none;
  color: white;
}
.detail-content {
  margin: 30px 0;
  padding: 0 2px;
  text-align: left;
}
.detail-btns {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  font-size: 17px;
  text-align: center;
}
.detail-btns .detail-share {
  border: none;
  outline: none;
  background: rgb(250, 227, 1);
  border-radius: 3px;
  color: #ffffff;
  padding: 12px 12px 7px 12px;
  font-size: 17px;
  text-align: center;
  box-shadow: 0 10px 20px -8px rgb(202, 190, 21);
  text-decoration: none;
  -webkit-transition: 0.3s ease;
  transition: 0.3s ease;
  display: block;
}
.detail-btns .detail-share:hover {
  transform: translateY(-3px);
}
.detail-btns .detail-share .fa {
  margin-right: 5px;
}
.participate-btn {
  background-color: #32093d;
  box-shadow: 0 10px 20px -8px rgb(5, 1, 7);
  padding: 12px 12px 7px 12px;
  border-radius: 3px;
  border: none;
  outline: none;
  text-decoration: none;
  -webkit-transition: 0.3s ease;
  transition: 0.3s ease;
  font-size: 17px;
  display: block;
  text-align: center;
}
.detail-btns .participate-btn:hover {
  transform: translateY(-3px);
  background-color: #2e0838;
}
.detail-btns .detail-join {
  background-color: #000000;
  box-shadow: 0 10px 20px -8px rgb(5, 1, 7);
  padding: 12px 12px 7px 12px;
  border-radius: 3px;
  border: none;
  outline: none;
  text-decoration: none;
  -webkit-transition: 0.3s ease;
  transition: 0.3s ease;
  font-size: 17px;
  display: block;
  text-align: center;
}
.detail-btns .detail-join:hover {
  transform: translateY(-3px);
  background-color: #141314;
}
.article-drop {
  display: flex;
  flex-direction: row;
  width: 3%;
}
.article-btn {
  background-color: transparent;
  color: rgb(182, 182, 182);
  padding: 0;
  font-size: 15px;
  border: none;
  cursor: pointer;
  width: 100%;
  outline: none;
}
.article-btn:focus {
  border: none;
  outline: none;
}
.article-drop:hover {
  border: none;
  outline: none;
}
.article-btn:hover {
  border: none;
  outline: none;
}
.router-link-active {
  color: white;
}
.router-link-exact-active a {
  color: white;
}
.articleUpdate {
  text-decoration: none;
  width: 100%;
}
.article:hover {
  text-decoration: none;
}
.share {
  display: flex;
}
.dropdown-item {
  cursor: pointer;
  text-decoration: none;
}
.participants {
  width: 75%;
  margin: auto;
  text-align: left;
  margin-top: 3%;
  border: 1px solid rgb(179, 175, 175);
  border-radius: 3px;
}
.form-input {
  width: 150%;
}
.tagList {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin: 0.5% 0 1% 0;
}
.nomargin {
  margin: 0;
}
</style>

<style scoped lang="scss">
.tag-btn {
  position: relative;

  background-color: transparent;
  border: 3px solid #ee6e9f;
  margin-right: 1%;
  outline: none;
  border-radius: 20px;
  padding: 5px 6px 2px 4px;

  color: #000;
  text-align: center;

  transform-style: flat;
  transition: all 250ms ease-out;

  &:before,
  &:after {
    content: "";
    position: absolute;
    z-index: -2;
    transition: all 250ms ease-out;
  }
}
.raise-btn {
  &:before {
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  &:hover {
    border: 3px solid #ee6e9f;

    &:before {
      box-shadow: 0 15px 10px -10px #e23577;
    }
  }
}
</style>