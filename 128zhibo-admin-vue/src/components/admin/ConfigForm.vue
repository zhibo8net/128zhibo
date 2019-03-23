<template>
  <div>
    <form>
            <div :class="{'form-group':true, 'has-error':errors.has('键')}">
        <label>键</label>
        <input type="text" class="form-control" placeholder="输入键" v-model="config.ckey" v-validate="'required'" name="键">
        <span class="help-block" v-show="errors.has('键')">{{ errors.first('键') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('值')}">
        <label>值</label>
        <input type="text" class="form-control" placeholder="输入值" v-model="config.cvalue" v-validate="'required'" name="值">
        <span class="help-block" v-show="errors.has('值')">{{ errors.first('值') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('说明')}">
        <label>说明</label>
        <input type="text" class="form-control" placeholder="输入说明" v-model="config.cdesc" v-validate="'required'" name="说明">
        <span class="help-block" v-show="errors.has('说明')">{{ errors.first('说明') }}</span>
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
  name: 'Config',
  data () {
    return {
      config: {}
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let config = this.$data.config
          if (!config.id) {
            Vue.http.post(g.t('/api/admin/configs'), JSON.stringify(config)).then(function (response) {
              g.href('/configList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/configs/' + config.id), JSON.stringify(config)).then(function (response) {
              g.href('/configList')
            }, function (response) {
              g.toLogin()
            })
          }
        }
      })
    }
  },
  created () {
    let config = this.$data.config
    let configId = this.$route.params.id
    if (configId) {
      Vue.http.get(g.t('/api/admin/configs/' + configId)).then((response) => {
        let configEntity = response.data
        this.$set(config, 'id', configEntity.id)
        this.$set(config, 'ckey', configEntity.ckey)
        this.$set(config, 'cvalue', configEntity.cvalue)
        this.$set(config, 'cdesc', configEntity.cdesc)
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
