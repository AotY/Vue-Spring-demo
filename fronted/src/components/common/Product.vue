<template>
  <div>
      <!--面包屑-->
      <v-crumb></v-crumb>
      <!--商品列表页面和商品详情页面-->
      <router-view></router-view>
  </div>

</template>

<script>
  // 这个页面是商品列表展示页面
  import vCrumb from './BreadCrumb.vue'
  // 请求服务器数据
  export default {
    components: {
      vCrumb
    }
  }
</script>

<style scoped>


</style>
