<template>
  <div>
      <h2>用户账号管理</h2>
      <div><button type="button" class="btn btn-info pull-right" v-on:click="create()">新建用户账号</button></div>
      <table class="table">
        <thead>
          <tr>
            <th>用户名</th>
            <th>姓名</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.mobile }}</td>
            <td>{{ a.name }}</td>
            <td><span class="active" v-if="a.status==1">启用</span><span v-if="a.status==0">停用</span> </td>
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
      g.href('/accountForm/')
    },
    edit: function (account) {
      g.href('/accountForm/' + account.id)
    },
    remove: function (account) {
      var that = this
      Vue.http.delete(g.t('/api/admin/accounts/' + account.id)).then(function (response) {
        var accounts = that.$data.page.content
        accounts.splice(accounts.indexOf(account), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var that = this
      Vue.http.get(g.t('/api/admin/accounts/page?page=' + page)).then(function (response) {
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
.active{
  color: green
}
</style>
