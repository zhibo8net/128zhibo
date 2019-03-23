<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('名称')}">
        <label>名称</label>
        <input type="text" class="form-control" placeholder="输入名称" v-model="log.name" v-validate="'required'" name="名称">
        <span class="help-block" v-show="errors.has('名称')">{{ errors.first('名称') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('链接')}">
        <label>链接</label>
        <input type="text" class="form-control" placeholder="输入链接" v-model="log.link" v-validate="'required'" name="链接">
        <span class="help-block" v-show="errors.has('链接')">{{ errors.first('链接') }}</span>
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
      log: {},
      logs: []
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let log = this.$data.log
          if (!log.id) {
            Vue.http.post(g.t('/api/admin/logs'), JSON.stringify(log)).then(function (response) {
              g.href('/logList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/logs/' + log.id), JSON.stringify(log)).then(function (response) {
              g.href('/logList')
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
        that.$set(that.$data.log, 'image', response.bodyText)
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    },
    onContentChange (val) {
      this.log.content = val
    }
  },
  created () {
    let log = this.$data.log
    let logId = this.$route.params.id
    if (logId) {
      Vue.http.get(g.t('/api/admin/logs/' + logId)).then((response) => {
        let logEntity = response.data
        this.$set(log, 'id', logEntity.id)
        this.$set(log, 'name', logEntity.name)
        this.$set(log, 'link', logEntity.link)
      }, function (response) {
        g.toLogin()
      })
    }

    // 加载文章分类
    let logs = this.$data.logs
    Vue.http.get(g.t('/api/admin/logs')).then((response) => {
      logs.splice(0, logs.length)
      response.data.forEach(at => logs.push(at))
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
