<template>
  <div>
      <h2>页面广告管理</h2>
      <div><button type="button" class="btn btn-info pull-right" v-on:click="create()">新建页面广告</button></div>
      <table class="table">
        <thead>
          <tr>
            <th>编号</th>
            <th>位置</th>
            <th>尺寸</th>
            <th>链接</th>
            <th>图片</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>广告位置{{ a.id }}</td>
            <td>{{ a.adKey }}</td>
            <td>{{ a.message }}</td>
            <td><a :href="a.link" target="_blank">链接</a></td>
            <td><a :href="a.image" target="_blank">图片</a></td>
            <td>
              <button type="button" class="btn btn-info" v-on:click="edit(a)">编辑</button>
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
      g.href('/pageAdForm/')
    },
    edit: function (pageAd) {
      g.href('/pageAdForm/' + pageAd.id)
    },
    remove: function (pageAd) {
      var that = this
      Vue.http.delete(g.t('/api/admin/pageAds/' + pageAd.id)).then(function (response) {
        var pageAds = that.$data.page.content
        pageAds.splice(pageAds.indexOf(pageAd), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var formStr = $('#queryForm').serialize()
      var that = this
      Vue.http.get(g.t(`/api/admin/pageAds/page?${formStr}&page=${page}`)).then(function (response) {
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
