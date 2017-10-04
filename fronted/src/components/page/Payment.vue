<template>

  <!--支付页面，展示支付宝二维码-->
  <div class="wrapper">
    <el-row class="qr">
      <el-col>
        <el-card :body-style="{ padding: '50px', margin: '0px auto'}">
          <div style="padding: 14px;">
            <span>请打开支付宝进行扫描或者先 </span>
            <span><a v-bind:href="'#/order-detail/orderNo/'+ orderNo" >查看订单</a></span>
            <div class="bottom clearfix">
            </div>
          </div>
          <!--<img src="http://image.vuespringdemo.com/qr-1506955845058.png" class="image">-->
          <img v-bind:src="qrUrl" class="image">
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  // 请求服务器数据
  import orderApi from './../../api/portal/orderapi.js'

  export default {
    data () {
      return {
        orderNo: '',
        qrUrl: ''
      }
    },
    created () {
      this.$store.dispatch('updateBreadcrumbs', this)
      var orderNo = this.$route.params['orderNo']
      // 获取支付二维码
      console.log('----orderNo----', orderNo)
      this.getQRUrl(orderNo)
    },
    methods: {
      getQRUrl (orderNo) {
        let self = this
        orderApi.pay(self, orderNo).then(function (response) {
          console.log('----getQRUrl----', response.data)
          if (response.data.status === 0) {
            self.orderNo = response.data.data.orderNo
            self.qrUrl = response.data.data.qrUrl
          } else if (response.data.status === 3) {
            self.$router.push('/login')
          } else {
            self.$message({
              message: response.data.msg,
              type: 'error'
            })
//            window.history.back()
            self.$router.push({name: 'Recommend'})
          }
        })
      }
    }
  }
</script>

<style scoped>

  .wrapper {
    margin: 0px auto;
    text-align: center;
    width: 1080px;
    height: 500px;
  }

  h3 {
    font-size: 16px;
    font-weight: 700;
    line-height: 35px;
    height: 35px;
    color: #333;
    border-bottom: 1px solid #ddd;
  }

  h2:hover {
    color: #c60023;
  }

  a {

    text-decoration: none;
  }

  a:hover {
    cursor: pointer;
    color: #c60023;
  }

  .image {
    width: 300px;
    height: 300px;
  }

</style>
