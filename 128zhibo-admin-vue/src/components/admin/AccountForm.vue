<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('用户名')}">
        <label>用户名</label>
        <input type="text" class="form-control" placeholder="输入用户名" v-model="account.mobile" v-validate="'required'" name="用户名">
        <span class="help-block" v-show="errors.has('用户名')">{{ errors.first('用户名') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('姓名')}">
        <label>姓名</label>
        <input type="text" class="form-control" placeholder="输入姓名" v-model="account.name" v-validate="'required'" name="姓名">
        <span class="help-block" v-show="errors.has('姓名')">{{ errors.first('姓名') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('密码')}">
        <label>密码</label>
        <input type="password" class="form-control" placeholder="如不修改请留空" v-model="account.plainPassword" name="密码">
        <span class="help-block" v-show="errors.has('密码')">{{ errors.first('密码') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('重复密码')}">
        <label>重复密码</label>
        <input type="password" class="form-control" placeholder="如不修改请留空" v-model="account.repeatPassword" name="重复密码">
        <span class="help-block" v-show="errors.has('重复密码')">{{ errors.first('重复密码') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('状态')}">
        <label>状态</label>
        <select class="form-control" name="运行状态" v-model="account.status">
            <option value="1">启用</option>
            <option value="0">停用</option>
        </select>
        <span class="help-block" v-show="errors.has('状态')">{{ errors.first('状态') }}</span>
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
      account: {
        status: 1
      }
    }
  },
  methods: {
    submit: function () {
      let account = this.$data.account
      this.$validator.validateAll().then(result => {
        if (result) {
          if (!account.id && (!account.plainPassword || !account.repeatPassword || account.plainPassword !== account.repeatPassword)) {
            alert('新建用户时必须两次输入同样的密码！')
            return
          } else if (account.id && account.plainPassword !== account.repeatPassword) {
            alert('两次输入的密码不一样！')
            return
          }

          if (!account.id) {
            Vue.http.post(g.t('/api/admin/accounts'), JSON.stringify(account)).then(function (response) {
              g.href('/accountList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/accounts/' + account.id), JSON.stringify(account)).then(function (response) {
              g.href('/accountList')
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
        that.$set(that.$data.account, 'image', response.bodyText)
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    },
    onContentChange (val) {
      this.account.content = val
    }
  },
  created () {
    let account = this.$data.account
    let accountId = this.$route.params.id
    if (accountId) {
      Vue.http.get(g.t('/api/admin/accounts/' + accountId)).then((response) => {
        let accountEntity = response.data
        this.$set(account, 'id', accountEntity.id)
        this.$set(account, 'mobile', accountEntity.mobile)
        this.$set(account, 'name', accountEntity.name)
        this.$set(account, 'status', accountEntity.status)
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
