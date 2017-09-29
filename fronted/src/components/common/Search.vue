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
    created () {
      console.log('search: ', this.$route.params)
      this.keyword = this.$route.params['keyword']
    },
    methods: {
      handleIconClick (ev) {
        console.log('handleIconClick')
        this.$router.push({name: 'ListKeyword', params: { keyword: this.keyword }})
//        this.$router.push('/list/keyword/' + this.keyword)
//        this.$router.go(1)
      }
    },
    watch: {
      '$route' (route) {
        console.log('search: ', route)
        this.keyword = route.params['keyword']
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
