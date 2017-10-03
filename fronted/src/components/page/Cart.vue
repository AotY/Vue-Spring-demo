<template>

  <div v-if="isBlank">
    <h1>购物车空空如也</h1>
    <a href="/">
      <h2>马上去购物</h2>
    </a>
  </div>

  <div class="confirm-wrapper" v-else>
    <div class="confirm-header">
      <table class="cart-table">
        <tbody>
        <tr>
          <th class="cart-cell cell-check">
            <label>
              <el-checkbox @change=changeAllSelect(data.allChecked) class="cart-select-all" v-model="data.allChecked" >
                全选
              </el-checkbox>
            </label>
          </th>
          <th class="cart-cell cell-info">商品信息</th>
          <th class="cart-cell cell-price">单价</th>
          <th class="cart-cell cell-count">数量</th>
          <th class="cart-cell cell-total">合计</th>
          <th class="cart-cell cell-opera">操作</th>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="cart-list">
      <table class="cart-table" data-product-id="32" data-checked="1">
        <tbody>
        <tr v-for="cartProductVo in data.cartProductVoList">
          <td class="cart-cell cell-check">
            <el-checkbox class="cart-select" v-model="cartProductVo.productChecked" @change=changeSelect(cartProductVo.productId,cartProductVo.productChecked)></el-checkbox>
          </td>

          <td class="cart-cell cell-img">
            <a v-bind:href="'#/detail/productId/' + cartProductVo.productId" target="_blank">
            <img class="p-img" v-bind:src="data.imageHost + cartProductVo.productMainImage"
                 v-bind:alt="cartProductVo.productSubtitle"></a>
          </td>

          <td class="cart-cell cell-info">
            <a class="link p-name" v-bind:href="'#/detail/productId/' + cartProductVo.productId" target="_blank">{{cartProductVo.productName}}</a>
          </td>

          <td class="cart-cell cell-price">{{ cartProductVo.productPrice | priceFormat }}</td>

          <td class="cart-cell cell-count">
            <span class="count-btn" data-opera-type="minus" v-on:click="changeCount(cartProductVo, 'minus')">-</span>
            <el-input class="count-input" v-model="cartProductVo.quantity" @keyup.enter.native="handleInputCount(cartProductVo)"></el-input>
            <span class="count-btn" data-opera-type="plus" v-on:click="changeCount(cartProductVo, 'plus')">+</span>
          </td>

          <td class="cart-cell cell-total">{{ cartProductVo.productTotalPrice | priceFormat }}</td>
          <td class="cart-cell cell-opera">
            <a class="link cart-delete" v-on:click="deleteProduct(cartProductVo.productId)">删除</a>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="cart-footer">

      <div class="select-con">
        <label>
          <el-checkbox class="cart-select-all" v-model="data.allChecked" @change=changeAllSelect(data.allChecked)>全选</el-checkbox>
        </label>
      </div>

      <div class="delete-con">
        <a class="cart-delete-seleced link" v-on:click="deleteSelect">
          <i class="el-icon-delete" aria-hidden="true"></i>
          <span>删除选中</span>
        </a>
      </div>

      <div class="submit-con">
        <span>总价：</span>
        <span class="submit-total">{{ data.cartTotalPrice | priceFormat }}</span>
        <!--<a href="#/confirm"><span class="btn submit-btn">去结算</span></a>-->
        <a v-on:click="confirm(data)"><span class="btn submit-btn">去结算</span></a>
      </div>
    </div>

  </div>
</template>

<script>
  // 请求服务器数据
  import cartApi from './../../api/portal/cartapi'
  export default {

    data () {
      return {
        data: {},
        isBlank: true
      }
    },
    created () {
      this.$store.dispatch('updateBreadcrumbs', this)
      this.getCartData()
    },
    methods: {
      getCartData () {
        let self = this
        cartApi.getList(self).then(function (response) {
          console.log(response.data)
          if (response.data.status === 0) {
            self.data = response.data.data
            console.log('self.data.cartProductVoList.length: ', self.data.cartProductVoList.length)
            if (self.data.cartProductVoList.length > 0) {
              self.isBlank = false
            } else {
              self.isBlank = true
            }
          } else if (response.data.status === 3) {
            self.$router.push('/login')
          }
        })
      },
      // 全选，取消全选
      changeAllSelect (allChecked) {
        console.log('----changeAllSelect----', allChecked)
        let self = this
        // 反转全选
        cartApi.reverseAllSelect(self, allChecked).then(function (response) {
          console.log(response.data)
          if (response.data.status === 0) {
            self.getCartData()
            // 重新加载页面
//            self.$router.go({
//              path: '/',
//              force: true
//            })
          }
        })
      },
      changeSelect (productId, productChecked) {
        console.log('----changeSelect----', productId, productChecked)
        let self = this
        cartApi.reverseSelect(self, productId, productChecked).then(function (response) {
          console.log(response.data)
          if (response.data.status === 0) {
            self.getCartData()
          }
        })
      },
      changeCount (cartProductVo, type) {
        console.log(cartProductVo, type)
        let self = this
        // 转为int
        cartProductVo.quantity = parseInt(cartProductVo.quantity)
        if (type === 'plus') {
          if (cartProductVo.quantity < cartProductVo.productStock) {
            cartProductVo.quantity += 1
          } else {
            cartProductVo.quantity = cartProductVo.productStock
          }
        } else {
          if (cartProductVo.quantity > 1) {
            cartProductVo.quantity -= 1
          } else {
            cartProductVo.quantity = 1
          }
        }
        console.log('cartProductVo.quantity: ', cartProductVo.quantity)
        cartApi.changeQuantity(self, cartProductVo.productId, cartProductVo.quantity).then(function (response) {
          console.log(response.data)
          if (response.data.status === 0) {
            self.getCartData()
          }
        })
      },
      handleInputCount (cartProductVo) {
        let self = this
        // 检查输入是否合法，为数值，同时在范围之内
        var quantity = parseInt(cartProductVo.quantity, 10)
        console.log(quantity)
        if (isNaN(quantity)) {
          self.$message({
            message: '输入数量非法',
            type: 'success'
          })
        }
        // 如果为数值
        if (quantity > 0 && quantity < cartProductVo.productStock) {
          cartProductVo.quantity = quantity
          cartApi.changeQuantity(self, cartProductVo.productId, cartProductVo.quantity).then(function (response) {
            if (response.data.status === 0) {
              self.getCartData()
            }
          })
        }
      },
      openMsgBox () {
        const h = this.$createElement
        return this.$msgbox({
          title: '提示',
          message: h('p', null, [
            h('span', null, '确定要删除吗？')
          ]),
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        })
      },
      // 删除指定商品
      deleteProduct (productId) {
        console.log('productId: ', productId)
        let self = this
        self.openMsgBox().then(action => {
          console.log('action: ', action)
          if (action === 'confirm') {
            // 删除选中商品
            cartApi.delete(self, productId).then(function (response) {
              self.$message({
                message: response.data.msg,
                type: 'success'
              })
              if (response.data.status === 0) {
                self.getCartData()
              } else if (response.data.status === 3) {
                self.$router.push('/login')
              } else {
                self.$message({
                  message: response.data.msg,
                  type: 'error'
                })
              }
            })
          }
        })
      },
      deleteSelect () {
        let self = this
        self.openMsgBox().then(action => {
          console.log('action: ', action)
          if (action === 'confirm') {
            cartApi.deleteSelect(self).then(function (response) {
              if (response.data.status === 0) {
                self.$message({
                  message: response.data.msg,
                  type: 'success'
                })
                self.getCartData()
              } else if (response.data.status === 3) {
                self.$router.push('/login')
              } else {
                self.$message({
                  message: response.data.msg,
                  type: 'error'
                })
              }
            })
          }
        })
      },
      confirm (data) {
        // 如果没有选择商品，则不跳转到confirm页面
        var i
        var isGoOn = false
        for (i in data.cartProductVoList) {
          isGoOn = data.cartProductVoList[i].productChecked
        }
        if (isGoOn) {
          this.$router.push('/confirm')
        } else {
          this.$message({
            message: '没有选择商品',
            type: 'error'
          })
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


  h1 {
    font-weight: 200;
    font-size: 38px;
  }

  h2 {
    font-weight: 200;
    font-size: 26px;
    cursor: pointer;
  }

  h2:hover {
    color: #c60023;
  }

  a {
    text-decoration: none;
  }


  .confirm-wrapper {
    width: 1080px;
    margin: 0 auto;
    position: relative;
    overflow: hidden;
    margin-top: 20px !important;
    text-align: left;
  }

  .confirm-wrapper .cart-table {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid #ebebeb;
    margin-bottom: 10px;
  }

  .confirm-wrapper .confirm-header {
    background-color: #eee;
  }

  tbody {
    display: table-row-group;
    vertical-align: middle;
    border-color: inherit;
  }

  label {
    cursor: pointer;
  }

  input {
    cursor: pointer;
  }

  tr {
    display: table-row;
    vertical-align: inherit;
    border-color: inherit;
  }

  span {
    font-size: 14px;
  }

  th {
    font-size: 14px;
  }

  td {
    font-size: 14px;
  }


  .confirm-wrapper .confirm-header .cart-cell {
    height: 40px;
    line-height: 40px;
  }

  .confirm-wrapper .cart-table .cell-check {
    width: 30px;
    padding-left: 20px;
    text-align: left;
  }

  .confirm-wrapper .confirm-header .cell-check {
    width: 130px;
  }

  .confirm-wrapper .cart-table .cell-info {
    width: 400px;
    padding: 0 10px;
  }

  .confirm-wrapper .cart-table .cell-price {
    width: 100px;
    text-align: center;
  }

  .confirm-wrapper .cart-table .cell-count {
    width: 200px;
    text-align: center;
  }

  .confirm-wrapper .cart-table .cell-total {
    width: 100px;
    text-align: center;
  }

  .confirm-wrapper .cart-table .cell-opera {
    width: 110px;
    text-align: center;
  }

  .p-img {
    width: 80px;
  }

  .confirm-wrapper .cart-list .cart-table .cell-img {
    width: 80px;
    padding: 10px;
  }

  .confirm-wrapper .cart-table .cell-info {
    width: 400px;
    padding: 0 10px;
  }

  .confirm-wrapper .cart-table .cell-info .p-name {
    font-size: 12px;
    line-height: 18px;
  }

  .confirm-wrapper .cart-table .cell-price {
    width: 100px;
    text-align: center;
  }

  .confirm-wrapper .cart-table .cell-count {
    width: 200px;
    text-align: center;
  }

  .confirm-wrapper .cart-table .cell-count .count-btn {
    display: inline-block;
    width: 20px;
    height: 34px;
    line-height: 28px;
    border: 1px solid #ddd;
    vertical-align: middle;
    cursor: pointer;
    background: #fff;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }

  .confirm-wrapper .cart-table .cell-count .count-input {
    width: 60px;
    text-align: center;
  }

  .confirm-wrapper .cart-list .cart-table .cell-total {
    color: #000;
    font-weight: 700;
  }

  .confirm-wrapper .cart-table .cell-total {
    width: 100px;
    text-align: center;
  }

  .cart-wrap .cart-table .cell-opera {
    width: 110px;
    text-align: center;
  }

  .link {
    color: #999;
    text-decoration: none;
    cursor: pointer;
  }


  .confirm-wrapper .cart-footer {
    height: 50px;
    margin-top: 10px;
    position: relative;
    line-height: 50px;
    background: #eee;
  }

  .confirm-wrapper .cart-footer .select-con {
    float: left;
    padding-left: 20px;
  }

  .confirm-wrapper .cart-footer .delete-con {
    float: left;
    margin-left: 20px;
  }

  .link {
    color: #999;
    text-decoration: none;
    cursor: pointer;
  }

  .confirm-wrapper .cart-footer .submit-con {
    float: right;
  }


  .confirm-wrapper .cart-footer .submit-con .submit-total {
    font-size: 18px;
    color: #c60023;
    font-weight: 700;
    margin-right: 30px;
    vertical-align: middle;
  }

  .cart-wrap .cart-footer .submit-con .submit-btn {
    height: 50px;
    line-height: 50px;
  }

  .btn {
    display: inline-block;
    padding: 0 20px;
    height: 50px;
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


</style>
