<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('旧密码')}">
        <label>旧密码</label>
        <input type="password" class="form-control" placeholder="输入旧密码" v-model="info.oldPassword" name="旧密码" v-validate="'required'">
        <span class="help-block" v-show="errors.has('旧密码')">{{ errors.first('旧密码') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('新密码')}">
        <label>新密码</label>
        <input type="password" class="form-control" placeholder="输入新密码" v-model="info.newPassword" name="新密码" v-validate="'required'">
        <span class="help-block" v-show="errors.has('新密码')">{{ errors.first('新密码') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('重复密码')}">
        <label>重复密码</label>
        <input type="password" class="form-control" placeholder="重复输入新密码" v-model="info.repeatPassword" name="重复密码" v-validate="'required'">
        <span class="help-block" v-show="errors.has('重复密码')">{{ errors.first('重复密码') }}</span>
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

Vue.use(VueResource)

Validator.localize('zh_CN', zhcn)
Vue.use(VeeValidate)

export default {
  name: 'Index',
  data () {
    return {
      info: {
        oldPassword: '',
        newPassword: '',
        repeatPassword: ''
      }
    }
  },
  methods: {
    submit: function () {
      let info = this.$data.info
      this.$validator.validateAll().then(result => {
        if (result) {
          if (info.newPassword !== info.repeatPassword) {
            alert('必须两次输入同样的密码！')
            return
          }

          Vue.http.post(g.t(`/api/admin/accounts/resetPassword?oldPassword=${info.oldPassword}&newPassword=${info.newPassword}`)).then(function (response) {
            alert('修改成功')
          }, function (response) {
            if (response.body.code === 401) {
              alert(response.body.message)
            }
          })
        }
      })
    }
  },
  created () {
  }
}
</script>

<style scoped>
body{
  background-color: #CCC;
}
</style>
