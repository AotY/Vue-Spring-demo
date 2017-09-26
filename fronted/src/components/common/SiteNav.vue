<template>
  <div class="site-nav-wrapper">
    <div class="user-nav-wrapper">
      <div class="user-info">
        <span class="site-user not-login">
          <span class="link link-login">登录</span>
          <span class="link link-register">注册</span>
        </span>
        <span class="site-user login">
          <span class="link-text">欢迎，
            <span class="username"></span></span>
            <span class="link link-logout">退出</span>
          </span>
      </div>

      <ul class="nav-list">
        <li class="nav-item">
          <a class="link" href="./cart.html">
            <i class="fa fa-shopping-cart">
            </i> 购物车(<span class="cart-count">0</span>)</a>
        </li>

        <li class="nav-item">
          <a class="link" href="./order-list.html">我的订单</a>
        </li>
        <li class="nav-item"><a class="link" href="./user-center.html">个人中心</a>
        </li>
        <li class="nav-item">
          <a class="link" href="./about.html">关于</a>
        </li>
      </ul>
    </div>
  </div>

</template>

<script>


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

  .site-nav-wrapper .site-user {
    display: none;
    /*display: inline;*/
  }

  .site-nav-wrapper .not-login {
    display: inline;
    /*display: none;*/
  }

  .link:hover {
    color: #c60023;
  }

  .link {
    cursor: pointer;
  }


</style>
