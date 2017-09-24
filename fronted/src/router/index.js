import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: resolve => require(['../components/common/Home.vue'], resolve),
      children: [
        {
          path: '/',
          component: resolve => require(['../components/page/Recommend.vue'], resolve)
        },
        {
          path: '/index',
          component: resolve => require(['../components/page/Recommend.vue'], resolve)
        }
      ]
    },
    {
      // 商品详情页面
      path: '/detail/productId/:productId',
      component: resolve => require(['../components/page/ProductDetail.vue'])
    },
    {
      // 用户搜索关键词、选择列表后展示的列表
      path: '/list/:category/:keyword',
      component: resolve => require(['../components/page/ProductList.vue'])
    }
  ]
})
