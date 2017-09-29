<template>
  <div class="header-wrapper">
    <header class="header">
      <div class="container">
        <h1>
          <!--<a href="https://github.com/AotY/Vue-Spring-demo">-->
          <a href="/">
            <img class="nav-logo" src="../../../static/images/nav-logo.png"/>
          </a>
        </h1>
      </div>
    </header>
  </div>

</template>

<script>

</script>

<style scoped>

  h1 {
    height: 40px;
    width: 150px;
  }
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
    top: 0;
    left: 0;
    line-height: 80px;
    color: #fff;
    background-color: #c60023;
  }

  .container {
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
    margin-bottom: 10px;
    vertical-align: sub;
    height: 40px;
    width: 150px;
  }



</style>

