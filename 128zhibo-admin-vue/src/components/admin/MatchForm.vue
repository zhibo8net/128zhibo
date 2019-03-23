<template>
  <div>
    <form v-on:submit.prevent>
      <div :class="{'form-group':true, 'has-error':errors.has('新浪直播源')}">
        <label>新浪直播源</label>
        <input type="text" class="form-control" placeholder="输入新浪直播源" v-model="match.sinaLiveUrl" name="新浪直播源">
        <span class="help-block" v-show="errors.has('新浪直播源')">{{ errors.first('新浪直播源') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('新浪数据源')}">
        <label>新浪数据源</label>
        <input type="text" class="form-control" placeholder="输入新浪数据源" v-model="match.sinaShujuUrl" name="新浪数据源">
        <span class="help-block" v-show="errors.has('新浪数据源')">{{ errors.first('新浪数据源') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('新浪数据源')}">
        <label>sstream365据源</label>
        <input type="text" class="form-control" placeholder="sstream365据源" v-model="match.matchStreamUrl" name="sstream365据源">
        <span class="help-block" v-show="errors.has('sstream365据源')">{{ errors.first('sstream365据源') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('比赛对阵')}">
        <label>比赛对阵</label>
        <input type="text" class="form-control" placeholder="输入比赛对阵" v-model="match.name" v-validate="'required'" name="比赛对阵">
        <span class="help-block" v-show="errors.has('比赛对阵')">{{ errors.first('比赛对阵') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('锁定状态')}">
        <label>锁定状态</label>
        <select class="form-control" name="锁定状态" v-model="match.locked">
            <option value="1">已锁定</option>
            <option value="0">未锁定</option>
        </select>
        <span class="help-block" v-show="errors.has('锁定状态')">{{ errors.first('锁定状态') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('解锁时间')}">
        <label>解锁时间</label>
        <input type="date" placeholder="输入解锁时间" v-model="match.unlockDateStr" name="解锁时间">
        <input type="time" placeholder="输入解锁时间" v-model="match.unlockTimeStr" name="解锁时间">
        <span class="help-block" v-show="errors.has('解锁时间')">{{ errors.first('解锁时间') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('所属项目')}">
        <label>所属项目</label>
        <input type="text" class="form-control" placeholder="输入所属项目" v-model="match.project" v-validate="'required'" name="所属项目">
        <span class="help-block" v-show="errors.has('所属项目')">{{ errors.first('所属项目') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('所属赛事')}">
        <label>所属赛事</label>
        <input type="text" class="form-control" placeholder="输入所属赛事" v-model="match.game" v-validate="'required'" name="所属赛事">
        <span class="help-block" v-show="errors.has('所属赛事')">{{ errors.first('所属赛事') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('是否重要')}">
        <label>是否重要</label>
        <select class="form-control" name="是否重要" v-model="match.emphasis">
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
        <span class="help-block" v-show="errors.has('是否重要')">{{ errors.first('是否重要') }}</span>
      </div>
      <h3>直播</h3>
      <div>
        <div v-for="l in match.lives">
          <label>直播频道</label>
          <input type="text" class="liveInput" v-model="l.name" style="width:200px">
          <label>直播链接</label>
          <input type="text" class="liveInput" v-model="l.link">
          <div :class="{'form-group':true, 'has-error':errors.has('是否站内播放')}" style="width:200px">
            <label>是否站内播放</label>
            <select class="form-control" name="是否站内播放" v-model="l.playFlag" style="width:150px">
              <option value="INNER">站内播放</option>
              <option value="OUTER">站外播放</option>
            </select>
            <span class="help-block" v-show="errors.has('是否站内播放')">{{ errors.first('是否站内播放') }}</span>
          </div>
          <button v-on:click="removeLive(l)" class="del-image-btn">删除</button>
        </div>
        <button v-on:click="addLive"> + </button>
      </div>
      <h3>广告</h3>
      <div>
        <div v-for="ad in match.ads">
          <label>广告名称</label>
          <input type="text" class="liveInput" v-model="ad.name">
          <label>频道链接</label>
          <input type="text" class="liveInput" v-model="ad.link">
          <br>
          <label>是否高亮</label>
          <input type="text" class="liveInput" v-model="ad.light">
          <button v-on:click="removeAd(ad)" class="del-image-btn">删除</button>
        </div>
        <button v-on:click="addAd"> + </button>
      </div>
      <button type="button" class="btn btn-default" v-on:click="submit()">提交</button>
    </form>
  </div>
</template>

<script>
import g from '../../global.js'
import Vue from 'vue'
import VueResource from 'vue-resource'
import zhcn from 'vee-validate/dist/locale/zh_CN'
import VeeValidate, { Validator } from 'vee-validate'
import VueKindEditor from 'vue-kindeditor'
import 'kindeditor/kindeditor-all-min.js'
import 'kindeditor/themes/default/default.css'

Vue.use(VueResource)

Validator.localize('zh_CN', zhcn)
Vue.use(VeeValidate)

Vue.config.productionTip = false
Vue.use(VueKindEditor)

export default {
  name: 'Index',
  data () {
    return {
      match: {
        locked: 0,
        emphasis: 0,
        lives: [],
        ads: []
      }
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let match = this.$data.match
          if (!match.id) {
            Vue.http.post(g.t('/api/admin/matchs'), JSON.stringify(match)).then(function (response) {
              g.href('/matchList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/matchs/' + match.id), JSON.stringify(match)).then(function (response) {
              g.href('/matchList')
            }, function (response) {
              g.toLogin()
            })
          }
        }
      })
    },
    upload: function (e) {
      let that = this

      e.preventDefault()
      var files = e.target.files
      var data = new FormData()
      data.append('image', files[0])

      Vue.http.post(g.t('/api/admin/file'), data).then(function (response) {
        that.$set(that.$data.match, 'image', response.bodyText)
      }, function (response) {
        g.toLogin()
      })
    },
    addLive () {
      this.match.lives.push({'name': '', 'link': ''})
    },
    addAd () {
      this.match.ads.push({'name': '', 'link': '', light: 0})
    },
    removeLive (live) {
      this.match.lives.splice(this.match.lives.indexOf(live), 1)
    },
    removeAd (ad) {
      this.match.ads.splice(this.match.ads.indexOf(ad), 1)
    }
  },
  created () {
    let match = this.$data.match
    let matchId = this.$route.params.id
    if (matchId) {
      Vue.http.get(g.t('/api/admin/matchs/' + matchId)).then((response) => {
        let matchEntity = response.data
        this.$set(match, 'id', matchEntity.id)
        this.$set(match, 'name', matchEntity.name)
        this.$set(match, 'locked', matchEntity.locked)
        this.$set(match, 'unlockTime', matchEntity.unlockTime)
        this.$set(match, 'unlockDateStr', matchEntity.unlockDateStr)
        this.$set(match, 'unlockTimeStr', matchEntity.unlockTimeStr)
        this.$set(match, 'project', matchEntity.project)
        this.$set(match, 'game', matchEntity.game)
        this.$set(match, 'emphasis', matchEntity.emphasis)
        this.$set(match, 'lives', matchEntity.lives)
        this.$set(match, 'ads', matchEntity.ads)
      this.$set(match, 'sinaLiveUrl', matchEntity.sinaLiveUrl)
      this.$set(match, 'matchStreamUrl', matchEntity.matchStreamUrl)
      this.$set(match, 'sinaShujuUrl', matchEntity.sinaShujuUrl)
      }, function (response) {
        g.toLogin()
      })
    }
  }
}
</script>

<style scoped>
.liveInput{
  width: 40%
}
</style>
