<template>
  <div>
    <div class="list-wrapper">

      <ul class="sorter-con">
        <li v-bind:class="{active: isDefaultActive}" class="sorter-item " v-on:click="sort('default')" >
          <span>默认排序</span>
        </li>

        <li v-bind:class="{active: isPriceActive}" class="sorter-item" v-on:click="sort('price')" >
        <!--<li class="sorter-item" v-on:clik="sort('price')">-->
          <span>价格</span>
          <!--<i class="sort-caret ascending"></i>-->
          <!--<i class="sort-caret descending"></i>-->
          <i v-bind:class="{active: isDesc}" class="fa fa-sort-asc"></i>
          <i v-bind:class="{active: isAsc}" class="fa fa-sort-desc"></i>
        </li>
      </ul>

      <el-row :gutter="10">
        <el-col :span="6" v-for="item in list">
          <el-card :body-style="{ padding: '0px'}">
            <a v-bind:href="'#/detail/productId/' + item.id" class="card-link">
              <img v-bind:src="item.imageHost + item.mainImage" class="image">
              <div style="padding: 15px;">
                <div class="line"></div>
                <span class="name-span">{{ item.name }}</span>
                <el-rate
                  v-model="item.rate"
                  disabled
                  show-text
                  text-color="#ff9900"
                  text-template="{value}">
                </el-rate>
                <!--先经过过滤器处理-->
                <span class="price">{{ item.price | priceFormat}}</span>
                <div class="bottom clearfix">
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
  // 这个页面是商品列表展示页面
  import productApi from '../../api/portal/productapi.js'
  import vCrumb from './../common/BreadCrumb.vue'
  import './../../../static/css/font-awesome.css'
  // 请求服务器数据
  export default {
    data () {
      return {
        list: [],
        isDefaultActive: true,
        isPriceActive: false,
        isAsc: false,
        isDesc: false
      }
    },
    created () {
      console.log('Productlist path', this.$route.path)
      console.log('Productlist params', this.$route.params)
//      parts = this.$route.path.split('/')
//      if (parts.length )
//      let categoryId =
//      let keyword = this.$route.path.split('/')[4]
      this.params = this.$route.params
      this.getData(this.params)
    },
    methods: {
      getData (params) {
        console.log('--- getData ---')
        let self = this
        productApi.getList(this, params).then((res) => {
          self.list = res.data.data.list
          console.log(self.list)
        })
      },
      sort (type) {
        if (type === 'price') {
          this.isPriceActive = true
          this.isDefaultActive = false
          if (this.isAsc === false && this.isDesc === false) {
            this.isAsc = true
            this.isDesc = false
            this.params['orderBy'] = 'price_asc'
          } else if (this.isAsc === true) {
            this.isAsc = false
            this.isDesc = true
            this.params['orderBy'] = 'price_desc'
          } else if (this.isDesc === true) {
            this.isAsc = true
            this.isDesc = false
            this.params['orderBy'] = 'price_asc'
          } else {
            this.isAsc = true
            this.isDesc = false
            this.params['orderBy'] = 'price_desc'
          }
          console.log(this.params)
          this.getData(this.params)
        } else {
          this.isPriceActive = false
          this.isDefaultActive = true
          this.isAsc = false
          this.isDesc = false
          this.params['orderBy'] = ''
          console.log(this.params)
          this.getData(this.params)
        }
      }
    },
    watch: {
      '$route' (route) {
        console.log('productlist: ', route)
        this.getData(route.params)
        // 根据 route.query重新请求数据，然后赋值给vm
      }
    },
    components: {
      vCrumb
    }
  }
</script>

<style scoped>



  .list-wrapper {
    margin: 0 auto;
    width: 1080px;
  }

  .price {
    font-size: 12px;
    color: #222;
  }

  .image {
    padding-top: 10px;
    margin: 0 auto;
    width: 56%;
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
    font-size: 13px;
    color: #333;
  }

  a {
    color: #FFFFFF;
    text-decoration: none;
  }

  .sorter-con {
    padding: 0px ;
    overflow: auto;
  }


  .sorter-con .sorter-item {
    float: left;
    display: inline-block;
    position: relative;
    padding: 0 8px;
    height: 23px;
    line-height: 23px;
    border: 1px solid #ccc;
    margin-right: -1px;
    background: #fff;
    color: #333;
    cursor: pointer;
    font-size: 13px;
  }

  .sorter-con .sorter-item.active {
    background: #c60023;
    border: 1px solid #c60023;
    color: #fff;
  }

  .fa {
    display: inline-block;
    font: normal normal normal 14px/1 FontAwesome;
    font-size: inherit;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  .sorter-con .sorter-item .fa-sort-desc {
    position: absolute;
    top: 7px;
    right: 8px;
  }

  .el-rate span {
    height: 15px;
  }

  .sorter-item i.active {
    opacity: .6;
  }
</style>
