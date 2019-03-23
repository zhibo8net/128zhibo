<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('原词语')}">
        <label>原词语</label>
        <input type="text" class="form-control" placeholder="输入原词语" v-model="replaceWord.fromWord" v-validate="'required'" name="原词语">
        <span class="help-block" v-show="errors.has('原词语')">{{ errors.first('原词语') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('新词语')}">
        <label>新词语</label>
        <input type="text" class="form-control" placeholder="输入新词语" v-model="replaceWord.toWord" v-validate="'required'" name="新词语">
        <span class="help-block" v-show="errors.has('新词语')">{{ errors.first('新词语') }}</span>
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
      replaceWord: {},
      replaceWords: []
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let replaceWord = this.$data.replaceWord
          if (!replaceWord.id) {
            Vue.http.post(g.t('/api/admin/replaceWords'), JSON.stringify(replaceWord)).then(function (response) {
              g.href('/replaceWordList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/replaceWords/' + replaceWord.id), JSON.stringify(replaceWord)).then(function (response) {
              g.href('/replaceWordList')
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
        that.$set(that.$data.replaceWord, 'image', response.bodyText)
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    },
    onContentChange (val) {
      this.replaceWord.content = val
    }
  },
  created () {
    let replaceWord = this.$data.replaceWord
    let replaceWordId = this.$route.params.id
    if (replaceWordId) {
      Vue.http.get(g.t('/api/admin/replaceWords/' + replaceWordId)).then((response) => {
        let replaceWordEntity = response.data
        this.$set(replaceWord, 'id', replaceWordEntity.id)
        this.$set(replaceWord, 'fromWord', replaceWordEntity.fromWord)
        this.$set(replaceWord, 'toWord', replaceWordEntity.toWord)
      }, function (response) {
        g.toLogin()
      })
    }

    // 加载文章分类
    let replaceWords = this.$data.replaceWords
    Vue.http.get(g.t('/api/admin/replaceWords')).then((response) => {
      replaceWords.splice(0, replaceWords.length)
      response.data.forEach(at => replaceWords.push(at))
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
