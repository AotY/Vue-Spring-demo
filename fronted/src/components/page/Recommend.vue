<template>
  <div class="recommend-wrapper">
    <v-carousel></v-carousel>


    <div class="floor-wrapper" v-for="parentCategory in categorys">
      <div class="title-wrapper">
        <h1 class="floor-title floor-name">{{ parentCategory.name }}</h1>
        <!--<router-link class="category-link" :to="{ path: '/list/category', params: { categoryId: parentCategory.id }}">-->
          <!--<h1 class="floor-title floor-more " >更多>></h1>-->
        <!--</router-link>-->

        <!--<router-link :to="{ name: 'ProductListByCategoryId', params: { categoryId: parentCategory.id }}">-->
          <a v-bind:href="'#/list/category/' + parentCategory.id"><h1 class="floor-title floor-more " >更多>></h1></a>
        <!--</router-link>-->
      </div>

      <el-row :gutter="10">
        <el-col :span="6" v-for="item in parentCategory.categoryVoList">
          <el-card :body-style="{ padding: '0px'}">
            <a v-bind:href="'#/list/category/' + item.id" class="card-link">
            <!--<router-link class="category-link" :to="{ path: '/list/category', params: { categoryId: item.id }}">-->
            <!--<router-link :to="{ name: 'ProductListByCategoryId', params: { categoryId: item.id }}">-->
              <img v-bind:src="item.imageHost + item.image" class="image">
              <div style="padding: 15px;">
                <div class="line"></div>
                <span class="name-span">{{ item.name }}</span>
                <div class="bottom clearfix">
                </div>
              </div>
            </a>
            <!--</router-link>-->
          </el-card>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
  // 这个页面是主要展示的页面

  import vCarousel from './../common/Carousel.vue'

  //  import productApi from '../../api/portal/productapi.js'
  import categoryApi from '../../api/portal/categoryapi'

  //  import vSearch from './../common/Search.vue'
  // 请求服务器数据
  export default {
    data () {
      return {
        categorys: []
      }
    },
    created () {
      this.getData()
    },
    methods: {
      getData () {
        console.log('--- getData ---')
        let self = this
        // 获取第一级类别（category）
        categoryApi.getRecommendCategory(self, 0).then((res) => {
          console.log(res.data)
          self.categorys = res.data.data
        })
//        self.categorys.forEach(function (category) {
//          productApi.getList(this, category.categoryId).then((res) => {
//            category.list = res.data.data.list
//          })
//          console.log(self.categorys)
//        })
      }
    },
    components: {
      vCarousel
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


  .recommend-wrapper {
    margin: 0 auto;
    width: 1080px;
  }


  .floor-wrapper {
    margin-top: 30px;
  }


  .title-wrapper {
    width: 1080px;
    border-bottom: 1px solid #c60023;
    height: 50px;
    margin-bottom: 20px;
  }

  .floor-title {
    color: #c60023;
    line-height: 30px;
    font-weight: 400;
    font-size: 20px;
  }

  .floor-name {
    float: left;
  }

  .floor-more {
    float: right;
  }

  .image {
    padding-top: 10px;
    margin: 0 auto;
    width: 75%;
    height: 180px;
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



  .name-span {
    font-size: 16px;
    color: #333;
  }

  a {
    color: #FFFFFF;
    text-decoration: none;
  }


</style>
