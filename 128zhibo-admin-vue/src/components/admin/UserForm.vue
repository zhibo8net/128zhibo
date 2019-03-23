<template>
  <div>
      <div :class="{'form-group':true, 'has-error':errors.has('期次')}">
        <label>期次：</label>
        <span>{{ issue.issue }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('对阵')}">
        <label>对阵：</label>
        <span>{{ issue.matchNameAndId }}</span>
        <span class="help-block" v-show="errors.has('对阵')">{{ errors.first('对阵') }}</span>
      </div>
    <div :class="{'form-group':true, 'has-error':errors.has('状态')}">
      <label>状态：</label>
      <span>{{issue.statusDesc}}</span>
      <span class="help-block" v-show="errors.has('对阵')">{{ errors.first('状态') }}</span>
    </div>
      <div :class="{'form-group':true, 'has-error':errors.has('问题数')}">
        <label>问题数：</label>
        <span>{{issue.problemNum}}</span>
        <span class="help-block" v-show="errors.has('对阵')">{{ errors.first('问题数') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('竞猜用户数')}">
        <label>竞猜用户数：</label>
        <span>{{ issue.partNum }}</span>
        <span class="help-block" v-show="errors.has('对阵')">{{ errors.first('竞猜用户数') }}</span>
      </div>
     <div :class="{'form-group':true, 'has-error':errors.has('竞猜答案')}">
      <label>竞猜答案(如 1-2-1  对应竞猜答案)：</label>
       <input id="update_issueAnswer" type="text" v-model="issue.issueAnswer" width="200"/>
      <span class="help-block" v-show="errors.has('对阵')">{{ errors.first('竞猜答案') }}</span>
    </div>
    <h2  v-if="issue.userShowFlag!='DISPLAY'">用户管理</h2>
    <form>
      <button type="button" class="btn btn-default" v-on:click="issuePublic()">竞猜发布</button>
      <button type="button" class="btn btn-default" v-on:click="issueEnd()">比赛结束</button>
      <button type="button" class="btn btn-default" v-on:click="issueAward()">开奖</button>
      <button type="button" class="btn btn-default" v-on:click="submit()">批量设置中奖</button>
      <button type="button" class="btn btn-default" v-on:click="retPage()">返回</button>

      <table class="table" v-if="issue.userShowFlag!='DISPLAY'">
        <thead>
          <tr>
            <th v-for="(item,i) in tablist" :key="i">{{item}}</th>
          </tr>
        </thead>
        <tbody>

          <tr v-for="a in userAdminDbList" v-if="issue.userShowFlag!='DISPLAY'">
            <td><input type="checkbox" name="category" v-on:click="addQuestion(a.id)" /></td>
            <td>{{ a.userMobile }}</td>
            <td>{{ a.addTimeStr }}</td>
            <td>{{ a.answer }}</td>
            <td>{{ a.answerRate }}%</td>
            <td v-if="a.status=='AWARD'" style="color:red">中奖</td>
            <td v-if="a.status=='UNAWARD'" >未中奖</td>
            <td v-if="a.status=='INIT'" >待开奖</td>
            <td>{{ a.userWx }}</td>
          </tr>
          <tr v-if="userAdminDbList.length == 0">
            <td colspan="7">
              暂无用户
            </td>
          </tr>
        </tbody>
      </table>
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
        tablist: ['选择', '用户电话', '竞猜时间', '竞猜内容', '正确率', '是否中奖', '微信'],
        issueId: '',
        list: [], // 已选中用户id列表
        issue: {
          issueUserList: []
        },
        userAdminDbList: []
      }
    },
    methods: {
      submit: function () {
        this.$validator.validateAll().then(result => {
          if (result) {
            this.issue.issueUserList = [];
            this.list.map((data) => {
              this.issue.issueUserList.push({
                'id': data
              })
            })
            let issue = this.issue
          if (issue.id) {
              Vue.http.post(g.t('/api/admin/updateIssueUser'), JSON.stringify(issue)).then(function (response) {
                alert("操作成功")
                location.href = '#/issueList'
              }, function (response) {
                g.showMessage(response)
              })
            }
          }
        })
      },
  retPage() {
    location.href = '#/issueList'
  },
  issuePublic() {
    let issue = this.issue
    let issueId = this.issueId = this.$route.params.id
    Vue.http.post(g.t('/api/admin/issuePublic'), JSON.stringify(issue)).then(function (response) {
      alert("操作成功")
      location.href = '#/issueList'
    }, function (response) {
      g.showMessage(response)
    })


  },issueEnd() {
    let issue = this.issue
    let issueId = this.issueId = this.$route.params.id
    Vue.http.post(g.t('/api/admin/issueEnd'), JSON.stringify(issue)).then(function (response) {
      alert("操作成功")
      location.href = '#/issueList'
    }, function (response) {
      g.showMessage(response)
    })
  },issueAward() {
    var issueAnswer= $("#update_issueAnswer").val();
    let issue = this.issue
    let issueId = this.issueId = this.$route.params.id
    issue.issueAnswer=issueAnswer;
    Vue.http.post(g.t('/api/admin/issueAward'), JSON.stringify(issue)).then(function (response) {
      alert("操作成功")
      location.href = '#/issueList'
    }, function (response) {
      g.showMessage(response)
    })
  },
      addQuestion(id) {
        let index = this.list.indexOf(id)
      if (index > -1) {
          this.list.splice(index, 1)
        } else {
          this.list.push(id)
        }
      }
    },
    created() {
      let that = this
      let issue = this.$data.issue
      let issueId = this.issueId = this.$route.params.id
      if (issueId) {
        Vue.http.get(g.t('/api/admin/issue/' + issueId)).then((response) => {
          let issueEntity = response.data
          that.userAdminDbList = issueEntity.issueUserList
          this.$set(issue, 'id', issueEntity.id)
          this.$set(issue, 'issue', issueEntity.issue)
        this.$set(issue, 'userShowFlag', issueEntity.userShowFlag)
        this.$set(issue, 'statusDesc', issueEntity.statusDesc)
        this.$set(issue, 'partNum', issueEntity.partNum)
        this.$set(issue, 'issueAnswer', issueEntity.issueAnswer)
        this.$set(issue, 'problemNum', issueEntity.problemNum)
          this.$set(issue, 'matchNameAndId', issueEntity.matchNameAndId)
        }, function (response) {
          g.toLogin()
        })
      }
    }
  }
</script>

<style scoped>
  body {
    background-color: #CCC;
  }

</style>
