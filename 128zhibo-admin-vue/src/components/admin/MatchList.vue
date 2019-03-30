<template>
  <div>
      <h2>比赛管理</h2>
      <form id="queryForm" class="form-inline" role="form">

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
              <label>联赛</label>
              <select class="form-control" name="search_EQ_game">
                  <option value="">全部联赛</option>
                  <option v-for="g in games">{{g}}</option>
              </select>
          </div>
          <div class="form-group">
              <label>日期</label>
              <input type="date" class="form-control" name="search_EQ_playDateStr">
          </div>
          <div class="form-group">
              <label>对阵</label>
              <input type="input" class="form-control" name="search_LIKE_name">
          </div>
          <!--<div class="form-group">-->
              <!--<label>状态</label>-->
              <!--<select class="form-control" name="search_EQ_locked">-->
                  <!--<option value="">全部</option>-->
                  <!--<option value="1">已锁定</option>-->
                  <!--<option value="0">未锁定</option>-->
              <!--</select>-->
          <!--</div>-->
          <!--<div class="form-group">-->
              <!--<label>是否重要</label>-->
              <!--<select class="form-control" name="search_EQ_emphasis">-->
                  <!--<option value="">全部</option>-->
                  <!--<option value="1">是</option>-->
                  <!--<option value="0">否</option>-->
              <!--</select>-->
          <!--</div>-->
          <button type="button" class="btn btn-primary" v-on:click="goto(0)">查询</button>
        <button type="button" class="btn btn-info" v-on:click="create()">新建比赛</button>
      </form>
      <hr>

      <table class="table">
        <thead>
          <tr>
            <th>直播日期</th>
            <th>直播时间</th>
            <th>星期几</th>
            <th>对阵</th>
            <th>联赛</th>
            <th>项目</th>
            <!--<th>重要</th>-->
            <!--<th>状态</th>-->
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in page.content">
            <td>{{ a.playDateStr }}</td>
            <td>{{ a.playTime }}</td>
            <td>{{ week(a) }}</td>
            <td>{{ a.name }}</td>
            <td>{{ a.game }}</td>
            <td>{{ a.project }}</td>
            <!--<td>{{ a.emphasis == 1 ? '是' : '否' }}</td>-->
            <!--<td>{{ a.locked == 1 ? '已锁定' : '未锁定' }}</td>-->
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
      games: '英超|意甲|西甲|法甲|德甲|中超|欧冠|NBA|CBA|希腊超|智甲|乌克超|土超|土甲|瑞士超|瑞士甲|丹超|葡超|波兰甲|阿超|瑞典超|克罗甲|苏超|捷甲|俄超|荷甲|德乙|澳超|澳甲|K联赛|J联赛|罗甲|比甲|秘鲁甲|巴甲|墨联|英冠|英甲|意乙|西乙| 挪超|中甲|中乙'.split('|'),
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
      g.href('/matchForm/')
    },
    edit: function (match) {
      g.href('/matchForm/' + match.id)
    },
    remove: function (match) {
      var that = this
      Vue.http.delete(g.t('/api/admin/matchs/' + match.id)).then(function (response) {
        var matchs = that.$data.page.content
        matchs.splice(matchs.indexOf(match), 1)
      }, function (response) {
        g.toLogin()
      })
    },
    goto: function (page) {
      var formStr = $('#queryForm').serialize()
      var that = this
      Vue.http.get(g.t(`/api/admin/matchs/page?${formStr}&page=${page}`)).then(function (response) {
        that.$set(that.$data, 'page', response.data)
      }, function (response) {
        g.toLogin()
      })
    },
    liveNames: function (lives) {
      var liveNamesArray = lives.map((l) => l.name)
      return liveNamesArray.join(',')
    },
    week: function (match) {
      var day = new Date(match.playDate).getDay()
      var xingqi = ''
      switch (day) {
        case 0:
          xingqi = '星期日'
          break
        case 1:
          xingqi = '星期一'
          break
        case 2:
          xingqi = '星期二'
          break
        case 3:
          xingqi = '星期三'
          break
        case 4:
          xingqi = '星期四'
          break
        case 5:
          xingqi = '星期五'
          break
        case 6:
          xingqi = '星期六'
          break
        default:
          xingqi = '系统错误！'
      }
      return xingqi
    }
  },
  created () {
    this.goto(0)
  }
}
</script>

<style scoped>
</style>
