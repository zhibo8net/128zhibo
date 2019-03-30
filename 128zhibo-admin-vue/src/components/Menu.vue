<template>
  <div>
    <ul class="menu" style="height:450px;">
      <li :class="{'active' : path == '/liveSourceList' || path.startsWith('/liveSourceForm')}">
        <a href="javascript:void(0);" v-on:click="href('/liveSourceList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 抓取频率</a>
      </li>
      <li :class="{'active' : path == '/matchList' || path.startsWith('/matchForm')}">
        <a href="javascript:void(0);" v-on:click="href('/matchList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 比赛管理</a>
      </li>

      <!--<li :class="{'active' : path == '/issueList' || path.startsWith('/issueForm')|| path.startsWith('/userForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/issueList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 竞猜管理</a>-->
      <!--</li>-->
      <!--<li :class="{'active' : path == '/problemDbList' || path.startsWith('/problemDbForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/problemDbList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 竞猜题库管理</a>-->
      <!--</li>-->
      <!--<li :class="{'active' : path == '/adList' || path.startsWith('/adForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/adList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 比赛广告管理</a>-->
      <!--</li>-->
      <li :class="{'active' : path == '/leagueList' || path.startsWith('/leagueForm')}">
        <a href="javascript:void(0);" v-on:click="href('/leagueList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 联赛管理</a>
      </li>
      <li :class="{'active' : path == '/teamList' || path.startsWith('/teamForm')}">
        <a href="javascript:void(0);" v-on:click="href('/teamList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 球队管理</a>
      </li>

      <!--<li :class="{'active' : path == '/videoList' || path.startsWith('/videoForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/videoList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 视频管理</a>-->
      <!--</li>-->
      <!--<li :class="{'active' : path == '/newsList' || path.startsWith('/newsForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/newsList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 新闻管理</a>-->
      <!--</li>-->
      <!--<li :class="{'active' : path == '/sensitiveList' || path.startsWith('/sensitiveForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/sensitiveList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 铭感词管理</a>-->
      <!--</li>-->
      <!--<li :class="{'active' : path == '/imageList' || path.startsWith('/imageForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/imageList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 组图管理</a>-->
      <!--</li>-->
      <!--<li :class="{'active' : path == '/teleList' || path.startsWith('/teleForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/teleList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 电视管理</a>-->
      <!--</li>-->
      <li :class="{'active' : path == '/pageAdList' || path.startsWith('/pageAdForm')}">
        <a href="javascript:void(0);" v-on:click="href('/pageAdList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 广告位管理</a>
      </li>
      <li :class="{'active' : path == '/friendLinkList' || path.startsWith('/friendLinkForm')}">
        <a href="javascript:void(0);" v-on:click="href('/friendLinkList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 友情链接管理</a>
      </li>
      <!--<li :class="{'active' : path == '/replaceWordList' || path.startsWith('/replaceWordForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/replaceWordList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 替换词管理</a>-->
      <!--</li>-->
      <!--<li :class="{'active' : path == '/configList' || path.startsWith('/configForm')}">-->
        <!--<a href="javascript:void(0);" v-on:click="href('/configList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 配置管理</a>-->
      <!--</li>-->
      <li :class="{'active' : path == '/accountList' || path.startsWith('/accountForm')}" v-if="isSuper">
        <a href="javascript:void(0);" v-on:click="href('/accountList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 账号管理</a>
      </li>
      <li :class="{'active' : path == '/logList' || path.startsWith('/logForm')}" v-if="isSuper">
        <a href="javascript:void(0);" v-on:click="href('/logList')"><span class="glyphicon glyphicon-book" aria-hidden="true"></span> 登录日志</a>
      </li>
      <!-- 示范：可以用这种方法定义跳转链接
      <li :class="{'active' : path == '/logList' || path.startsWith('/logForm')}" v-if="isSuper">
        <router-link to="/about">关于</router-link>
      </li>
      -->
    </ul>
  </div>
</template>

<script>
import g from '../global.js'
import Vue from 'vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)

export default {
  name: 'Menu',
  data () {
    return {
      articleTypes: []
    }
  },
  computed: {
    path: function () {
      return this.$route.path
    },
    isSuper: function () {
      return localStorage.getItem('token') && localStorage.getItem('token').startsWith('s')
    }
  },
  methods: {
    do_load: function (id) {
      // ...
    },
    href: function (href) {
      g.href(href)
      // 这样来进行路由跳转就可以，不过既然用了上面的方式，就这样吧
      this.$router.push(href)
    }
  },
  created () {
    this.do_load()
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
ul.menu{
  padding: 0;
  margin:0;
  list-style-type: none;
  background-color: #3d3d3d;
  height: 100%;
}
ul.menu li{
  padding: 10px;
  padding-left: 15px;
}
ul.menu li.active{
  background-color: #c62a29;
}
ul.menu li a{
  font-weight: 300;
  color: #fff ;
}
</style>
