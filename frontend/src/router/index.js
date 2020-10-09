import Vue from "vue";
import VueRouter from "vue-router";
import constants from "../lib/constants";
import Home from "../views/Home.vue";
import test from "../views/introduce/test.vue";
import Join from "../components/user/Join.vue";
import editUser from "../components/user/editUser.vue";
import Profile from "../components/user/Profile.vue";
import userList from "../components/user/userList.vue";
import accuseList from "../components/user/accuseList.vue";
import klogin from "@/components/user/klogin.vue";
import adminLogin from "@/components/user/adminLogin.vue";
import userDetail from "@/components/user/userDetail.vue";
import aboutUs from "../views/introduce/aboutUs.vue";
import articleCreate from "../views/articles/articleCreate.vue";
import articleDetail from "../views/articles/articleDetail.vue";
import articleUpdate from "../views/articles/articleUpdate.vue";
import articleList from "../views/articles/articleList.vue";
import localList from "@/views/articles/localList.vue";
import postCreate from "../views/post/postCreate";
import postDetail from "../views/post/postDetail";
import postUpdate from "../views/post/postUpdate";
import postList from "../views/post/postList";
import reviewCreate from "../views/reviews/reviewCreate";
import reviewUpdate from "../views/reviews/reviewUpdate";
import reviewList from "../views/reviews/reviewList";
import tempList from "../views/articles/tempList.vue";
import err400 from "../views/errors/err400.vue";
import err404 from "../views/errors/err404.vue";
import err500 from "../views/errors/err500.vue";
import searchList from "../views/searchList.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "HOME",
    component: Home,
  },
  {
    path: "/test",
    name: "test",
    component: test,
  },
  {
    path: "/user/admin",
    name: "adminLogin",
    component: adminLogin,
  },
  {
    path: "/user/join",
    name: constants.URL_TYPE.USER.JOIN,
    component: Join,
  },
  {
    path: "/user/profile",
    name: constants.URL_TYPE.USER.PROFILE,
    component: Profile,
  },
  {
    path: "/user/profile/:ID",
    name: "userDetail",
    component: userDetail,
  },
  {
    path: "/user/edit",
    name: constants.URL_TYPE.USER.EDIT,
    component: editUser,
  },
  {
    path: "/user/klogin",
    name: "klogin",
    component: klogin,
  },
  {
    path: "/introduce",
    name: "aboutUs",
    component: aboutUs,
  },
  {
    path: "/create",
    name: "articleCreate",
    component: articleCreate,
  },
  {
    path: "/detail/:ID",
    name: "articleDetail",
    component: articleDetail,
  },
  {
    path: "/update/:ID",
    name: "articleUpdate",
    component: articleUpdate,
  },
  {
    path: "/article",
    name: "articleList",
    component: articleList,
  },
  {
    path: "/templist",
    name: "tempList",
    component: tempList,
  },
  {
    path: "/localList",
    name: "localList",
    component: localList,
  },
  {
    path: "/pcreate",
    name: "postCreate",
    component: postCreate,
  },
  {
    path: "/pdetail/:ID",
    name: "postDetail",
    component: postDetail,
  },
  {
    path: "/pupdate",
    name: "postUpdate",
    component: postUpdate,
  },
  {
    path: "/posts",
    name: "postList",
    component: postList,
  },
  {
    path: "/rcreate",
    name: "reviewCreate",
    component: reviewCreate,
  },
  {
    path: "/rupdate/:ID",
    name: "reviewUpdate",
    component: reviewUpdate,
  },
  {
    path: "/reviews",
    name: "reviewList",
    component: reviewList,
  },
  {
    path: "/searchlist",
    name: "searchList",
    component: searchList,
  },
  {
    path: "*",
    redirect: "/400",
  },
  {
    path: "/400",
    component: err400,
  },
  {
    path: "*",
    redirect: "/404",
  },
  {
    path: "/404",
    component: err404,
  },
  {
    path: "*",
    redirect: "/500",
  },
  {
    path: "/500",
    component: err500,
  },
  {
    path: "/user/userlist",
    name: "userList",
    component: userList,
  },
  {
    path: "/user/accuselist",
    name: "accuseList",
    component: accuseList,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  const publicPages = [
    constants.URL_TYPE.USER.LOGIN,
    constants.URL_TYPE.USER.JOIN,
    "HOME",
    "articleList",
    "postList",
    "klogin",
    "adminLogin",
    "searchList",
    "aboutUs",
    "reviewList",
  ];
  const authPages = [
    constants.URL_TYPE.USER.LOGIN,
    constants.URL_TYPE.USER.JOIN,
  ];

  const authRequired = !publicPages.includes(to.name);
  const unauthRequired = authPages.includes(to.name);
  var isLoggedIn = false;
  if (Vue.$cookies.isKey("auth-token")) {
    isLoggedIn = true;
  } else if (Vue.$cookies.isKey("admin-token")) {
    isLoggedIn = true;
  }

  if (unauthRequired && isLoggedIn) {
    next(from);
  }

  if (authRequired && !isLoggedIn) {
    Swal.fire({
      icon: 'warning',
      height: 300,
      width: 350,
      title: '<a style="font-size:1rem; color:black">로그인 후 이용해 주세요!!</a>'
    });
    next("/");
  } else {
    next();
  }
});

export default router;
