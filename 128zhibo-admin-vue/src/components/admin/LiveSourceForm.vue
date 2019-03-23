<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('抓取频道')}">
        <label>抓取频道</label>
        <input type="text" class="form-control" placeholder="输入抓取频道" v-model="liveSource.name" v-validate="'required'" name="抓取频道" readonly>
        <span class="help-block" v-show="errors.has('抓取频道')">{{ errors.first('抓取频道') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('抓取网址')}">
        <label>抓取网址</label>
        <input type="text" class="form-control" placeholder="输入抓取网址" v-model="liveSource.link" v-validate="'required'" name="抓取网址" readonly>
        <span class="help-block" v-show="errors.has('抓取网址')">{{ errors.first('抓取网址') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('运行状态')}">
        <label>运行状态</label>
        <select class="form-control" name="运行状态" v-model="liveSource.active">
            <option value="1">活动中</option>
            <option value="0">非活动中</option>
        </select>
        <span class="help-block" v-show="errors.has('运行状态')">{{ errors.first('运行状态') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('抓取间隔')}">
        <label>抓取间隔（单位：分钟）</label>
        <input type="number" class="form-control" placeholder="输入抓取间隔" v-model="liveSource.fetchInterval" v-validate="'required'" name="抓取间隔">
        <span class="help-block" v-show="errors.has('抓取间隔')">{{ errors.first('抓取间隔') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('频道匹配')}">
        <label>频道匹配（用|分隔）</label>
        <input type="text" class="form-control" placeholder="输入频道匹配" v-model="liveSource.channels" v-validate="'required'" name="频道匹配">
        <span class="help-block" v-show="errors.has('频道匹配')">{{ errors.first('频道匹配') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('站内频道匹配')}">
        <label>站内频道匹配（用|分隔）</label>
        <input type="text" class="form-control" placeholder="输入站内频道匹配" v-model="liveSource.innerPlayChannels"  name="站内频道匹配">
        <span class="help-block" v-show="errors.has('站内频道匹配')">{{ errors.first('站内频道匹配') }}</span>
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
      liveSource: {},
      liveSources: []
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let liveSource = this.$data.liveSource
          if (!liveSource.id) {
            Vue.http.post(g.t('/api/admin/liveSources'), JSON.stringify(liveSource)).then(function (response) {
              g.href('/liveSourceList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/liveSources/' + liveSource.id), JSON.stringify(liveSource)).then(function (response) {
              g.href('/liveSourceList')
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
        that.$set(that.$data.liveSource, 'image', response.bodyText)
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    },
    onContentChange (val) {
      this.liveSource.content = val
    }
  },
  created () {
    let liveSource = this.$data.liveSource
    let liveSourceId = this.$route.params.id
    if (liveSourceId) {
      Vue.http.get(g.t('/api/admin/liveSources/' + liveSourceId)).then((response) => {
        let liveSourceEntity = response.data
        this.$set(liveSource, 'id', liveSourceEntity.id)
        this.$set(liveSource, 'name', liveSourceEntity.name)
        this.$set(liveSource, 'link', liveSourceEntity.link)
        this.$set(liveSource, 'active', liveSourceEntity.active)
        this.$set(liveSource, 'fetchInterval', liveSourceEntity.fetchInterval)
        this.$set(liveSource, 'channels', liveSourceEntity.channels)
      this.$set(liveSource, 'innerPlayChannels', liveSourceEntity.innerPlayChannels)
      }, function (response) {
        g.toLogin()
      })
    }

    // 加载文章分类
    let liveSources = this.$data.liveSources
    Vue.http.get(g.t('/api/admin/liveSources')).then((response) => {
      liveSources.splice(0, liveSources.length)
      response.data.forEach(at => liveSources.push(at))
    }, function (response) {
      g.toLogin()
    })
  }
}
</script>

<style scoped>
body{
  background-color: #CCC;
}
</style>
