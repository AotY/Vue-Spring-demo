<template>
  <div class="header-wrapper">
    <header class="header">
      <div class="container">
        <h1>
          <a>
            <img class="nav-logo" src="../../../static/images/nav-logo.png"/>
          </a>
        </h1>
        <!--<ul class="nav">-->
        <!--<li class="nav-item">-->
        <!--<a></a>-->
        <!--</li>-->
        <!--</ul>-->

      </div>
    </header>
  </div>

</template>

<script>
  export default {
    data () {
      return {
        activeIndex: '1'
      }
    },
    methods: {
      handleSelect (key, keyPath) {
        console.log(key, keyPath)
      }
    }
  }

</script>

<style>
  .header-wrapper {
    height: 60px;
  }

  .header .container {
    box-sizing: border-box;
    height: 100%;
  }
  .header {
    width: 100%;
    height: 70px;
    /*position: relative;*/
    top: 0;
    left: 0;
    line-height: 80px;
    color: #fff;
    background-color: #c60023;
  }

  .container {
    /*border: 2px red solid;*/
    width: 1080px;
    margin: 0 auto;
    padding: 0 30px;
  }

  .header h1 {
    margin: 0;
    float: left;
    left: 0px;
  }
  .header .nav-logo {

    vertical-align: sub;
    height: 40px;
    width: 150px;
  }

</style>

