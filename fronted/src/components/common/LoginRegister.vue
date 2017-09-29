<template>
  <div class="login-wrapper">
    <div class="header-simple">
      <h1>
        <a href="/">
          <img class="nav-logo" src="../../../static/images/vue-spring-demo.png"/>
        </a>
      </h1>
    </div>
    <transition name="move" mode="out-in">
      <router-view></router-view>
    </transition>
    <v-footer></v-footer>
  </div>
</template>

<script>
  import vFooter from './Footer.vue'
  export default {
    components: {
      vFooter
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .login-wrapper {
    width: 100%;
    height: 70px;
    line-height: 60px;
    border-bottom: 1px solid #ddd;
    background: #fff;
  }

  .header-simple {
    width: 1080px;
    margin: 0 auto;
    position: relative;
    overflow: hidden;
  }

  .nav-logo {
    float: left;
    margin-bottom: 10px;
    vertical-align: sub;
    height: 40px;
    width: 150px;
  }
</style>
