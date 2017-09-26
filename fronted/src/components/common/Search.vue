<template>
  <div class="input-wrapper">
    <el-input
      placeholder="请输入商品名称"
      icon="search"
      v-model="keyword"
      size="large"
      :on-icon-click="handleIconClick"
      @keyup.enter.native="handleIconClick">
    </el-input>

  </div>
</template>

<script>
  export default {
    data () {
      return {
        keyword: ''
      }
    },
    methods: {
      handleIconClick (ev) {
        console.log('handleIconClick')
        console.log(this.keyword)
        console.log(ev)
        this.$router.push('/list/keyword/' + this.keyword)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .input-wrapper {
    margin: 0 auto;
    margin-top: 20px;
    width: 800px;
  }

</style>
