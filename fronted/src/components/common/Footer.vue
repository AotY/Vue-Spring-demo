<template>
  <div class="footer-wrapper">
    <div class="footer">
    <div class="links">
      <a class="link" href="https://www.vuejs.org">Vue.js</a>
      |
      <a class="link" href="http://element.eleme.io/#/zh-CN">Element</a>
      |
      <a class="link" href="https://github.com/AotY/Vue-Spring-demo">Github</a>
    </div>
    <p class="copyright"> Copyright © 2017 电信学院科创中心 All Rights Reserved </p>
    </div>
  </div>

</template>

<script>

</script>

<style scoped>

  .footer-wrapper {
    width:100%;
    clear: right;
  }

  .footer{
    width: 1080px;
    margin: 0 auto;
  }

  .footer-wrapper .links {
    height: 30px;
    margin: 15px 0;
    text-align: center;
    color: #666;
  }

  .footer-wrapper .links .link {
    font-size: 14px;
    padding: 10px;
    text-decoration: none;
    color: #666;
  }

  .footer-wrapper .copyright {
    height: 30px;
    font-size: 14px;
    margin: 10px 0;
    text-align: center;
    color: #666;
  }
</style>

