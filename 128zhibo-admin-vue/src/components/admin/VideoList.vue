<template>
  <div>
      <h2>视频管理</h2>
      <form id="queryForm" class="form-inline" role="form">
          <div class="form-group">
              <label>类型</label>
              <select class="form-control" name="search_EQ_type">
                  <option value="">全部</option>
                  <option>集锦</option>
                  <option>录像</option>
                  <option>片段</option>
                  <option>视频</option>
              </select>
          </div>
          <div class="form-group">
              <label>项目</label>
              <select class="form-control" name="search_EQ_project">
                  <option value="">全部项目</option>
                  <option>足球</option>
                  <option>篮球</option>
              </select>
          </div>
          <div class="form-group">
              <label>赛事</label>
              <select class="form-control" name="search_EQ_game">
                  <option value="">全部赛事</option>
                  <option v-for="g in games">{{g}}</option>
              </select>
          </div>
          <div class="form-group">
              <label>修改时间</label>
              <input type="date" class="form-control" name="search_GT_addTime"> 
              -
              <input type="date" class="form-control" name="search_LT_addTime"> 
          </div>
          <button type="button" class="btn btn-primary" v-on:click="goto(0)">查询</button>
      </form>
      <hr>
      <div><button type="button" class="btn btn-info pull-right" v-on:click="create()">新建视频</button></div>
      <table class="table">
        <thead>
          <tr>
            <th>标题</th>
            <th>比赛</th>
            <th>类型</th>
            <th>项目</th>
            <th>联赛</th>
            <th>链接</th>
            <th>图片</th>
            <th>修改时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.name }}</td>
            <td>{{ a.ended ? a.ended.name : '' }}</td>
            <td>{{ a.type }}</td>
            <td>{{ a.project }}</td>
            <td>{{ a.game }}</td>
            <td><a :href="a.link" target="_blank" v-if="a.link">链接</a></td>
            <td><a :href="a.image" target="_blank" v-if="a.image">图片</a></td>
            <td>{{ a.addTime }}</td>
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
      games: '英超|意甲|西甲|法甲|德甲|中超|欧冠|NBA|CBA'.split('|'),
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
      g.href('/videoForm/')
    },
    edit: function (video) {
      g.href('/videoForm/' + video.id)
    },
    remove: function (video) {
      var that = this
      Vue.http.delete(g.t('/api/admin/videos/' + video.id)).then(function (response) {
        var videos = that.$data.page.content
        videos.splice(videos.indexOf(video), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var formStr = $('#queryForm').serialize()
      var that = this
      Vue.http.get(g.t(`/api/admin/videos/page?${formStr}&page=${page}`)).then(function (response) {
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
