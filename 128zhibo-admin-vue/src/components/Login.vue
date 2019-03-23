<template>
<div>
  <div class="row">
    <div id="login-content" class="col-md-4 col-md-offset-4">
      <div class="login-title text-center">
        直播吧后台管理系统
      </div>
      <form class="form-horizontal">
        <div :class="{'form-group':true, 'has-error':errors.has('手机号码')}">
          <div class="col-sm-12">
            <input type="text" class="form-control" placeholder="手机号码" v-model="mobile" v-validate="'required'" name="手机号码">
            <span class="help-block" v-show="errors.has('手机号码')">{{ errors.first('手机号码') }}</span>
          </div>
        </div>
        <div :class="{'form-group':true, 'has-error':errors.has('密码')}">
          <div class="col-sm-12">
            <input type="password" class="form-control" placeholder="密码" v-model="password" v-validate="'required'" name="密码" @keyup.enter="login()">
            <span class="help-block" v-show="errors.has('密码')">{{ errors.first('密码') }}</span>
          </div>
        </div>
        <div :class="{'form-group':true}">
          <div class="col-sm-12">
            <div class="checkbox">
              <label>
                <input type="checkbox" v-model="rememberMe"> 记住我
              </label>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-12">
            <button type="button" class="btn btn-default pull-right" v-on:click='login()'>登录</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
</template>

<script>
import Vue from 'vue'
import VueResource from 'vue-resource'
import zhcn from 'vee-validate/dist/locale/zh_CN'
import VeeValidate, { Validator } from 'vee-validate'

Vue.use(VueResource)
Validator.localize('zh_CN', zhcn)
Vue.use(VeeValidate)

export default {
  name: 'Login',
  data () {
    return {
      mobile: '',
      password: '',
      rememberMe: false
    }
  },
  methods: {
    login: function () {
      if (this.errors.items.length === 0) {
        let mobile = this.$data.mobile
        let password = this.$data.password
        let rememberMe = this.$data.rememberMe

        localStorage.setItem('mobile', mobile)
        if (rememberMe) {
          localStorage.setItem('password', password)
        }

        Vue.http.post('/api/login?mobile=' + mobile + '&password=' + password).then(function (response) {
          let token = response.data.token
          localStorage.setItem('token', token)
          window.router.push('liveSourceList')
        }, function (response) {
          if (response.body.code === 401) {
            alert(response.body.message)
          }
        })
      }
    }
  },
  created () {
    let mobile = localStorage.getItem('mobile')
    let password = localStorage.getItem('password')
    if (mobile && password) {
      this.$data.mobile = mobile
      this.$data.password = password
      this.$data.rememberMe = true
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.login-title{
  font-size: 32px;
  line-height: 100px;
}

#login-content{
  color: #fff;
  background-color: #86a1a7;
  padding: 40px;
  margin-top: 100px;
  width:
}
</style>