<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('题目标题')}">
        <label>题目标题</label>
        <input type="text" class="form-control"  placeholder="输入题目标题" v-model="problemDb.problemTitle"  v-validate="'required'">
        <span class="help-block" v-show="errors.has('题目标题')">{{ errors.first('题目标题') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('项目')}">
        <label>项目</label>
        <select class="form-control"  v-model="problemDb.project"  v-validate="'required'">
          <option value="足球">足球</option>
          <option value="篮球">篮球</option>
        </select>
        <span class="help-block" v-show="errors.has('是否默认')">{{ errors.first('是否默认') }}</span>
      </div>

      <div :class="{'form-group':true, 'has-error':errors.has('是否默认')}">
        <label>是否默认</label>
        <select class="form-control"  v-model="problemDb.problemFlag"  v-validate="'required'">
          <option value="DEFAULT">是</option>
          <option value="NORMAL">否</option>
        </select>
        <span class="help-block" v-show="errors.has('是否默认')">{{ errors.first('是否默认') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('题目类型')}">
        <label>题目类型</label>
        <select class="form-control"  v-model="problemDb.problemType"  v-validate="'required'">
          <option value="RADIO">单选-选项</option>
          <option value="CHECKBOX">多选-选项</option>
        </select>
        <span class="help-block" name="题目类型" v-show="errors.has('题目类型')">{{ errors.first('题目类型') }}</span>
      </div>
      <h3>题目选项</h3>
      <div>
        <div v-for="question in problemDb.problemContentList">
          <label>题目选项</label>
          <input type="text" v-model="question.questionContent" style="width:400px">

          <button v-on:click="removeQuestion(question)" class="del-image-btn">删除</button>
        </div>
        <button v-on:click="addQuestion"> + </button>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('输入值标示')}">
        <label>输入值标示</label>
        <select class="form-control"  v-model="problemDb.inputFlag">
          <option value="INPUT">有</option>
          <option value="UNINPUT">无</option>
        </select>
        <span class="help-block" v-show="errors.has('输入值标示')">{{ errors.first('输入值标示') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('输入值')}">
        <label>输入值</label>
        <input type="text" class="form-control"   placeholder="输入值" v-model="problemDb.inputContent">
        <span class="help-block" v-show="errors.has('输入值')">{{ errors.first('输入值') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('正确答案')}">
        <label>正确答案</label>
        <input type="text" class="form-control"   placeholder="输入正确答案" v-model="problemDb.answer">
        <span class="help-block" v-show="errors.has('正确答案')">{{ errors.first('正确答案') }}</span>
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
    return {
      problemDb: {
        problemContentList: []
      }
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let problemDb = this.$data.problemDb

              if (!problemDb.id) {
                Vue.http.post(g.t('/api/admin/problemDb'), JSON.stringify(problemDb)).then(function (response) {
                  location.href = '#/problemDbList'
                }, function (response) {
                  g.showMessage(response)
                })
              } else {
                Vue.http.put(g.t('/api/admin/problemDb/' + problemDb.id), JSON.stringify(problemDb)).then(function (response) {
                  location.href = '#/problemDbList'
                }, function (response) {
                  g.showMessage(response)
                })
              }

          }
      })
    },
addQuestion () {
      this.problemDb.problemContentList.push({'questionContent': ''})
    },
removeQuestion (question) {
  this.problemDb.problemContentList.splice(this.problemDb.problemContentList.indexOf(question), 1)
},
  },
  created () {
  let that = this;

  let problemDb = this.$data.problemDb
  let problemDbId = this.$route.params.id
  if (problemDbId) {
    Vue.http.get(g.t('/api/admin/problemDb/' + problemDbId)).then((response) => {
      let problemDbEntity = response.data
      this.$set(problemDb, 'id', problemDbEntity.id)
    this.$set(problemDb, 'problemTitle', problemDbEntity.problemTitle)
    this.$set(problemDb, 'problemFlag', problemDbEntity.problemFlag)
    this.$set(problemDb, 'problemType', problemDbEntity.problemType)
    this.$set(problemDb, 'answer', problemDbEntity.answer)
    this.$set(problemDb, 'project', problemDbEntity.project)
    this.$set(problemDb, 'inputFlag', problemDbEntity.inputFlag)
    this.$set(problemDb, 'inputContent', problemDbEntity.inputContent)
    this.$set(problemDb, 'problemContentList', problemDbEntity.problemContentList)
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
