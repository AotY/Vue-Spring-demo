<template>
  <div class="site-nav">
    <div class="user-nav-wrapper">
      <div class="user-info">
        <!--<span class="link link-login">登录</span>-->
        <!--<span class="link link-register">注册</span>-->
      </div>
      <ul class="nav-list"></ul>
    </div>
  </div>

</template>

<script>


</script>

<style>
  .site-nav {
    background-color: #eee;
    line-height: 30px;
    width: 100%;
    height: 30px;

  }

  .user-nav-wrapper {
    /*background-color: #f7f7f7;*/
    width: 1080px;
    margin: 0 auto;
    /*overflow: hidden;*/
    /*position: relative;*/
  }

  .site-nav .user-info {
    float: left;
  }

  .site-nav .nav-list {
    float: right;
  }

  .site-nav .link {
    text-decoration: none;
    color: #999;
    font-size: 12px;
  }

</style>
