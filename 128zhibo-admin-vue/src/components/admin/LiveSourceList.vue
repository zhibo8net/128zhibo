<template>
  <div>
      <h2>抓取频率</h2>
      <table class="table">
        <thead>
          <tr>
            <th>数据源</th>
            <!--<th>匹配频道</th>-->
            <th>抓取频率</th>
            <th>链接</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.name }} </td>
            <!--<td>{{ a.channels }}</td>-->
            <td>{{ a.fetchInterval }}分钟</td>
            <td>{{ a.link }}</td>
            <td><span class="active" v-if="a.active==1">活动中</span> <span v-else>非活动中</span></td>
            <td>
              <button type="button" class="btn btn-info" v-on:click="edit(a)">编辑</button>
              <button type="button" class="btn btn-danger" v-on:click="remove(a)">删除</button>
              <button type="button" class="btn btn-success" v-on:click="active(a)" v-if="a.active==0">设为活动状态</button>
            </td>
          </tr>
        </tbody>
      </table>
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
      g.href('/liveSourceForm/')
    },
    edit: function (liveSource) {
      g.href('/liveSourceForm/' + liveSource.id)
    },
    remove: function (liveSource) {
      var that = this
      Vue.http.delete(g.t('/api/admin/liveSources/' + liveSource.id)).then(function (response) {
        var liveSources = that.$data.page.content
        liveSources.splice(liveSources.indexOf(liveSource), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    active: function (liveSource) {
      var that = this
      Vue.http.post(g.t('/api/admin/liveSources/' + liveSource.id + '/active')).then(function (response) {
        that.$set(that.$data, 'page', response.data)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var that = this
      Vue.http.get(g.t('/api/admin/liveSources/page?page=' + page)).then(function (response) {
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
  color: green;
  font-weight: bold;
}
</style>
