<template>
  <div class="content-wrapper">
    <v-header></v-header>
    <v-site-nav></v-site-nav>
    <v-search-input></v-search-input>
    <transition name="move" mode="out-in">
      <router-view></router-view>
    </transition>
    <v-footer></v-footer>
  </div>
</template>

<script>
  import vHeader from './Header.vue'
  import vSiteNav from './SiteNav.vue'
  import vSearchInput from './Search.vue'
  import vFooter from './Footer.vue'
  export default {
    components: {
      vHeader, vSiteNav, vSearchInput, vFooter
    },
    created () {
      // 更新购物车信息
      this.$store.dispatch('updateCartCount', this)
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  /*.content-wrapper {*/
    /*margin: 0 auto;*/
    /*width: 1080px;*/
  /*}*/

</style>
