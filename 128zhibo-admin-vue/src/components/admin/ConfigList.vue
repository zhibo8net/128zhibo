<template>
  <div>
      <h2>配置管理</h2>
      <div><button type="button" class="btn btn-info pull-right" v-on:click="create()">新建配置</button></div>
      <table class="table">
        <thead>
          <tr>
            <th>键</th>
            <th>值</th>
            <th>说明</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="e in page.content">
            <td>{{ e.ckey }}</td>
            <td>{{ e.cvalue }}</td>
            <td>{{ e.cdesc }}</td>
            <td>
              <button type="button" class="btn btn-info" v-on:click="edit(e)">编辑</button>
              <button type="button" class="btn btn-danger" v-on:click="remove(e)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <nav>
        <ul class="pagination">
          <li v-for="n in page.totalPages"><a href="javascript:void(0);" v-on:click="goto(n-1)">{{ n }}</a></li>
        </ul>
      </nav>
  </div>
</template>

<script>
import g from '../../global.js'
import Vue from 'vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)

export default {
  name: 'Index',
  data () {
    return {
      page: {
        content: [],
        totalElements: 0,
        last: false,
        totalPages: 0,
        sort: null,
        size: 0,
        number: 0,
        first: false,
        numberOfElements: 0
      }
    }
  },
  methods: {
    create: function () {
      g.href('/configForm/')
    },
    edit: function (config) {
      g.href('/configForm/' + config.id)
    },
    remove: function (config) {
      var that = this
      Vue.http.delete(g.t('/api/admin/configs/' + config.id)).then(function (response) {
        var configs = that.$data.page.content
        configs.splice(configs.indexOf(config), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var that = this
      Vue.http.get(g.t('/api/admin/configs/page?page=' + page)).then(function (response) {
        that.$set(that.$data, 'page', response.data)
      }, function (response) {
        g.toLogin()
      })
    }
  },
  created () {
    this.goto(0)
  }
}
</script>

<style scoped>
</style>