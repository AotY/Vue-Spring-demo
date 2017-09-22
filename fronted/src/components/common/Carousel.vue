<template>
  <div class="carousel-wrapper">
    <el-carousel indicator-position="outside" height="400px">
      <!-- :key="image"-->
      <el-carousel-item v-for="image in images">
        <img v-bind:src="image.url" class="image"></img>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>


<script>
  export default {
    data () {
      return {
        images: [
          {url: '../../../static/images/1.jpg'},
          {url: '../../../static/images/2.jpg'},
          {url: '../../../static/images/3.jpg'},
          {url: '../../../static/images/4.jpg'}
        ]
      }
    }
  }
//  setActiveItem(index) {
//    this.$refs.carousel.setActiveItem(index);
//  }
</script>

<style >
  .carousel-wrapper {
    padding-top: 20px;
    /*right: auto;*/
    /*width: 80%;*/
  }

  /*.el-carousel__item:nth-child(2n) {*/
    /*background-color: #99a9bf;*/
  /*}*/

  /*.el-carousel__item:nth-child(2n+1) {*/
    /*background-color: #d3dce6;*/
  /*}*/
  .image {
    width: 100%;
    display: block;
    height: 100%;
  }


</style>
