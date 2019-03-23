<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('视频链接')}">
        <label>视频链接</label>
        <input type="text" class="form-control" placeholder="输入视频链接" v-model="video.link" v-validate="'required'" name="视频链接" :disabled="video.id">
        <span class="help-block" v-show="errors.has('视频链接')">{{ errors.first('视频链接') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('视频标题')}">
        <label>视频标题</label>
        <input type="text" class="form-control" placeholder="输入视频标题" v-model="video.name" v-validate="'required'" name="视频标题">
        <span class="help-block" v-show="errors.has('视频标题')">{{ errors.first('视频标题') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('比赛')}" v-if="video.id">
        <label>比赛</label>
        <input type="text" class="form-control" placeholder="输入比赛" v-model="video.endedName" v-validate="'required'" name="比赛" disabled>
        <span class="help-block" v-show="errors.has('比赛')">{{ errors.first('比赛') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('类别')}">
        <label>类别</label>
        <select class="form-control" v-model="video.type" v-validate="'required'" name="类别" disabled>
            <option>集锦</option>
            <option>录像</option>
            <option>片段</option>
            <option>视频</option>
        </select>
        <span class="help-block" v-show="errors.has('类别')">{{ errors.first('类别') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('项目')}">
        <label>项目</label>
        <input type="text" class="form-control" placeholder="输入项目" v-model="video.project" v-validate="'required'" name="项目" :disabled="video.id">
        <span class="help-block" v-show="errors.has('项目')">{{ errors.first('项目') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('赛事')}">
        <label>赛事</label>
        <input type="text" class="form-control" placeholder="输入赛事" v-model="video.game" v-validate="'required'" name="赛事" :disabled="video.id">
        <span class="help-block" v-show="errors.has('赛事')">{{ errors.first('赛事') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('图片')}">
        <label>图片</label>
        <input ref="files" type="file" name="image" id="image" v-on:change="upload">
        <img :src="video.image" class="img-responsive"/>
        <span class="help-block" v-show="errors.has('图片')">{{ errors.first('图片') }}</span>
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
      video: {
        type: '视频'
      }
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let video = this.$data.video
          if (!video.id) {
            Vue.http.post(g.t('/api/admin/videos'), JSON.stringify(video)).then(function (response) {
              g.href('/videoList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/videos/' + video.id), JSON.stringify(video)).then(function (response) {
              g.href('/videoList')
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
        that.$set(that.$data.video, 'image', response.bodyText)
      }, function (response) {
        g.toLogin()
      })
    },
    addLive () {
      this.video.lives.push({'name': '', 'link': ''})
    },
    addAd () {
      this.video.ads.push({'name': '', 'link': '', light: 0})
    }
  },
  created () {
    let video = this.$data.video
    let videoId = this.$route.params.id
    if (videoId) {
      Vue.http.get(g.t('/api/admin/videos/' + videoId)).then((response) => {
        let videoEntity = response.data
        this.$set(video, 'id', videoEntity.id)
        this.$set(video, 'name', videoEntity.name)
        this.$set(video, 'link', videoEntity.link)
        this.$set(video, 'type', videoEntity.type)
        this.$set(video, 'project', videoEntity.project)
        this.$set(video, 'game', videoEntity.game)
        this.$set(video, 'image', videoEntity.image)
        if (videoEntity.ended) {
          this.$set(video, 'endedName', videoEntity.ended.name)
        }
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
