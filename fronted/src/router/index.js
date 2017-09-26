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
        // /query/:keyword
        {
          path: '/list',
          component: resolve => require(['../components/common/Product.vue'], resolve),
          children: [
            {
              path: '/list/category/:categoryId',
              component: resolve => require(['../components/page/ProductList.vue'], resolve),
              children: [
                {
                  path: '/list/category/:categoryId/keyword/:keyword',
                  component: resolve => require(['../components/page/ProductList.vue'], resolve)
                }
              ]
            },
            {
              path: '/list/keyword/:keyword',
              component: resolve => require(['../components/page/ProductList.vue'], resolve),
              children: [
                {
                  path: '/list/keyword/:keyword/category/:categoryId',
                  component: resolve => require(['../components/page/ProductList.vue'], resolve)
                }
              ]
            }
          ]
        },
        {
          // 商品详情页面
          path: '/detail/productId/:productId',
          component: resolve => require(['../components/page/ProductDetail.vue'], resolve)
        }
      ]
    }
  ]
})
