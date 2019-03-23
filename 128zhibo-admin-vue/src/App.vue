<template>
<div class="container">
  <router-view name="header"/>
  <div class="row admin-body">
    <div class="col-md-2 menu">
      <router-view name="menu"/>
    </div>
    <div class="col-md-10 main">
      <router-view/>
    </div>
  </div>
  <router-view name="footer"/>
  <router-view name="login"/>
</div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
*{
  font-size: 12px;
}
</style>
