<template>
  <div>
    <v-carousel></v-carousel>
    <div class="floor-wrapper">
      <h1 class="floor-title">所有商品</h1>
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in list">
          <el-card :body-style="{ padding: '0px'}">
            <a href="/" class="card-link">
              <img v-bind:src="item.imageHost + item.mainImage" class="image">
              <div style="padding: 15px;">
                <div class="line"></div>
                <span class="name-span">{{ item.name }}</span>
                <div class="bottom clearfix">
                  <el-rate
                    v-model="value5"
                    disabled
                    show-text
                    text-color="#ff9900"
                    text-template="{value}">
                  </el-rate>
                  <span class="price">{{ item.price }}<span>.00 元</span></span>
                  <!--<el-button type="text" class="button" v-bind:data-id="item.id">立即购买</el-button>-->
                </div>
              </div>
            </a>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  // 这个页面是主要展示的页面
  import vCarousel from './../common/Carousel.vue'
  import productApi from '../../api/portal/productapi.js'
  // 请求服务器数据
  export default {
    data () {
      return {
        list: [],
        value5: 3.7
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

  .price {

    font-size: 14px;
    color: #222;
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
    width: 85%;
    height: 260px;
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
