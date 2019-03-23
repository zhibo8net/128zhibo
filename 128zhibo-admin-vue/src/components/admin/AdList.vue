<template>
  <div>
      <h2>比赛广告管理</h2>
      <form id="queryFormAd" class="form-inline" role="form">
          <div class="form-group">
              <label>类型</label>
              <select class="form-control" name="search_EQ_type" v-on:change="goto(0)">
                  <option>通用</option>
                  <option>高级</option>
              </select>
          </div>
      </form>
      <hr>
      <div><button type="button" class="btn btn-info pull-right" v-on:click="create()">新建比赛广告</button></div>
      <table class="table">
        <thead>
          <tr>
            <th>类型</th>
            <th>标题</th>
            <th>链接</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.type }}</td>
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
      g.href('/adForm/')
    },
    edit: function (ad) {
      g.href('/adForm/' + ad.id)
    },
    remove: function (ad) {
      var that = this
      Vue.http.delete(g.t('/api/admin/ads/' + ad.id)).then(function (response) {
        var ads = that.$data.page.content
        ads.splice(ads.indexOf(ad), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var formStr = $('#queryFormAd').serialize()
      var that = this
      Vue.http.get(g.t(`/api/admin/ads/page?${formStr}&page=${page}`)).then(function (response) {
        that.$set(that.$data, 'page', response.data)
      }, function (response) {
        g.toLogin()
      })
    }
  },
  mounted () {
    this.goto(0)
  }
}
</script>

<style scoped>
</style>
