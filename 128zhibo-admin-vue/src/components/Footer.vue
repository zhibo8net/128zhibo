<template>
<div>
  <div class="row foot-bar">
    <div class="col-md-12">Copyright © 2018-2020 备案号:沪ICP备13048152号-1 友情链接AD:5583670 80直播吧 All Rights Reserved.</div>
  </div>
</div>
</template>

<script>
import Vue from 'vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)

export default {
  name: 'Footer',
  data () {
    return {}
  },
  methods: {
    // ...
  },
  created () {
    // ...
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.foot-bar{
  background-color: #3d3d3d;
  color: #fff;
  font-size: 12px;
  line-height: 30px;
  padding: 5px;
}
</style>
