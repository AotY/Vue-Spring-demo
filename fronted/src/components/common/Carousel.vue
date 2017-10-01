<template>
  <div class="carousel-wrapper">
    <div class="category-wrapper">
      <ul class="category-list">
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '手机' }}">手机</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '数码' }}">数码</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '电脑' }}">电脑</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '办公配件' }}">办公配件</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '电视' }}">电视</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '空调' }}">空调</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '冰箱' }}">冰箱</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '洗衣机' }}">洗衣机</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '厨卫家电' }}">厨卫家电</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '小家电' }}">小家电</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '家居' }}">家居</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '家具' }}">家具</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '家装' }}">家装</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '个护化妆' }}">个护化妆</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '清洁' }}">清洁</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '纸品' }}">纸品</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '母婴' }}">母婴</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '玩具' }}">玩具</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '童装童鞋' }}">童装童鞋</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '鞋靴' }}">鞋靴</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '箱包' }}">箱包</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '钟表' }}">钟表</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '珠宝' }}">珠宝</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '图书' }}">图书</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '音像' }}">音像</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '电子书' }}">电子书</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '运动户外' }}">运动户外</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '足球' }}">足球</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '汽车生活' }}">汽车生活</router-link>
        </li>
        <li class="category-item">
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '机票' }}">机票</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '酒店' }}">酒店</router-link>
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '旅游' }}">旅游</router-link>
        </li>
      </ul>
    </div>

    <el-carousel height="410px"  v-loading.body="loading" >
      <el-carousel-item v-for="item in list">
        <a v-bind:href="item.link">
          <img class="image"  v-bind:src="imageHost + item.image" v-bind:alt="item.name"/>
        </a>
      </el-carousel-item>
    </el-carousel>

  </div>
</template>


<script>
  import recommendApi from './../../api/portal/recommendapi.js'

  export default {
    data () {
      return {
        list: [],
        imageHost: '',
        loading: true
      }
    },
    created () {
      this.getCarousels()
    },
    methods: {
      getCarousels () {
        let self = this
        recommendApi.getCarousels(self).then(function (response) {
          if (response.data.status === 0) {
            self.imageHost = response.data.data.imageHost
            var i
            var list = []
            for (i in response.data.data.carousels) {
              var item = response.data.data.carousels[i]
              list[i] = {name: item.name, image: item.image, link: '#/detail/productId/' + item.productId}
            }
            self.list = list
            self.loading = false
          }
        })
      }
    }
  }
</script>

<style scoped>

  .carousel-wrapper {
    width: 1080px;
    padding-top: 20px;
    margin: 0 auto;
    margin-top: 30px;
  }

  .category-wrapper {
    float: left;
    padding: 10px 0px;
    height: 390px;
    width: 240px;
    background-color: #777;
    margin-right: 10px;
    color: #ffffff;
    text-align: left;
    font-weight: 800;
  }

  .category-wrapper .category-list {
    margin: 0px;
    padding: 0px;
  }

  .category-wrapper .category-item {
    /*position: relative;*/
    overflow: hidden;
    padding-left: 12px;
    height: 35px;
    line-height: 35px;
    font-size: 14px;
  }

  .category-wrapper .category-item .category-link {
    margin-right: 3px;
    color: #fff;
    text-decoration: none;
  }

  .image {
    width: 100%;
    display: block;
    height: 100%;
  }

  .category-wrapper .category-item .category-link:hover {
    color: #c60023;
    text-decoration: none;
  }

  a {
    text-decoration: none;
  }

</style>
