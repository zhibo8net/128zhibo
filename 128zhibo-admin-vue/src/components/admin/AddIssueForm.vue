<template>
  <div>
    <h2>竞猜编辑</h2>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('期次')}">
        <label>期次(由系统自动生成)</label>
        <span>{{ issue.issue }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('项目')}">
        <label>项目</label>
        <select class="form-control"  v-model="issue.project" v-validate="'required'" style="width:200px">
          <option value="足球">足球</option>
          <option value="篮球">篮球</option>
        </select>
        <span class="help-block" v-show="errors.has('项目')">{{ errors.first('项目') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('对阵')}">
        <label>对阵</label>
        <select class="form-control" v-model="issue.matchNameAndId" v-validate="'required'" style="width:200px">
          <option v-for="matchNameAndId in matchList">{{matchNameAndId}}</option>
        </select>
        <span class="help-block" v-show="errors.has('对阵')">{{ errors.first('对阵') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('竞猜题目')}">
        <label>竞猜题目</label>
        <div v-for="(question,i) in issue.issueQuestionList" :key="i">
          <p>
            <input type="checkbox"  v-on:click="checkQuestion(question)"/>
            <span style="padding-right:10px;">默认题{{i+1}}:</span>{{question.problemTitle}}
            <input type="text" v-if="question.inputFlag=='INPUT'" v-model="question.inputContent">
          </p>
          <span>选项：</span>
          <label v-for="option in question.problemContentList"  style="border:1px solid #4184f3;margin-left:10px;font-weight:200">{{option.questionContent}}</label>
        </div>

      </div>
      <label>添加自定义问题与选项</label>  <button  v-on:click="addOtherQuestion()"> + </button>
      <div>
        <div v-for="otherQuestion in otherIssueQuestionList">
          <label>标题</label></br>
          <textarea  v-model="otherQuestion.problemTitle" style="width:200px"/>
          </br>
          <label>选项</label>  <button  v-on:click="addOtherOption(otherQuestion)">+</button><br>
          <div  v-for="(otherOptions,i) in otherQuestion.problemContentList" :key="i">
            <span style="padding-right:10px;">选项{{i+1}}:</span>
            <input type="text" class="liveInput"  v-model="otherOptions.questionContent">
          </div>


        </div>

      </div>
      <button type="button" class="btn btn-default" v-on:click="submit()">发布</button>
      <h2></h2>
    </form>
  </div>
</template>

<script>
  import g from '../../global.js'
  import Vue from 'vue'
  import VueResource from 'vue-resource'
  import zhcn from 'vee-validate/dist/locale/zh_CN'
  import VeeValidate, {
    Validator
  } from 'vee-validate'
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
    data() {
      return {
        issueId: '',
        issue: {
          issueQuestionList: []
        },
        otherIssueQuestionList: [],
        matchList: {}
      }
    },
    methods: {
      submit: function () {
        this.$validator.validateAll().then(result => {
          if (result) {

            let issue = this.issue;
             issue.otherQuestionList=this.otherIssueQuestionList

              Vue.http.post(g.t('/api/admin/issue'), JSON.stringify(issue)).then(function (response) {
                location.href = '#/issueList'
              }, function (response) {
                g.showMessage(response)
              })
          }
        })
      },
   checkQuestion(question) {
        if(question.issueChecked=='CHECKED'){
          question.issueChecked='UNCHECKED';
        }else{
          question.issueChecked='CHECKED';
        }
   },
  addOtherQuestion () {
   this.otherIssueQuestionList.push({'problemTitle': '',problemContentList:[]})
  },
  addOtherOption (otherQuestion) {
    otherQuestion.problemContentList.push({'questionContent': ''})
  }
    },
    created() {
      let that = this
      Vue.http.get(g.t('/api/admin/matchListByProject'),{params:{project:'足球'}}).then(function (response) {
        that.matchList = response.bodyText.split('|')
      })

    Vue.http.get(g.t('/api/admin/getQuestionByProject'),{params:{project:'足球'}}).then(function (response) {
      that.issue.issueQuestionList = JSON.parse(response.bodyText)
    })
    }
  }
</script>

<style scoped>
  body {
    background-color: #CCC;
  }

</style>
