<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('类别')}">
        <label>类别</label>
        <select class="form-control" v-model="ad.type" v-validate="'required'">
            <option>通用</option>
            <option>高级</option>
        </select>
        <span class="help-block" v-show="errors.has('类别')">{{ errors.first('类别') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('名称')}">
        <label>名称</label>
        <input type="text" class="form-control" placeholder="输入名称" v-model="ad.name" v-validate="'required'" name="名称">
        <span class="help-block" v-show="errors.has('名称')">{{ errors.first('名称') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('链接')}">
        <label>链接</label>
        <input type="text" class="form-control" placeholder="输入链接" v-model="ad.link" v-validate="'required'" name="链接">
        <span class="help-block" v-show="errors.has('链接')">{{ errors.first('链接') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('失效日期')||errors.has('失效时间')}" v-if="ad.type=='高级'">
        <label>失效日期</label>
        <input type="date" class="form-control" placeholder="输入失效日期" v-model="ad.endDateStr" v-validate="'required'" name="失效日期">
        <input type="time" class="form-control" placeholder="输入失效时间" v-model="ad.endTimeStr" name="失效时间">
        <span class="help-block" v-show="errors.has('失效日期')">{{ errors.first('失效日期') }}</span>
        <span class="help-block" v-show="errors.has('失效时间')">{{ errors.first('失效时间') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('匹配球队')}" v-if="ad.type=='高级'">
        <label>匹配球队（多个球队之间用|分隔，如意大利|西班牙）</label>
        <input type="text" class="form-control" placeholder="输入匹配球队" v-model="ad.teams" v-validate="'required'" name="匹配球队">
        <span class="help-block" v-show="errors.has('匹配球队')">{{ errors.first('匹配球队') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('是否重要')}" v-if="ad.type=='高级'">
        <label>是否重要</label>
        <select class="form-control" v-model="ad.important" name="是否重要" v-validate="'required'">
          <option value="1">是</option>
          <option value="0">否</option>
        </select>
        <span class="help-block" v-show="errors.has('是否重要')">{{ errors.first('是否重要') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('是否高亮')}" v-if="ad.type=='高级'">
        <label>是否高亮</label>
        <select class="form-control" v-model="ad.light" name="是否高亮" v-validate="'required'">
          <option value="1">是</option>
          <option value="0">否</option>
        </select>
        <span class="help-block" v-show="errors.has('是否高亮')">{{ errors.first('是否高亮') }}</span>
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
      ad: {
        type: '通用',
        important: 0,
        light: 0
      }
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let ad = this.$data.ad
          if (!ad.id) {
            Vue.http.post(g.t('/api/admin/ads'), JSON.stringify(ad)).then(function (response) {
              g.href('/adList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/ads/' + ad.id), JSON.stringify(ad)).then(function (response) {
              g.href('/adList')
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
        that.$set(that.$data.ad, 'image', response.bodyText)
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    },
    onContentChange (val) {
      this.ad.content = val
    }
  },
  created () {
    let ad = this.$data.ad
    let adId = this.$route.params.id
    if (adId) {
      Vue.http.get(g.t('/api/admin/ads/' + adId)).then((response) => {
        let adEntity = response.data
        this.$set(ad, 'id', adEntity.id)
        this.$set(ad, 'type', adEntity.type)
        this.$set(ad, 'name', adEntity.name)
        this.$set(ad, 'link', adEntity.link)
        this.$set(ad, 'endDateStr', adEntity.endDateStr)
        this.$set(ad, 'endTimeStr', adEntity.endTimeStr)
        this.$set(ad, 'teams', adEntity.teams)
        this.$set(ad, 'important', adEntity.important)
        this.$set(ad, 'light', adEntity.light)
      }, function (response) {
        g.toLogin()
      })
    }
  }
}
</script>

<style scoped>
body{
  background-color: #CCC;
}
</style>
