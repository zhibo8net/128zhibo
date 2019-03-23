<template>
  <div>
      <h2>竞猜题库管理</h2>
    <form id="queryForm" class="form-inline" role="form">
      <div class="form-group">
        <label>题目标题</label>
        <input type="input" class="form-control" name="search_LIKE_problemTitle">
      </div>


      <div class="form-group">
        <label>是否默认</label>
        <select class="form-control" name="search_EQ_problemFlag">
          <option value="">所有</option>
          <option  value="DEFALUT">默认题目</option>
          <option  value="NORMAL">一般题目</option>
        </select>
      </div>

      <div class="form-group">
        <label>更新时间</label>
        <input type="date" class="form-control" name="search_GTE_updateTime">
      </div>

      <button type="button" class="btn btn-primary" v-on:click="goto(0)">查询</button>
      <button type="button" class="btn btn-info" v-on:click="create()">新建</button>
    </form>
    <hr>
    <table class="table">
        <thead>
          <tr>
            <th>编号</th>
            <th>标题</th>
            <th>是否默认</th>
            <th>题目类型</th>
            <th>更新时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.id }}</td>
            <td>{{ a.problemTitle }}</td>
            <td>{{ a.problemFlag == 'DEFALUT' ? '是' : '否' }}</td>
            <td>{{ a.problemTypeStr }}</td>

            <td>{{ a.updateTimeStr }}</td>
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
      location.href = '#/problemDbForm/'
    },
    edit: function (problemDb) {
      location.href = '#/problemDbForm/' + problemDb.id
    },
    remove: function (problemDb) {
      var that = this
      Vue.http.delete(g.t('/api/admin/problemDb/' + problemDb.id)).then(function (response) {

        var problemDb = that.$data.page.content
        problemDb.splice(problemDb.indexOf(problemDb), 1)
      }, function (response) {
        g.showMessage(response)
      })
    },
    goto: function (page) {
      var formStr = $('#queryForm').serialize()
      var that = this
      Vue.http.get(g.t(`/api/admin/problemDb/page?${formStr}&page=${page}`)).then(function (response) {
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
