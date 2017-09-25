<template>
  <div>
    <v-search-input></v-search-input>
    <v-carousel></v-carousel>

    <div class="floor-wrapper" v-for="category in categorys">
      <h1 class="floor-title">{{ category.name }}</h1>
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in category.list">
          <el-card :body-style="{ padding: '0px'}">
            <a v-bind:href="'/detail/productId/' + item.id"  class="card-link">
              <img v-bind:src="item.imageHost + item.mainImage" class="image">
              <div style="padding: 15px;">
                <div class="line"></div>
                <span class="name-span">{{ item.name }}</span>
                <div class="bottom clearfix">
                  <el-rate
                    v-model="item.rate"
                    disabled
                    show-text
                    text-color="#ff9900"
                    text-template="{value}">
                  </el-rate>
                  <span class="price">{{ item.price }}<span>.00 元</span></span>
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
  import vSearchInput from './../common/Search.vue'
  import productApi from '../../api/productapi.js'

//  import vSearch from './../common/Search.vue'
  // 请求服务器数据
  export default {
    data () {
      return {
        categorys: [
          {name: 'F1 数码3C', categoryId: 100002, list: []},
          {name: 'F2 家用电器', categoryId: 100001, list: []}
        ]
      }
    },
    created () {
      this.getData()
    },
    methods: {
      getData () {
        console.log('--- getData ---')
        let self = this
        self.categorys.forEach(function (category) {
          productApi.getList(this, category.categoryId).then((res) => {
//            self.list = res.data.data.list
            category.list = res.data.data.list
          })
          console.log(self.categorys)
        })
      }
    },
    components: {
      vCarousel, vSearchInput
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
