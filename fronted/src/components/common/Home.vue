<template>
  <div class="wrapper">
    <v-header></v-header>
    <div class="content">
      <transition name="move" mode="out-in">
        <router-view></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
  import vHeader from './Header.vue'

  export default {
    components: {
      vHeader
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .content {
    margin: 0px auto;
    width: 80%;
  }

</style>
