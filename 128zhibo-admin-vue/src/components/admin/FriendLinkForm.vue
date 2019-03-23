<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('名称')}">
        <label>名称</label>
        <input type="text" class="form-control" placeholder="输入名称" v-model="friendLink.name" v-validate="'required'" name="名称">
        <span class="help-block" v-show="errors.has('名称')">{{ errors.first('名称') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('链接')}">
        <label>链接</label>
        <input type="text" class="form-control" placeholder="输入链接" v-model="friendLink.link" v-validate="'required'" name="链接">
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
      friendLink: {},
      friendLinks: []
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let friendLink = this.$data.friendLink
          if (!friendLink.id) {
            Vue.http.post(g.t('/api/admin/friendLinks'), JSON.stringify(friendLink)).then(function (response) {
              g.href('/friendLinkList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/friendLinks/' + friendLink.id), JSON.stringify(friendLink)).then(function (response) {
              g.href('/friendLinkList')
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
        that.$set(that.$data.friendLink, 'image', response.bodyText)
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    },
    onContentChange (val) {
      this.friendLink.content = val
    }
  },
  created () {
    let friendLink = this.$data.friendLink
    let friendLinkId = this.$route.params.id
    if (friendLinkId) {
      Vue.http.get(g.t('/api/admin/friendLinks/' + friendLinkId)).then((response) => {
        let friendLinkEntity = response.data
        this.$set(friendLink, 'id', friendLinkEntity.id)
        this.$set(friendLink, 'name', friendLinkEntity.name)
        this.$set(friendLink, 'link', friendLinkEntity.link)
      }, function (response) {
        g.toLogin()
      })
    }

    // 加载文章分类
    let friendLinks = this.$data.friendLinks
    Vue.http.get(g.t('/api/admin/friendLinks')).then((response) => {
      friendLinks.splice(0, friendLinks.length)
      response.data.forEach(at => friendLinks.push(at))
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
