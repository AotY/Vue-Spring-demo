<template>
  <div class="page-container">

    <div class="intro-wrap clear">

      <div class="p-img-wrap">
        <div class="main-img-con">
          <img class="main-img" v-bind:src="data.imageHost + data.mainImage"></div>
        <ul class="p-img-list">
          <li class="p-img-item" v-for="item in data.subImages">
            <img class="p-img" v-bind:src="data.imageHost + item">
          </li>
        </ul>
      </div>

      <div class="p-info-wrap"><p class="p-name">{{ data.name }}</p>
        <p class="p-subtitle">{{ data.subtitle }}</p>
        <div class="info-item p-price-con">
          <span class="label">价格:</span>
          <span class="p-price">{{data.price | formatPrice }} </span>
        </div>
        <div class="info-item p-quantity-con"><span class="label">库存:</span> <span class="p-price">{{data.stock}}</span>
        </div>
        <div class="info-item">
          <span class="label">数量:</span>
          <input class="p-count" disabled v-model="count">

          <!--<span class="p-count-btn plus" data-opera-type="plus" v-on:click="count += 1">+</span>-->
          <span class="p-count-btn plus" data-opera-type="plus" v-on:click="changeCount('plus')">+</span>
          <span class="p-count-btn minus" data-opera-type="minus"  v-on:click="changeCount('minus')">-</span></div>
        <div class="info-item"><a class="btn cart-add">加入购物车</a></div>
      </div>

    </div>

    <div class="detail-wrap">
      <el-tabs v-model="activeName2" type="card" @tab-click="handleClick">
        <el-tab-pane label="商品详情" name="first">
          <img class="p-img" v-bind:src="data.imageHost + data.detail">
        </el-tab-pane>
        <el-tab-pane label="用户评价" name="second">用户评价</el-tab-pane>
      </el-tabs>
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
        data: {stock: 0},
        activeName2: 'first',
        count: 1
      }
    },
    created () {
      // 获取商品详细信息
      this.getData()
    },
    methods: {
      getData () {
        console.log('--- getData ---')
        let self = this
        console.log(this.$route.params)
        productApi.getDetail(this, this.$route.params).then((res) => {
          console.log(res.data.data)
          self.data = res.data.data
        })
      },
      handleClick (tab, event) {
        console.log(tab, event)
      },
      changeCount (type) {
        let self = this
        console.log('changeCount')
        console.log(self.data)

        if (type === 'plus') {
          if (self.count < self.data.stock) {
            self.count += 1
          }
        } else {
          if (self.count > 1) {
            self.count -= 1
          }
        }
      }
    },
    components: {
      vCarousel
    },
    filters: {
      formatPrice: function (price) {
        if (!price) return ''
        price = price.toString()
        return '￥' + parseFloat(price).toFixed(2)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .page-container {
    width: 1080px;
    margin: 0 auto;
    position: relative;
    overflow: hidden;
  }

  .intro-wrap {
    height: 500px;
  }

  .intro-wrap .p-img-wrap {
    width: 422px;
    float: left;
  }

  .intro-wrap .main-img-con {
    height: 400px;
    border: 1px solid #ddd;
  }

  .intro-wrap .main-img-con .main-img {
    width: 90%;
    height: 100%;
  }

  img {
    margin: 0px auto;
    display: block;
    border: none;
  }

  li {
    list-style: none;
  }

  .intro-wrap .p-img-list {
    margin: 0px;
    padding: 0px;
    height: 78px;
    margin-top: 5px;
    margin-right: -10px;
    overflow: hidden;
  }

  .intro-wrap .p-img-list .p-img-item {
    width: 74px;
    height: 74px;
    float: left;
    margin-right: 10px;
    border: 2px solid #eee;
    cursor: pointer;
  }

  .intro-wrap .p-img-list .p-img-item .p-img {
    width: auto;
    height: auto;
    width: 90%;
    height: 100%;
  }

  .intro-wrap .p-info-wrap {
    text-align: left;
    margin-left: 420px;
    padding: 0 15px;
  }

  .intro-wrap .p-info-wrap .p-name {
    font-size: 20px;
    color: #333;
  }

  .intro-wrap .p-info-wrap .p-subtitle {
    color: #c60023;
    line-height: 30px;
    font-size: 15px;
  }


  .p-price-con {
    padding: 20px;
    background: #eee;
    margin-top: 20px;

  }

  .p-price-con span{
    padding: 10px;
  }

  .intro-wrap .p-info-wrap .label {
    display: inline-block;
    width: 50px;
    color: #999;
  }

  .intro-wrap .p-info-wrap .p-price-con .p-price {
    font-size: 18px;
    color: #c60023;
  }

  .intro-wrap .p-info-wrap .info-item {
    position: relative;
    margin-top: 20px;
    padding: 0 10px;
  }

  .intro-wrap .p-info-wrap .label {
    display: inline-block;
    width: 50px;
    color: #999;
  }

  .intro-wrap .p-info-wrap .info-item {
    position: relative;
    margin-top: 20px;
    padding: 0 10px;
  }

  .intro-wrap .p-info-wrap .label {
    display: inline-block;
    width: 50px;
    color: #999;
  }

  .intro-wrap .p-info-wrap .p-count {
    height: 36px;
    line-height: 36px;
    font-size: 15px;
    width: 45px;
    text-align: center;
    outline: none;
    border: 1px solid #aaa;
  }

  .intro-wrap .p-info-wrap .p-count-btn {
    position: absolute;
    left: 115px;
    display: block;
    width: 20px;
    height: 15px;
    line-height: 15px;
    font-size: 10px;
    text-align: center;
    border: 1px solid #aaa;
    background: #fff;
    cursor: pointer;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }

  .intro-wrap .p-info-wrap .p-count-btn.plus {
    top: 0;
  }

  .intro-wrap .p-info-wrap .p-count-btn.minus {
    bottom: 0;
  }

  .intro-wrap .p-info-wrap .info-item {
    position: relative;
    margin-top: 20px;
    padding: 0 10px;
  }

  .btn {
    display: inline-block;
    padding: 0 20px;
    height: 40px;
    line-height: 40px;
    vertical-align: middle;
    border: none;
    background-color: #c60023;
    font-size: 17px;
    font-weight: 700;
    color: #fff;
    outline: none;
    cursor: pointer;
    text-decoration: none;
  }


  .detail-wrap .detail-con {
    padding-top: 20px;
    text-align: center;
    background: #fff;
  }



</style>
