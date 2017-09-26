<template>
  <div>
    <div class="list-wrapper">

      <ul class="sorter-con">
        <li class="sorter-item active" data-type="default">
          <span>推荐排序</span>
        </li>
        <li class="sorter-item" data-type="price">
          <span>价格</span>
          <i class="fa fa-sort-asc"></i>
          <i class="fa fa-sort-desc"></i>
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
                <span class="price">{{ item.price }}<span>.00 元</span></span>
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
  // 请求服务器数据
  export default {
    data () {
      return {
        list: [],
        value5: 3.7
      }
    },
    created () {
      console.log('path', this.$route.path)
      console.log('params', this.$route.params)
//      parts = this.$route.path.split('/')
//      if (parts.length )
//      let categoryId =
//      let keyword = this.$route.path.split('/')[4]
      this.getData(this.$route.params)
    },
    methods: {
      getData (params) {
        console.log('--- getData ---')
        let self = this
        productApi.getList(this, params).then((res) => {
          console.log(res.data)
          self.list = res.data.data.list
          console.log(self.list)
        })
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


</style>
