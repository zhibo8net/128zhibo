<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('联赛')}">
        <label>联赛中文名称</label>
        <input type="text" class="form-control" placeholder="联赛中文名称" v-model="league.leagueZh" v-validate="'required'" name="联赛中文名称">
        <span class="help-block" v-show="errors.has('联赛中文名称')">{{ errors.first('联赛中文名称') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('联赛英文名称')}">
        <label>联赛英文名称</label>
        <input type="text" class="form-control" placeholder="输入联赛英文名称" v-model="league.leagueEn" v-validate="'required'" name="联赛英文名称">
        <span class="help-block" v-show="errors.has('联赛英文名称')">{{ errors.first('联赛英文名称') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('联赛球队数')}">
        <label>联赛球队数</label>
        <input type="text" class="form-control" placeholder="联赛球队数" v-model="league.teamNum" v-validate="'required'" name="联赛球队数">
        <span class="help-block" v-show="errors.has('联赛球队数')">{{ errors.first('联赛球队数') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('联赛简称1')}">
        <label>联赛简称1</label>
        <input type="text" class="form-control" placeholder="输入联赛简称1" v-model="league.leagueName1" v-validate="'required'" name="联赛简称1">
        <span class="help-block" v-show="errors.has('联赛简称1')">{{ errors.first('联赛简称1') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('联赛简称2')}">
        <label>联赛简称2</label>
        <input type="text" class="form-control" placeholder="输入联赛简称2" v-model="league.leagueName2" v-validate="'required'" name="联赛简称2">
        <span class="help-block" v-show="errors.has('联赛简称2')">{{ errors.first('联赛简称2') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('联赛简称3')}">
        <label>联赛简称3</label>
        <input type="text" class="form-control" placeholder="输入联赛简称3" v-model="league.leagueName3" v-validate="'required'" name="联赛简称3">
        <span class="help-block" v-show="errors.has('联赛简称3')">{{ errors.first('联赛简称3') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('联赛图片')}">


          <img :src="league.leagueImgLink"/>

        <input ref="files" type="file" name="league.leagueImgLink" id="image" v-on:change="upload">
        <span class="help-block" v-show="errors.has('联赛图片')">{{ errors.first('联赛图片') }}</span>
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
  return { league:{}}
},methods: {
    submit: function () {
      this.$validator.validateAll().then(result => {
        if (result) {
          let league = this.$data.league
          if (!league.id) {
            Vue.http.post(g.t('/api/admin/league'), JSON.stringify(league)).then(function (response) {
              location.href = '#/leagueList'
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/league/' + league.id), JSON.stringify(league)).then(function (response) {
              location.href = '#/leagueList'
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
        that.$data.league.leagueImgLink= response.bodyText
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    }

},created () {
  let league = this.$data.league
  let leagueId = this.$route.params.id

  if (leagueId) {
    Vue.http.get(g.t('/api/admin/league/' + leagueId)).then((response) => {
      let leagueEntity = response.data
      this.$set(league, 'id', leagueEntity.id)
    this.$set(league, 'leagueZh', leagueEntity.leagueZh)
    this.$set(league, 'leagueEn', leagueEntity.leagueEn)
    this.$set(league, 'leagueName1', leagueEntity.leagueName1)
    this.$set(league, 'teamNum', leagueEntity.teamNum)
    this.$set(league, 'leagueName2', leagueEntity.leagueName2)
    this.$set(league, 'leagueName3', leagueEntity.leagueName3)
    this.$set(league, 'leagueImgLink', leagueEntity.leagueImgLink)
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
