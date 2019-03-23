<template>
  <div>
    <form>
      <div :class="{'form-group':true, 'has-error':errors.has('球队')}">
        <label>球队中文名称</label>
        <input type="text" class="form-control" placeholder="球队中文名称" v-model="team.teamZh" v-validate="'required'" name="球队中文名称">
        <span class="help-block" v-show="errors.has('球队中文名称')">{{ errors.first('球队中文名称') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('球队英文名称')}">
        <label>球队英文名称</label>
        <input type="text" class="form-control" placeholder="输入球队英文名称" v-model="team.teamEn"  name="球队英文名称">
        <span class="help-block" v-show="errors.has('球队英文名称')">{{ errors.first('球队英文名称') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('球队简称1')}">
        <label>球队简称1</label>
        <input type="text" class="form-control" placeholder="输入球队简称1" v-model="team.teamName1" name="球队简称1">
        <span class="help-block" v-show="errors.has('球队简称1')">{{ errors.first('球队简称1') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('球队简称2')}">
        <label>球队简称2</label>
        <input type="text" class="form-control" placeholder="输入球队简称2" v-model="team.teamName2" name="球队简称2">
        <span class="help-block" v-show="errors.has('球队简称2')">{{ errors.first('球队简称2') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('球队简称3')}">
        <label>球队简称3</label>
        <input type="text" class="form-control" placeholder="输入球队简称3" v-model="team.teamName3"  name="球队简称3">
        <span class="help-block" v-show="errors.has('球队简称3')">{{ errors.first('球队简称3') }}</span>
      </div>
      <div :class="{'form-group':true, 'has-error':errors.has('球队图片')}">


          <img :src="team.teamImgLink"/>

        <input ref="files" type="file" name="team.teamImgLink" id="image" v-on:change="upload">
        <span class="help-block" v-show="errors.has('球队图片')">{{ errors.first('球队图片') }}</span>
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
  name: 'Index',data(){
  return {
    team:{}
  }
},methods: {
    submit: function () {
      this.$validator.validateAll().then(result =>{
        if (result) {
          let team = this.$data.team
          if (!team.id) {
            Vue.http.post(g.t('/api/admin/team'), JSON.stringify(team)).then(function (response) {
              location.href = '#/teamList'
            }, function (response) {
              g.toLogin()
            })
          } else {
            Vue.http.put(g.t('/api/admin/team/' + team.id), JSON.stringify(team)).then(function (response) {
              location.href = '#/teamList'
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
        that.$data.team.teamImgLink= response.bodyText
      }, function (response) {
        console.log(2)
        console.log(response.bodyText)
      })
    }

},created () {
  let team = this.$data.team
  let teamId = this.$route.params.id

  if (teamId) {
    Vue.http.get(g.t('/api/admin/team/' + teamId)).then((response) => {
      let teamEntity = response.data
      this.$set(team, 'id', teamEntity.id)
    this.$set(team, 'teamZh', teamEntity.teamZh)
    this.$set(team, 'teamEn', teamEntity.teamEn)
    this.$set(team, 'teamName1', teamEntity.teamName1)
    this.$set(team, 'teamName2', teamEntity.teamName2)
    this.$set(team, 'teamName3', teamEntity.teamName3)
    this.$set(team, 'teamImgLink', teamEntity.teamImgLink)
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
