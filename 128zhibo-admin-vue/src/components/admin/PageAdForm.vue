<template>
  <div>
    <form>
      <h4>广告位置{{ pageAd.id }}</h4>
      <div :class="{'form-group':true, 'has-error':errors.has('键')}">
        <label>位置</label>
        <input type="text" class="form-control" placeholder="输入键" v-model="pageAd.adKey" v-validate="'required'" name="键">
        <span class="help-block" v-show="errors.has('键')">{{ errors.first('键') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('说明')}">
        <label>尺寸</label>
        <input type="text" class="form-control" placeholder="输入说明" v-model="pageAd.message" v-validate="'required'" name="说明">
        <span class="help-block" v-show="errors.has('说明')">{{ errors.first('说明') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('链接')}">
        <label>链接</label>
        <input type="text" class="form-control" placeholder="输入链接" v-model="pageAd.link" v-validate="'required'" name="链接">
        <span class="help-block" v-show="errors.has('链接')">{{ errors.first('链接') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('图片')}">
        <label>图片</label>
        <input ref="files" type="file" name="image" id="image" v-on:change="upload">
        <img :src="pageAd.image" class="img-responsive"/>
        <a href="javascript:void(0);" v-on:click="clear">删除</a>
        <span class="help-block" v-show="errors.has('图片')">{{ errors.first('图片') }}</span>
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

Vue.config.productionTip = false

export default {
  name: 'Index',
  data () {
    return {
      pageAd: {}
    }
  },
  methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let pageAd = this.$data.pageAd
          if (!pageAd.id) {
            Vue.http.post(g.t('/api/admin/pageAds'), JSON.stringify(pageAd)).then(function (response) {
              g.href('/pageAdList')
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/pageAds/' + pageAd.id), JSON.stringify(pageAd)).then(function (response) {
              g.href('/pageAdList')
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
        that.$set(that.$data.pageAd, 'image', response.bodyText)
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    },
    clear: function () {
      this.$set(this.$data.pageAd, 'image', '')
    }
  },
  created () {
    let pageAd = this.$data.pageAd
    let pageAdId = this.$route.params.id
    if (pageAdId) {
      Vue.http.get(g.t('/api/admin/pageAds/' + pageAdId)).then((response) => {
        let pageAdEntity = response.data
        this.$set(pageAd, 'id', pageAdEntity.id)
        this.$set(pageAd, 'adKey', pageAdEntity.adKey)
        this.$set(pageAd, 'message', pageAdEntity.message)
        this.$set(pageAd, 'link', pageAdEntity.link)
        this.$set(pageAd, 'image', pageAdEntity.image)
      }, function (response) {
        g.toLogin()
      })
    }
  }
}
</script>

<style scoped>
</style>
