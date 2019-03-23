<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('敏感词')}">
        <label>敏感词:每个单词以"|"分割  如xxx|xxxx</label>
        <editor id="editor_id" height="400px" width="100%" :content="sensitive.content"
                pluginsPath="/static/kindeditor/plugins/"
                :loadStyleMode="false"
                @on-content-change="onContentChange"></editor>
        <span class="help-block" v-show="errors.has('敏感词')">{{ errors.first('敏感词') }}</span>
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
  import 'kindeditor/kindeditor-all.js'
  import 'kindeditor/themes/default/default.css'

  Vue.use(VueResource)

  Validator.localize('zh_CN', zhcn)
  Vue.use(VeeValidate)

  Vue.config.productionTip = false
  Vue.use(VueKindEditor)


export default {
  name: 'Index',
  data () {
  return { sensitive:{}}
},
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result)
      {
        let sensitive = this.$data.sensitive
        if (!sensitive.id) {
          Vue.http.post(g.t('/api/admin/sensitive'), JSON.stringify(sensitive)).then(function (response) {
            location.href = '#/sensitiveList'
          }, function (response) {
            g.toLogin()
          })
        } else {
          Vue.http.put(g.t('/api/admin/sensitive/' + sensitive.id), JSON.stringify(sensitive)).then(function (response) {
            location.href = '#/sensitiveList'
          }, function (response) {
            g.toLogin()
          })
        }
      }
    }
  )
  }, onContentChange(val)
  {
    this.sensitive.content = val
  }
  }
,created(){

  let sensitive = this.$data.sensitive

  let sensitiveId = this.$route.params.id
  if (sensitiveId) {
    Vue.http.get(g.t('/api/admin/sensitive/' + sensitiveId)).then((response) =>{
      let sensitiveEntity = response.data
      this.$set(sensitive, 'id', sensitiveEntity.id)
     this.$set(sensitive, 'content', sensitiveEntity.content)
    this.$set(sensitive, 'optTime', sensitiveEntity.optTime)
  },function (response) {
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
.one-image{
  padding: 20px;
}
.one-image img{
  height: 200px;
}
.del-image-btn{
  vertical-align: bottom;
}
</style>
