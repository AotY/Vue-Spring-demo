<template>
  <div>
  </div>
</template>

<script>
  // 这个页面是主要展示的页面
  import vCarousel from './../common/Carousel.vue'
  import productApi from '../../api/portal/productapi.js'
  // 请求服务器数据
  export default {
    data () {
      return {
        list: []
      }
    },
    created () {
      this.getData()
    },
    methods: {
      getData () {
        console.log('--- getData ---')
        let self = this
        productApi.getList(this, 10, 1).then((res) => {
          self.list = res.data.data.list
          console.log(self.list)
        })
      }
    },
    components: {
      vCarousel
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


</style>
