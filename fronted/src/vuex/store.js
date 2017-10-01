import Vue from 'vue'
import Vuex from 'vuex'
//
import siteNav from './modules/siteNav.js'
import breadCrumb from './modules/breadCrumb.js'
// import productDetail from './modules/productDetail.js'
// import productList from './modules/productList.js'
// import cart from './modules/cart.js'
//

Vue.use(Vuex)

//
export default new Vuex.Store({
  modules: {
    siteNav,
    breadCrumb
  }
})
