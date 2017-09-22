<template>
  <div class="content-wrapper">
    <v-carousel></v-carousel>
    <div class="content">
      <el-row :gutter="30">
        <el-col :span="6" v-for="item in items">
          <el-card :body-style="{ padding: '0px' }">
            <img v-bind:src="item.url" class="image">
              <div style="padding: 15px;">
              <div class="line"></div>
              <span>{{ item.name }}</span>
              <div class="bottom clearfix">
                <time class="time">{{ item.price }}</time>
                <el-button type="text" class="button" v-bind:data-id="item.id">立即购买</el-button>
              </div>
            </div>
          </el-card>
        </el-col>

      </el-row>

    </div>
  </div>
</template>

<script>
  // 这个页面是主要展示的页面
  import vCarousel from './../common/Carousel.vue'
  export default {
    data () {
      return {
        items: [
          {
            name: '毛巾',
            url: '../../../static/images/5.png',
            id: 1,
            price: '20.0元'
          },
          {
            name: '枕头',
            url: '../../../static/images/6.png',
            id: 2,
            price: '50.0元'
          },
          {
            name: '老干妈',
            url: '../../../static/images/7.png',
            id: 3,
            price: '8.9元'
          }
        ]
      }
    },
    components: {
      vCarousel
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

  .content {
    padding-top: 20px;
  }

  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  /*.line {*/
    /*background: #222;*/
    /*background-color: #333333;*/
  /*}*/


</style>
