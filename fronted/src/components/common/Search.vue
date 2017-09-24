<template>
  <div class="input-wrapper">
    <el-input
      placeholder="请输入商品名称"
      icon="search"
      v-model="input2"
      size="large"
      :on-icon-click="handleIconClick">
    </el-input>

  </div>
</template>

<script>
  export default {
    data () {
      return {
        input2: ''
      }
    },
    methods: {
      handleIconClick (ev) {
        console.log(ev)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .input-wrapper {
    padding-top: 20px;
    margin: 0 auto;
    width: 800px;
  }



</style>
