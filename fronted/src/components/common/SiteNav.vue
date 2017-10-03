<template>
  <div class="site-nav-wrapper">
    <div class="user-nav-wrapper">

      <div class="user-info">
        <!--这里应该用v-if-->
        <div v-if="isLogin == false || isLogin == undefined">
          <span class="site-user">
            <a href="#/login"><span class="link link-login">登录</span></a>
            <a href="#/register"><span class="link link-register">注册</span></a>
          </span>
        </div>

        <div v-else>
          <span class="site-user">
            <span class="link-text">欢迎，
              <span class="username">{{ username }}</span>
            </span>
              <span class="link link-logout" v-on:click="logout">退出</span>
          </span>
        </div>

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

//  import {mapGetters} from 'vuex'
//    computed: mapGetters(['username', 'count', 'isLogin']),
  export default {
    data () {
      return {
      }
    },
    created () {
      // 购物车数量
    },
    computed: {
      count () {
        console.log('computed count: ', this.$store.getters.count)
        return this.$store.getters.count
      },
      isLogin () {
        console.log('computed isLogin: ', this.$store.getters.isLogin)
        return this.$store.getters.isLogin
      },
      username () {
        console.log('computed username: ', this.$store.getters.username)
        return this.$store.getters.username
      }
    },
    methods: {
      logout () {
        console.log('logout')
        this.$store.dispatch('logout', this)
      }
    }
    // 还是应该用vuex进行状态管理,
//    watch: {
//      '$route' (route) {
//        console.log('getCartCount: ', route)
//        // 购物车数量
//      }
//    }
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
    font-size: 13px;
    color: #555;
    text-decoration: none;
  }

  .site-user.active {
    display: inline;
  }

  .site-user {
    display: inline;
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
