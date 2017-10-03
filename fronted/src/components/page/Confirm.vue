<template>

  <!--确认订单页面，选择收货地址-->
  <div class="confirm-wrapper">

    <h3 class="title">收货地址</h3>
    <el-row :gutter="20">
      <el-col :span="5" v-for="item in shippingData">
        <!--v-bind:class="{active: item.default }"-->
        <el-card class=" shipping-card" v-bind:class="item.id == curShippingId ? 'active' : ''"
                 :body-style="{ padding: '0px', cursor: 'pointer'}">
          <!--点击位置的问题-->

          <div style="padding: 14px; height: 110px;">
            <a v-on:click="changeShipping(item.id)">
              <div class="receiver-name-wrapper">
                <span>{{ item.receiverName }}</span>
                <span>{{ item.receiverPhone }}</span>
              </div>
              <div>
                <span class="receiver-address">{{ item.receiverAddress }}</span>
              </div>

              <div>
                <span class="receiver-zip">邮编：{{ item.receiverZip }}</span>
              </div>
            </a>


            <div class="bottom clearfix">
              <el-button type="text" class="button" v-on:click="editShipping(item)">编辑</el-button>
              <el-button type="text" class="button" v-on:click="deleteShipping(item.id)">删除</el-button>
              <el-button v-if="item.default == false" type="text" class="button" v-on:click="setDefault(item.id)">
                设为默认
              </el-button>
              <el-button v-else type="text" class="button default">当前默认地址</el-button>

            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="5">
        <a v-on:click="addShipping">
          <el-card :body-style="{ padding: '0px', cursor: 'pointer'}">
            <div style="padding: 14px; text-align: center; height: 110px;">
              <i class="el-icon-plus"></i>
              <span>新增地址</span>
              <div class="bottom clearfix">
              </div>
            </div>
          </el-card>
        </a>
      </el-col>
    </el-row>


    <h3 class="product-info">商品信息</h3>
    <div class="confirm-header">

      <table class="product-table">
        <tbody>
        <tr>
          <th width="10%">&nbsp;</th>
          <th width="30%" align="left">商品描述</th>
          <th width="20%" align="center">价格</th>
          <th width="20%" align="center">数量</th>
          <th width="20%" align="center">小计</th>
        </tr>
        <tr v-for="cartProductVo in selectedProducts.cartProductVoList">

          <td class="product-cell cell-img">
            <a v-bind:href="'#/detail/productId/' + cartProductVo.productId" target="_blank">
              <img class="p-img" v-bind:src="selectedProducts.imageHost + cartProductVo.productMainImage"
                   v-bind:alt="cartProductVo.productSubtitle"></a>
          </td>
          <td align="left">
            <a class="link p-name" v-bind:href="'#/detail/productId/' + cartProductVo.productId"
               target="_blank">{{cartProductVo.productName}}</a>
          </td>

          <td align="center">{{ cartProductVo.productPrice | priceFormat }}</td>

          <td align="center">{{ cartProductVo.quantity }}</td>

          <td align="center">{{ cartProductVo.productTotalPrice | priceFormat }}</td>

        </tr>
        </tbody>
      </table>
    </div>

    <div class="product-footer">
      <div class="submit-con">
        <span>总价：</span>
        <span class="submit-total">{{ selectedProducts.cartTotalPrice | priceFormat }}</span>
        <span class="btn submit-btn" v-on:click="createOrder">提交订单</span>
      </div>
    </div>

    <!--地址对话框-->
    <el-dialog v-bind:title="dialogTitle" :visible.sync="dialogFormVisible">
      <!--不要忘记设置ref='form'-->
      <el-form :model="form" :rules="rules" ref="form" class="demo-ruleForm">
        <el-form-item label="收件人姓名" :label-width="formLabelWidth" prop="receiverName">
          <el-input type="text" class="input" v-model="form.receiverName" auto-complete="off"></el-input>
        </el-form-item>

        <!--<el-form-item label="所在城市" :label-width="formLabelWidth" prop="distpicker">-->
        <el-form-item label="所在城市" :label-width="formLabelWidth">
          <!--<v-distpicker class="input" v-model="form.distpicker" @selected="onDistpickerSelected" :province="select.province" :city="select.city" :area="select.area"></v-distpicker>-->
          <v-distpicker class="input" @selected="onDistpickerSelected" :province="select.province" :city="select.city" :area="select.area"></v-distpicker>
        </el-form-item>

        <el-form-item label="详细地址" :label-width="formLabelWidth" prop="receiverAddress">
          <el-input type="text" class="input" v-model="form.receiverAddress" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="收件人手机" :label-width="formLabelWidth" prop="receiverPhone">
          <el-input type="text" class="input" v-model.number="form.receiverPhone" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="邮编" :label-width="formLabelWidth" prop="receiverZip">
          <el-input type="text" class="input" v-model.number="form.receiverZip" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('form')" style="margin-left: 55px">{{dialogBtnName}}</el-button>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
        </el-form-item>

      </el-form>


    </el-dialog>


  </div>
</template>

<script>
  // 请求服务器数据
  import shippingApi from './../../api/portal/shippingapi.js'
  import cartApi from './../../api/portal/cartapi.js'
  import orderApi from './../../api/portal/orderapi.js'

  // 地区选择插件
  import VDistpicker from 'v-distpicker'

  export default {
    data () {
      var validateDistpicker = (rule, value, callback) => {
        if (this.distpickerSelected === '') {
          callback(new Error('请选择城市'))
        } else {
          this.form.receiverProvince = this.distpickerSelected.province.value
          this.form.receiverCity = this.distpickerSelected.city.value
          this.form.receiverDistrict = this.distpickerSelected.area.value
        }
      }
      return {
        shippingData: {},
        selectedProducts: [],
        curShippingId: 0,
        shippingIdInit: true,
        dialogFormVisible: false,
        distpickerSelected: '',
        editType: '',
        dialogTitle: '',
        dialogBtnName: '',
        shipingId: '',
        form: {
          id: '',
          receiverName: '',
          receiverPhone: '',
          receiverProvince: '',
          receiverCity: '',
          receiverDistrict: '',
          receiverZip: '',
          receiverAddress: ''
//          distpicker: ''
        },
        select: { province: '', city: '', area: '' },
        rules: {
          receiverName: [
            {required: true, message: '请输入收件人姓名', trigger: 'blur'}
          ],
          receiverPhone: [
            {required: true, message: '请输入收件人手机'}
//            {type: 'number', message: '手机号码必须为数字值'}
          ],
          receiverAddress: [
            {required: true, message: '请输入详细地址', trigger: 'blur'}
          ],
          distpicker: [
            {required: true, validator: validateDistpicker, trigger: 'blur'}
          ],
          receiverZip: [
//            {type: 'number', message: '邮票必须为数字', trigger: 'blur'}
          ]
        },
        formLabelWidth: '100px'
      }
    },
    created () {
      this.$store.dispatch('updateBreadcrumbs', this)
      this.getShippingData()
      this.getSelectedProducts()
    },
    methods: {
      getShippingData () {
        let self = this
        console.log('-----self.curShippingId------: ', self.curShippingId)
        shippingApi.getList(self).then(function (response) {
          console.log(response.data)
          if (response.data.status === 0) {
            self.shippingData = response.data.data
            // 第一次初始化收货地址
            if (self.shippingIdInit) {
              var i; var item
              for (i in self.shippingData) {
                item = self.shippingData[i]
                if (item.default) {
                  console.log('-----item.id------: ', item.id)
                  self.curShippingId = item.id
                  self.shippingIdInit = false
                  break
                }
              }
            }
          } else if (response.data.status === 3) {
            self.$router.push('/login')
          }
        })
      },
      // 获取当前购物车中checked（勾选）的商品
      getSelectedProducts () {
        let self = this
        cartApi.getSelectedProducts(self).then(function (response) {
          console.log('getSelectedProducts：', response.data)
          if (response.data.status === 0) {
            self.selectedProducts = response.data.data
          }
        })
      },
      addShipping (shipping) {
        // 打开对话框
        console.log('------addShipping--------')
        this.editType = 'add'
        this.dialogTitle = '使用新地址'
        this.dialogBtnName = '新增地址'
        this.dialogFormVisible = true
      },
      // 设置默认地址
      setDefault (id) {
        let self = this
        shippingApi.setDefault(self, id).then(function (response) {
          if (response.data.status === 0) {
            self.$message({
              message: '设置成功',
              type: 'success'
            })
            self.getShippingData()
          }
        })
      },
      // 编辑地址
      editShipping (shipping) {
        this.editType = 'edit'
        this.dialogTitle = '编辑地址'
        this.dialogBtnName = '确认修改'
        this.distpickerSelected = {province: {value: shipping.receiverProvince}, city: {value: shipping.receiverCity}, area: {value: shipping.receiverDistrict}}

        this.form.id = shipping.id
        this.form.receiverName = shipping.receiverName
        this.form.receiverPhone = shipping.receiverPhone
        this.form.receiverProvince = shipping.receiverProvince
        this.form.receiverCity = shipping.receiverCity
        this.form.receiverDistrict = shipping.receiverDistrict

        this.select.province = shipping.receiverProvince
        this.select.city = shipping.receiverCity
        this.select.area = shipping.receiverDistrict

        this.form.receiverZip = shipping.receiverZip
        this.form.receiverAddress = shipping.receiverAddress
        this.dialogFormVisible = true

        // 设置数据
      },
      // 删除地址
      deleteShipping (id) {
        let self = this
        self.openMsgBox().then(action => {
          if (action === 'confirm') {
            shippingApi.delete(self, id).then(function (response) {
              if (response.data.status === 0) {
                self.getShippingData()
              }
            })
          }
        })
      },
      // 改变当前地址
      changeShipping (id) {
        this.curShippingId = id
        console.log('-----self.curShippingId------: ', this.curShippingId)
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
      // 当选择地址Distpicker时
      onDistpickerSelected (data) {
        this.distpickerSelected = data
        console.log('------onDistpickerSelected-----', data)
      },
      // 新增地址或者修改地址对话框
      submitForm (formName) {
        console.log('----submitForm----', this.form)
        // 对话框确认
        let self = this
        this.$refs[formName].validate((valid) => {
          console.log('----valid----', valid)
          if (valid) {
            if (self.editType === 'add') {
              console.log('----add----')
              shippingApi.add(self, self.form).then(function (response) {
                if (response.data.status === 0) {
                  self.dialogFormVisible = false
                  self.$message({
                    message: '新增成功',
                    type: 'success'
                  })
                  self.getShippingData()
                  console.log('-----submitForm-----: ', response)
                }
              })
            } else {
              console.log('----edit----')
              shippingApi.edit(self, self.form).then(function (response) {
                console.log('-----edit-response----: ', response.data)
                if (response.data.status === 0) {
                  self.dialogFormVisible = false
                  self.$message({
                    message: '修改成功',
                    type: 'success'
                  })
                  self.getShippingData()
                }
              })
            }
          } else {
            console.log('----no valid----')
          }
        })
      },
      createOrder () {
        // 创建订单，跳转到支付页面
        let self = this
        orderApi.create(self, self.curShippingId).then(function (response) {
          console.log('---- createOrder')
          if (response.data.status === 0) {
            self.$router.push('/payment/orderNo/' + response.data.data.orderNo)
          } else {
            self.$message({
              message: response.data.msg,
              type: 'error'
            })
          }
        })
      }
    },
    components: {
      VDistpicker
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

  .confirm-wrapper {
    width: 1080px;
    margin: 0 auto;
    position: relative;
    overflow: hidden;
    margin-top: 20px !important;
    text-align: left;
  }

  .shipping-card.active {
    border: 2px dashed #c60023;
  }

  .product-info {
    margin-top: 30px;
  }

  .product-table {
    width: 100%;
    margin-top: 20px;
    border-collapse: collapse;
    margin-bottom: 10px;
  }

  .p-img {
    padding: 5px;
    width: 80px;
    height: 90px;
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

  .el-icon-plus {
    margin-top: 45px;
    width: 100%;

  }

  .receiver-name-wrapper {
    width: 100%;
    border-bottom: 1px solid #ddd;
  }

  .receiver-name-wrapper span {
    font-size: 14px;
    font-weight: 500;
  }

  .receiver-address {
    padding-top: 3px;
    font-size: 12px;
    color: #333;
  }

  .receiver-zip {
    padding-top: 3px;
    font-size: 12px;
    color: #666;
  }

  .button {
    font-size: 13px;
  }

  .product-footer {
    height: 50px;
    background-color: #eee;
    text-align: center;
  }

  .submit-con {
    float: right;
  }

  .submit-con span {
    font-weight: 500;
  }

  .submit-total {
    font-size: 18px;
    color: #c60023;
    font-weight: 700;
    margin-right: 30px;
    vertical-align: middle;
  }

  .default {
    color: #333333;
  }

  .demo-ruleForm .input {
    width: 330px;
  }


</style>
