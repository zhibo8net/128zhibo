<template>
  <div>
    欢迎使用后台管理系统
  </div>
</template>

<script>
import g from '../global.js'
import Vue from 'vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)

export default {
  name: 'Index',
  data () {
    return {
      liveSources: []
    }
  },
  methods: {
    do_load: function (id) {
      var that = this
      Vue.http.get(g.t('/api/admin/liveSources')).then(function (response) {
        that.$set(that.$data, 'liveSources', response.data)
      }, function (response) {
        g.toLogin()
      })
    }
  },
  created () {
    this.do_load()
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body{
  background-color: #CCC;
}
</style>
