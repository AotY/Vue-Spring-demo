<template>
  <div class="wrapper">
    <v-header></v-header>
    <v-site-nav></v-site-nav>
    <div class="content">
      <transition name="move" mode="out-in">
        <router-view></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
  import vHeader from './Header.vue'
  import vSiteNav from './SiteNav.vue'
  export default {
    components: {
      vHeader, vSiteNav
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .content {
    margin: 0 auto;
    width: 1080px;
  }

</style>
