<template>
  <div class="carousel-wrapper">
    <div class="category-wrapper">
      <ul class="category-list">
        <li class="category-item">
          <a class="category-link" target="_blank" href="./list.html?keyword=手机">手机</a>
          <a class="category-link" target="_blank" href="./list.html?keyword=数码">数码</a>
        </li>
        <li class="category-item">
          <a class="category-link" target="_blank" href="./list.html?keyword=电脑">电脑</a>
          <a class="category-link" target="_blank" href="./list.html?keyword=办公配件">办公配件</a>
        </li>
        <li class="category-item">
          <a class="category-link" target="_blank" href="./list.html?keyword=电视">电视</a>
          <a class="category-link" target="_blank" href="./list.html?keyword=空调">空调</a>
          <a class="category-link" target="_blank" href="./list.html?keyword=冰箱">冰箱</a>
          <a class="category-link" target="_blank" href="./list.html?keyword=洗衣机">洗衣机</a>
        </li>
        <li class="category-item">
          <a class="category-link" target="_blank" href="./list.html?keyword=厨卫家电">厨卫家电</a>
          <a class="category-link" target="_blank" href="./list.html?keyword=小家电">小家电</a>
        </li>
      </ul>
    </div>
    <el-carousel height="370px">
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

<style >
  #category-wrapper {
    float: left;
    height: 350px;
    /*border: 2px red solid;*/
    width: 240px;
    background-color: #777;
    margin-right: 10px;
    /*margin: 0 auto;*/
  }
  .carousel-wrapper {
    width: 1080px;
    padding-top: 20px;
    margin: 0 auto;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
    height: 100%;
  }

  .category-wrapper {
    float: left;
    padding: 10px 0;
    width: 240px;
    margin-right: 10px;
    height: 350px;
    background: #666;
    color: #fff;
  }

  .category-wrapper .category-item {
    position: relative;
    overflow: hidden;
    padding-left: 12px;
    height: 35px;
    line-height: 35px;
    font-size: 14px;
  }

  li {
    list-style: none;
  }

  .category-wrapper .category-item .category-link {
    margin-right: 3px;
    color: #fff;
    text-decoration: none;
  }
</style>
