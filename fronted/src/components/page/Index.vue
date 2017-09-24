<template>
  <div>
    <v-carousel></v-carousel>
    <div class="floor-wrapper">
      <h1 class="floor-title">F1 数码电器</h1>
      <!--<ul class="floor-con">-->
      <!--<li class="floor-item">-->
      <!--<a>-->
      <!--<span class="floor-text">双开门冰箱</span>-->
      <!--<img class="floor-img" src="../../../static/images/5.png"/>-->
      <!--</a>-->
      <!--</li>-->
      <!--</ul>-->
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in list">
          <el-card :body-style="{ padding: '0px'}">
            <img v-bind:src="item.imageHost + item.mainImage" class="image">
            <div style="padding: 15px;">
              <div class="line"></div>
              <span class="name-span">{{ item.name }}</span>
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
  import productApi from '../../api/productapi.js'
  // 请求服务器数据
  export default {
    data () {
      return {
        list: []
      }
    },
    created () {
      this.getData()
    },
    methods: {
      getData () {
        console.log('--- getData ---')
        let self = this
        productApi.getList(this, 10, 1).then((res) => {
          self.list = res.data.data.list
          console.log(self.list)
        })
      }
    },
    components: {
      vCarousel
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


  .floor-wrapper {
    padding-top: 50px;
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
    padding-top: 10px;
    margin: 0 auto;
    width: 90%;
    height: 250px;
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

  /*.floor-wrapper {*/
  /*overflow: hidden;*/
  /*}*/
  .floor-wrapper .floor-title {
    width: 100%;
    float: left;
    color: #d58717;
    border-bottom: 1px solid #d58717;
    height: 50px;
    line-height: 50px;
    font-weight: 400;
    font-size: 20px;
  }

  .name-span {
    font-size: 13px;
    color: #333;
  }

</style>
