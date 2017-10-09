<template>

  <div class="order-wrapper">
    <div>
      <div class="panel">
        <h3 class="product-info">订单信息</h3>
        <div class="panel-body">
          <div class="order-info">

            <div class="text-line">
              <span class="text">订单号：{{orderDetail.orderNo}}</span>
              <span class="text">创建时间：{{orderDetail.createTime}}</span>
            </div>
            <div class="text-line">
              <span class="text">收件人：{{orderDetail.receiverName}}  </span>
            </div>
            <div class="text-line">
              <span class="text">订单状态：{{orderDetail.statusDesc}}</span>
            </div>
            <div class="text-line">
            <span class="text">支付方式：{{orderDetail.paymentTypeDesc}}
            </span>
            </div>

            <div>
              <div class="text-line" v-if="orderDetail.status === 10">
                <a class="btn btn-mini" v-bind:href="'#/payment/orderNo/' + orderDetail.orderNo">去支付</a>
                <a class="btn btn-mini order-cancel" v-on:click="cancel">取消订单</a>
              </div>

              <div class="text-line" v-else-if="orderDetail.status === 0">
                <a class="btn btn-mini order-cancel" v-on:click="deleteOrder">删除订单</a>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>

    <h3 class="product-info">商品信息</h3>

    <div class="panel">
      <table class="product-table">
        <tbody>
        <tr>
          <th width="10%">&nbsp;</th>
          <th width="30%" align="left">商品描述</th>
          <th width="20%" align="center">价格</th>
          <th width="20%" align="center">数量</th>
          <th width="20%" align="center">小计</th>
        </tr>
        <tr v-for="orderItem in orderDetail.orderItemVoList">

          <td class="product-cell cell-img">
            <a v-bind:href="'#/detail/productId/' + orderItem.productId" target="_blank">
              <img class="p-img" v-bind:src="orderDetail.imageHost + orderItem.productImage"
                   v-bind:alt="orderItem.productSubtitle"></a>
          </td>
          <td align="left">
            <a class="link p-name" v-bind:href="'#/detail/productId/' + orderItem.productId"
               target="_blank">{{orderItem.productName}}</a>
          </td>

          <td align="center">{{ orderItem.currentUnitPrice | priceFormat }}</td>

          <td align="center">{{ orderItem.quantity }}</td>

          <td align="center">{{ orderItem.totalPrice | priceFormat }}</td>
        </tr>
        </tbody>
      </table>

      <div class="total-wrapper">
        <p class="total pull-right">
          <span>订单总价：</span>
          <span class="enhance">￥17997</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
  // 请求服务器数据
  import orderApi from './../../api/portal/orderapi.js'

  export default {
    data () {
      return {
        orderDetail: {},
        orderNo: ''
      }
    },
    created () {
      this.$store.dispatch('updateBreadcrumbs', this)
      var orderNo = this.$route.params['orderNo']
      console.log('----orderNo----', orderNo)
      this.orderNo = orderNo
      this.getOrderDetail(orderNo)
    },
    methods: {
      getOrderDetail (orderNo) {
        let self = this
        orderApi.getDetail(self, orderNo).then(function (response) {
          console.log('-----getOrderDetail------: ', response.data)
          if (response.data.status === 0) {
            self.orderDetail = response.data.data
          } else if (response.data.status === 3) {
            self.$router.push('/login')
          }
        })
      },
      cancel () {
        let self = this
        orderApi.cancel(self, self.orderNo).then(function (response) {
          if (response.data.status === 0) {
            self.$message({
              message: '取消成功',
              type: 'success'
            })
            self.getOrderDetail(self.orderNo)
          } else if (response.data.status === 3) {
            self.$message({
              message: response.data.msg,
              type: 'error'
            })
          }
        })
      },
      deleteOrder () {
        let self = this
        orderApi.deleteOrder(self, self.orderNo).then(function (response) {
          if (response.data.status === 0) {
            self.$message({
              message: '删除成功',
              type: 'success'
            })
            self.getOrderDetail(self.orderNo)
          } else if (response.data.status === 3) {
            self.$message({
              message: response.data.msg,
              type: 'error'
            })
          }
        })
      }
    }
  }
</script>

<style scoped>

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

  .order-wrapper {
    width: 1080px;
    margin: 0 auto;
    position: relative;
    overflow: hidden;
    margin-top: 20px !important;
    text-align: left;
  }

  .order-wrapper .panel {
    padding: 10px;
    margin-bottom: 10px;
    background: #fff;
  }

  .panel-body {
    padding: 10px 0;
    overflow: hidden;
  }

  .order-info {
    padding: 0 10px;
    font-size: 14px;
    font-weight: 400;
  }

  .order-info .text {
    margin-right: 20px;
    height: 30px;
    line-height: 30px;
    padding-top: 5px;
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

  .btn-mini {
    height: 25px;
    line-height: 25px;
    font-size: 12px;
    padding: 2px 10px;
    margin-right: 10px;
  }

  .right-con .total {
    font-size: 14px;
    line-height: 40px;
    height: 40px;
    margin-top: 10px;
    margin-right: 20px;
  }

  .enhance {
    font-weight: 700;
    color: #c60023;
  }

  .p-img {
    width: 70px;
    height: 80px;
    border: 1px solid #ddd;
  }

  .product-table th {
    font-size: 13px;
    font-weight: 500;
    height: 30px;
    line-height: 30px;
    border-bottom: 1px dotted #ddd;
  }

  .product-table td {
    border-bottom: 1px dotted #ddd;
  }

  td {
    font-size: 14px;
  }

  .link {
    font-size: 14px;
    color: #666;
    text-decoration: none;
    cursor: pointer;
  }

</style>
