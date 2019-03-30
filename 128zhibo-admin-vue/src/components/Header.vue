<template>
<div>
  <div class="row top-bar">
    <div class="col-md-12"><h1>80直播吧后台管理系统</h1></div>
  </div>
  <div class="row user-bar">
    <div class="col-md-12" v-if="mobile">
      <span class="pull-right">欢迎你，{{ mobile }}
      <a href="javascript:void(0)" v-on:click="href('/resetPassword')">修改密码</a>
      <a href="javascript:void(0)" v-on:click="logout">退出登录</a>
      </span>
    </div>
  </div>
</div>
</template>

<script>
import g from '../global.js'
import Vue from 'vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)

export default {
  name: 'Header',
  data () {
    return {}
  },
  methods: {
    href: function (href) {
      g.href(href)
    },
    logout: function () {
      g.toLogin()
    }
  },
  created () {
    // ...
  },
  computed: {
    mobile: function () {
      return localStorage.getItem('mobile')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.top-bar{
  background-color: #212121;
  color: #fff;
  font-size: 20px;
  line-height: 40px;
  padding: 10px 0 20px 0;
}
.user-bar{
  background-color: #212121;
  color: #fff;
  font-size: 20px;
  padding: 10px 0 20px 0;
}
.user-bar a{
  color: red;
}
</style>
