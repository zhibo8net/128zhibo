<template>
  <div>
      <h2>友情链接管理</h2>
      <div><button type="button" class="btn btn-info pull-right" v-on:click="create()">新建友情链接</button></div>
      <table class="table">
        <thead>
          <tr>
            <th>名称</th>
            <th>链接</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.name }}</td>
            <td><a :href="a.link" target="_blank">链接</a></td>
            <td>
              <button type="button" class="btn btn-info" v-on:click="edit(a)">编辑</button>
              <button type="button" class="btn btn-danger" v-on:click="remove(a)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <nav>
        <ul class="pagination">
          <li><a @click="goto(0)" href="javascript:void(0);">&lt;&lt;</a></li>
          <li v-for="n in page.totalPages" v-if="n > page.number - 12 && n < page.number + 10" :class="{'active':n == page.number + 1}"><a href="javascript:void(0);" v-on:click="goto(n-1)">{{ n }}</a></li>
          <li><a @click="goto(page.totalPages-1)" href="javascript:void(0);">&gt;&gt;</a></li>
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
      g.href('/friendLinkForm/')
    },
    edit: function (friendLink) {
      g.href('/friendLinkForm/' + friendLink.id)
    },
    remove: function (friendLink) {
      var that = this
      Vue.http.delete(g.t('/api/admin/friendLinks/' + friendLink.id)).then(function (response) {
        var friendLinks = that.$data.page.content
        friendLinks.splice(friendLinks.indexOf(friendLink), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var that = this
      Vue.http.get(g.t('/api/admin/friendLinks/page?page=' + page)).then(function (response) {
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
