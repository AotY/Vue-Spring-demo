// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// Vuex
import Vuex from 'vuex'
import store from './vuex/store'

// 添加axios 和 element-ui
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'

Vue.config.productionTip = false

Vue.use(Vuex)
Vue.use(ElementUI)
// Every JavaScript object has a prototype. The prototype is also an object.
// The JavaScript prototype property allows you to add new properties to an existing prototype:

// 开启debug模式
Vue.config.debug = true

axios.defaults.withCredentials = true
axios.defaults.timeout = 5000
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
axios.defaults.baseURL = 'http://neteaseyanxuan.leanapp.cn/'

Vue.prototype.$axios = axios

/* eslint-disable no-new */
// new Vue({
//   router,
//   Vuex,
//   store,
//   render: h => h(App)
// }).$mount('#app')

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  Vuex,
  store,
  template: '<App/>',
  components: {App}
})

// 全局过滤器
Vue.filter('priceFormat', function (price) {
  if (!price) return '￥0'
  price = price.toString()
  return '￥' + parseFloat(price).toFixed(2)
})
