<template>
  <div class="carousel-wrapper">
    <div class="category-wrapper">
      <ul class="category-list">
        <li class="category-item">
          <!--<router-link class="category-link" to="/list/category/100001">手机 </router-link>-->
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '手机' }}">手机</router-link>
          <!--<a class="category-link" target="_blank" href="#/list/keyword/手机">手机</a>-->
          <router-link class="category-link" :to="{name: 'ListKeyword', params: { keyword: '数码' }}">数码</router-link>
          <!--<a class="category-link" target="_blank" href="#/list/keyword/数码">数码</a>-->
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
      </ul>
    </div>
    <el-carousel height="410px">
      <el-carousel-item v-for="image in images">
        <img v-bind:src="image.url" class="image"></img>
      </el-carousel-item>
    </el-carousel>

  </div>
</template>


<script>
  export default {
    data () {
      return {
        images: [
          {url: '../../../static/images/1.jpg'},
          {url: '../../../static/images/2.jpg'},
          {url: '../../../static/images/3.jpg'},
          {url: '../../../static/images/4.jpg'}
        ]
      }
    }
  }
  //  setActiveItem(index) {
  //    this.$refs.carousel.setActiveItem(index);
  //  }
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
