// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// 添加axios 和 element-ui
import axios from 'axios'
import ElementUI from 'element-ui'
import VueLocalStorage from 'vue-localstorage'
import 'element-ui/lib/theme-default/index.css'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(VueLocalStorage)
// Every JavaScript object has a prototype. The prototype is also an object.
// The JavaScript prototype property allows you to add new properties to an existing prototype:

// 开启debug模式
Vue.config.debug = true

axios.defaults.withCredentials = true
Vue.prototype.$axios = axios

/* eslint-disable no-new */
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

// 全局过滤器
Vue.filter('priceFormat', function (price) {
  if (!price) return '￥0'
  price = price.toString()
  return '￥' + parseFloat(price).toFixed(2)
})
