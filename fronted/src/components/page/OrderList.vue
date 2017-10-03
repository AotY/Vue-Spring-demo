<template>

  <div v-if="isBlank">
    <h1>目前没有订单</h1>
    <a href="/">
      <h2>马上去购物</h2>
    </a>
  </div>

  <div class="panel-wrapper" v-else>
    <div class="panel">
      <h1 class="panel-title">我的订单</h1>

      <div class="panel-body">
        <div class="order-list">
          <div class="order-list">

            <table class="order-list-table header">
              <tbody>
              <tr>
                <th class="order-list-cell cell-img">&nbsp;</th>
                <th class="order-list-cell cell-info">商品信息</th>
                <th class="order-list-cell cell-price">单价</th>
                <th class="order-list-cell cell-count">数量</th>
                <th class="order-list-cell cell-total">合计</th>
              </tr>
              </tbody>
            </table>

            <table class="order-list-table order-item" v-for="order in data.list">
              <tbody>
              <tr>
                <td class="order-info" colspan="6">
                  <span class="order-text">
                    <span>订单号：</span>
                    <a class="link" v-bind:href="'#/order-detail/orderNo/' + order.orderNo">{{order.orderNo}}</a>
                  </span>

                  <span class="order-text">{{order.createTime}}</span>
                  <span class="order-text">
                    <span>收件人：{{order.receiverName}}</span>
                  </span>

                  <span class="order-text">
                    <span>订单状态：{{order.statusDesc}}</span>
                  </span>

                  <span class="order-text">
                    <span>订单总价：</span>
                    <span class="enhance">{{order.payment}}</span>
                    </span>
                  <a class="link pull-right" v-bind:href="'#/order-detail/orderNo/' + order.orderNo">查看详情&gt;</a>
                </td>
              </tr>

              <tr v-for="orderItem in order.orderItemVoList">
                <td class="order-list-cell cell-img">
                  <a v-bind:href="'#/detail/productId/' + orderItem.productId" target="_blank">
                    <img class="p-img" v-bind:src="order.imageHost + orderItem.productImage"
                         v-bind:alt="orderItem.productName">
                  </a>
                </td>

                <td class="order-list-cell cell-info">
                  <a class="link p-name" v-bind:href="'#/detail/productId/' + orderItem.productId"
                     target="_blank">{{orderItem.productName}}</a>
                </td>
                <td class="order-list-cell cell-price">{{orderItem.currentUnitPrice}}</td>
                <td class="order-list-cell cell-count">{{orderItem.quantity}}</td>
                <td class="order-list-cell cell-total">{{orderItem.totalPrice}}</td>
              </tr>
              </tbody>
            </table>

          </div>
        </div>
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
        data: {
          isBlank: true,
          data: {}
        }
      }
    },
    created () {
      this.$store.dispatch('updateBreadcrumbs', this)
      this.getOrderList()
    },
    methods: {
      getOrderList () {
        let self = this
        orderApi.getList(self).then(function (response) {
          console.log(response.data)
          if (response.data.status === 0) {
            self.data = response.data.data
            if (self.data.total > 0) {
              self.isBlank = false
            } else {
              self.isBlank = true
            }
          } else if (response.data.status === 3) {
            self.$router.push('/login')
          }
        })
      }
    }
  }
</script>

<style scoped>


  h2 {
    font-weight: 200;
    font-size: 26px;
    cursor: pointer;
  }

  h2:hover {
    color: #c60023;
  }

  a {
    font-size: 14px;
    color: #999;
    text-decoration: none;
    cursor: pointer;
    font-weight: 700;
  }

  .panel-wrapper {
    width: 1080px;
    margin: 0 auto;
  }

  .panel {
    padding: 10px;
    margin-bottom: 10px;
    background: #fff;
  }

  .panel-title {
    width: 100%;
    text-align: left;
    float: left;
    padding: 10px;
    font-size: 18px;
    color: #555;
    border-bottom: 1px solid #eee;
    font-weight: 700;

  }

  .panel-body {
    padding: 10px 0;
  }

  .link {
    color: #999;
    text-decoration: none;
    cursor: pointer;
  }

  .order-list-table {
    width: 100%;
    border-collapse: collapse;
    background: #fafafa;
    margin-bottom: 20px;
  }

  .order-list-table.header {
    background: #eee;
  }

  .order-list-cell {
    color: #666;
    border-bottom: 1px solid #eee;
  }

  .order-list-table .cell-img {
    width: 10%;
    padding: 10px;
  }

  .order-list-table .cell-info {
    width: 45%;
    padding: 0 10px;
    text-align: left;
  }

  .order-list-table .order-info {
    background: #eee;
    padding: 10px;
  }

  .order-list-table .cell-price {
    width: 15%;
    text-align: center;
  }

  .order-list-table .cell-count {
    width: 15%;
    text-align: center;
  }

  .order-list-table .cell-total {
    width: 15%;
    text-align: center;
  }

  .order-text {
    float: left;
  }

  .order-list-table .order-info .order-text {
    margin-right: 25px;
    color: #999;
  }

  .order-list .order-list-table .cell-img .p-img {
    width: 70px;
    height: 80px;
    border: 1px solid #ddd;
  }

  .order-list-table .order-info .link {
    font-weight: 700;
  }

  .enhance {
    font-weight: 700;
    color: #c60023;
  }

  th {
    font-size: 13px;
  }

  span {
    font-size: 11px;
  }
</style>
