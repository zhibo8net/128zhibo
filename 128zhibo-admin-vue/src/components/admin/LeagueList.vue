<template>
  <div>
      <h2>联赛管理</h2>
    <form id="queryForm" class="form-inline" role="form">

      <div class="form-group">
        <label>联赛中文名称</label>
        <input type="text" class="form-control" name="search_EQ_leagueZh">
      </div>


      <button type="button" class="btn btn-primary" v-on:click="goto(0)">查询</button>
    </form>
      <table class="table">
        <thead>
          <tr>
            <th>联赛中文名称</th>
            <th>联赛英文名称</th>
            <th>联赛球队数</th>
            <th>联赛简称1</th>
            <th>联赛简称2</th>
            <th>联赛简称3</th>
            <th>图片地址</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.leagueZh }}</td>
            <td>{{ a.leagueEn }}</td>
            <td>{{ a.teamNum }}</td>
            <td>{{ a.leagueName1 }}</td>
            <td>{{ a.leagueName2 }}</td>
            <td>{{ a.leagueName3 }}</td>
            <td><img :src="a.leagueImgLink" v-if="a.leagueImgLink!=null" width="20px" height="20px"/></td>
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
      location.href = '#/leagueForm/'
    },
    edit: function (league) {
      location.href = '#/leagueForm/' + league.id
    },
    remove: function (league) {
      var that = this
      Vue.http.delete(g.t('/api/admin/league/' + league.id)).then(function (response) {
        var images = that.$data.page.content
        images.splice(images.indexOf(league), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var formStr = $('#queryForm').serialize()
      var that = this
      Vue.http.get(g.t(`/api/admin/league/page?${formStr}&page=${page}`)).then(function (response) {
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
