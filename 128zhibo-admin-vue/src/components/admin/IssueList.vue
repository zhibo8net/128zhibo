<template>
  <div>
      <h2>竞猜管理</h2>
    <form id="queryForm" class="form-inline" role="form">
      <div class="form-group">
        <label>期次</label>
        <input type="input" class="form-control" name="search_LIKE_issue">
      </div>

      <div class="form-group">
        <label>项目</label>
        <select class="form-control" name="search_EQ_project">
          <option value="">全部项目</option>
          <option>足球</option>
          <option>篮球</option>
          <option>游戏</option>
          <option>综合</option>
          <option>其他</option>
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
        <label>状态</label>
        <select class="form-control" name="search_EQ_status">
          <option value="">所有状态</option>
          <option  value="INIT">初始化</option>
          <option  value="DOING">竞猜中</option>
          <option  value="MATCHEND">比赛结束</option>
          <option  value="DRAWING">开奖中</option>
          <option  value="DRAW">已开奖</option>
          <option  value="PIEAWARD">已派奖</option>
        </select>
      </div>
      <div class="form-group">
        <label>对阵</label>
        <input type="input" class="form-control" name="search_LIKE_matchName">
      </div>
      <div class="form-group">
        <label>发布日期</label>
        <input type="date" class="form-control" name="search_GTE_addTime">
      </div>

      <button type="button" class="btn btn-primary" v-on:click="goto(0)">查询</button>
      <button type="button" class="btn btn-info" v-on:click="create()">+发布</button>
    </form>
    <hr>
    <table class="table">
        <thead>
          <tr>
            <th>期次</th>
            <th>对阵</th>
            <th>比赛时间</th>
            <th>项目</th>
            <th>赛事</th>
            <th>状态</th>
            <th>竞猜人数</th>
            <th>中奖人数</th>
            <th>问题数</th>
            <th>发布时间</th>
            <th align="center">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.issue }}</td>
            <td>{{ a.matchName }}</td>
            <td>{{ a.playDateStr }}</td>
            <td>{{ a.project }}</td>
            <td>{{ a.game }}</td>
            <td>{{ a.statusDesc }}</td>
            <td>{{ a.partNum }}</td>
            <td>{{ a.awardNum }}</td>
            <td>{{ a.problemNum }}</td>
            <td>{{ a.addTimeStr }}</td>
            <td width="200px">
              <button type="button" class="btn btn-info" v-on:click="edit(a)">编辑</button>
              <button type="button" class="btn btn-danger" v-on:click="remove(a)">删除</button>
              <button type="button" class="btn btn-info" v-on:click="userEdit(a)">更多</button>
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
      games: '法联杯|德国杯|国王杯|英足总杯|亚洲杯|英超|意甲|西甲|法甲|德甲|中超|欧冠|NBA|CBA|希腊超|智甲|乌克超|土超|土甲|瑞士超|瑞士甲|丹超|葡超|波兰甲|阿超|瑞典超|克罗甲|苏超|捷甲|俄超|荷甲|德乙|澳超|澳甲|K联赛|J联赛|罗甲|比甲|秘鲁甲|巴甲|墨联|英冠|英甲|意乙|西乙| 挪超|中甲|中乙'.split('|'),
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
      location.href = '#/addIssueForm/'
    },
    edit: function (issue) {
      location.href = '#/updateIssueForm/' + issue.id
    },
    userEdit: function (issue) {
      location.href = '#/userForm/' + issue.id
    },
    remove: function (issue) {
      var that = this
      Vue.http.delete(g.t('/api/admin/issue/' + issue.id)).then(function (response) {

        var issue = that.$data.page.content
        issue.splice(issue.indexOf(issue), 1)
      }, function (response) {
        g.showMessage(response)
      })
    },
    goto: function (page) {
      var formStr = $('#queryForm').serialize()
      var that = this
      Vue.http.get(g.t(`/api/admin/issue/page?${formStr}&page=${page}`)).then(function (response) {
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
