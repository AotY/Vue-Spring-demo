<template>
  <div class="site-nav-wrapper">
    <div class="user-nav-wrapper">

      <div class="user-info">

        <span class="site-user" v-bind:class="{active: isLoginActive}">
          <a href="#/login"><span class="link link-login">登录</span></a>
          <a href="#/register"><span class="link link-register">注册</span></a>
        </span>

        <span class="site-user" v-bind:class="{active: isUserActive}">
          <span class="link-text">欢迎，
            <span class="username">{{ username }}</span>
          </span>
            <span class="link link-logout" v-on:click="logout">退出</span>
        </span>
      </div>

      <ul class="nav-list">
        <li class="nav-item">
          <a class="link" href="#/cart">
            <i class="fa fa-shopping-cart">
              <el-badge class="cart-badge" v-bind:value="count">
                <span class="cart-count">购物车</span>
              </el-badge>
            </i>
          </a>
        </li>

        <li class="nav-item">
          <a class="link" href="#/order-list">我的订单</a>
        </li>
        <li class="nav-item">
          <a class="link" href="#/user-center">个人中心</a>
        </li>
        <li class="nav-item">
          <a class="link" href="#/about">关于</a>
        </li>
      </ul>

    </div>
  </div>

</template>

<script>
  import userApi from './../../api/portal/userapi.js'
  import cartApi from './../../api/portal/cartapi.js'
  import userSession from '../../util/usersession.js'
  export default {
    data () {
      return {
        count: 0,
        username: '',
        isLoginActive: true,
        isUserActive: false
      }
    },
    created () {
      // 购物车数量
      this.getCartCount()
    },
    methods: {
      logout () {
        console.log('logout')
        let self = this
        userApi.logout(self).then(function (response) {
          console.log(response.data)
          if (response.data.status === 0) {
            userSession.removeSession()

            self.$message({
              message: '注销成功',
              type: 'success'
            })
            // 重新加载页面
            self.$router.go({
              path: '/',
              force: true
            })
          }
        })
      },
      getCartCount () {
        let self = this
        cartApi.getQuantitySum(self).then(function (response) {
          console.log('getCartCount:', response.data)
          if (response.data.status === 0) {
            self.count = response.data.data
            var username = localStorage.getItem('username')
            console.log('username:', username)
            if (username === undefined || username === null) {
              self.isLoginActive = true
              self.isUserActive = false
            } else {
              self.username = username
              self.isLoginActive = false
              self.isUserActive = true
            }
          } else {
            self.count = 0
            userSession.removeSession()
          }
        })
      }
    },
    watch: {
      '$route' (route) {
        console.log('getCartCount: ', route)
        // 购物车数量
      }
    }
  }

</script>

<style scoped>

  .site-nav-wrapper {
    background-color: #eee;
    line-height: 30px;
    width: 100%;
    height: 30px;
  }

  .user-nav-wrapper {
    /*border: 2px red solid;*/
    width: 1080px;
    margin: 0 auto;
    height: 30px;
  }

  .site-nav-wrapper .user-info {
    float: left;
  }

  .site-nav-wrapper .nav-list {
    /*原来是margin问题，Chrome调试确实强大*/
    margin: 0px;
    float: right;
  }

  .site-nav-wrapper .link {
    text-decoration: none;
    color: #777;
    font-size: 12px;
  }

  .site-nav-wrapper .nav-list .nav-item {
    margin: 0 5px;
    display: inline-block;
    list-style: none;
  }

  .site-nav-wrapper .link {
    text-decoration: none;
    color: #999;
  }

  .fa {
    display: inline-block;
    font: normal normal normal 14px/1 FontAwesome;
    font-size: inherit;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  .link-text {
    color: #777;
    text-decoration: none;
  }

  .site-user.active {
    display: inline;
  }

  .site-user {
    display: none;
  }


  .link:hover {
    color: #c60023;
  }

  .link {
    cursor: pointer;
  }

  .cart-count {
    margin-top: 0px;
    margin-right: 6px;
  }

  a {
    text-decoration: none;
  }
</style>
