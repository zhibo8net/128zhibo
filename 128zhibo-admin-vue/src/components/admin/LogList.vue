<template>
  <div>
      <h2>登录日志</h2>
      <form id="queryForm" class="form-inline" role="form">
          <div class="form-group">
              <label>用户账号</label>
              <input type="text" class="form-control" name="search_EQ_account.mobile"> 
          </div>
          <div class="form-group">
              <label>时间</label>
              <input type="date" class="form-control" name="search_GT_addTime"> 
              -
              <input type="date" class="form-control" name="search_LT_addTime"> 
          </div>
          <button type="button" class="btn btn-primary" v-on:click="goto(0)">查询</button>
      </form>
      <hr>
      <table class="table">
        <thead>
          <tr>
            <th>用户账号</th>
            <th>操作时间</th>
            <th>IP地址</th>
            <th>用户行为</th>
            <th>关联实体</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.account.mobile }}</td>
            <td>{{ a.addTimeStr }}</td>
            <td>{{ a.ip }}</td>
            <th>{{ a.behaviour }}</th>
            <th><a href="javascript:void(0);" v-if="a.link!=null" v-on:click="href(a.link)">关联实体</a></th>
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
      g.href('/logForm/')
    },
    edit: function (log) {
      g.href('/logForm/' + log.id)
    },
    remove: function (log) {
      var that = this
      Vue.http.delete(g.t('/api/admin/logs/' + log.id)).then(function (response) {
        var logs = that.$data.page.content
        logs.splice(logs.indexOf(log), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var formStr = $('#queryForm').serialize()
      var that = this
      Vue.http.get(g.t(`/api/admin/logs/page?${formStr}&page=${page}`)).then(function (response) {
        that.$set(that.$data, 'page', response.data)
      }, function (response) {
        g.toLogin()
      })
    },
    href: function (href) {
      g.href(href)
    }
  },
  created () {
    this.goto(0)
  }
}
</script>

<style scoped>
</style>
