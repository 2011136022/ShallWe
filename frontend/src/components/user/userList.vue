<template>
  <div class="mt-5">
    <h3 class="mb-3 our-main-font">유저관리</h3>
    <table class="table">
      <thead class="table-dark">
        <tr>
          <th scope="col" class="our-main-font">#</th>
          <th scope="col" class="our-main-font">이메일</th>
          <th scope="col" class="our-main-font">닉네임</th>
          <th scope="col" class="our-main-font">지역</th>
          <th scope="col" class="our-main-font">레벨</th>
          <th scope="col" class="our-main-font">포인트</th>
          <th scope="col" class="our-main-font">회원관리</th>
        </tr>
      </thead>
      <tbody v-for="user in users" :key="user.userId">
        <tr>
          <td class="our-main-font" style="font-size: 13px;">{{ user.userId }}</td>
          <td class="our-main-font" style="font-size: 13px;">{{ user.email }}</td>
          <td class="our-main-font" style="font-size: 13px;">{{ user.nickname }}</td>
          <td class="our-main-font" style="font-size: 13px;">{{ user.address }}</td>
          <td class="our-main-font" style="font-size: 13px;">{{ user.grade }}</td>
          <td class="our-main-font" style="font-size: 13px;">{{ user.userPoint }}</td>
          <td>
            <router-link :to="{name: 'userDetail', params: { ID: `${user.userId}` }}">
              <button class="see-detail">
                <span class="our-main-font" style="font-size: 13px;">보러가기</span>
                <svg class="arrow-effect" width="13px" height="10px" viewBox="0 0 13 10">
                <path d="M1,5 L11,5"></path>
                <polyline points="8 1 12 5 8 9"></polyline>
                </svg>
              </button>
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import { mapState, mapActions } from 'vuex'

  export default {
    name: 'userList',
    data() {
      return {
      }
    },
    methods: {
      ...mapActions([ 'getUsers', 'getArticles' ]),
    },
    computed: {
      ...mapState([ 'users', 'articles' ]),
    },
    created() {
      this.getUsers()
    },
  }
</script> 

<style>
.info-btn {
  border: none;
  outline: none;
  border-radius: 3px;
  padding: 7px 7px;
  font-size: 14px;
  background-color: #ee6e9f;
  color: white;
  margin: 0 0 0;
  opacity: 0.8;
}
.info-btn:hover {
  transition-duration: 0.5s;
  opacity: 1;
}
</style>