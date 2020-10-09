import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueCookies from 'vue-cookies'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import "chart.js";
import "hchs-vue-charts";
import "@/assets/css/global.css"

Vue.use(VueCookies)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(window.VueCharts);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
